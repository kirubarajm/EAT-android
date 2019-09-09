package com.tovo.eat.ui.track;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.service.autofill.FieldClassification;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.firebase.geofire.util.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.android.PolyUtil;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderTrackingBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.notification.FirebaseDataReceiver;
import com.tovo.eat.ui.track.help.OrderHelpActivity;
import com.tovo.eat.ui.track.orderdetails.OrderDetailsActivity;
import com.tovo.eat.utilities.LatLngInterpolator;
import com.tovo.eat.utilities.MarkerAnimation;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import org.joda.time.DateTime;

import java.io.FilterReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class OrderTrackingActivity extends BaseActivity<ActivityOrderTrackingBinding, OrderTrackingViewModel> implements
        OrderTrackingNavigator, OnMapReadyCallback, HasSupportFragmentInjector {


    private static final String TAG = OrderTrackingActivity.class.getSimpleName();
    private static final int overview = 0;
    @Inject
    OrderTrackingViewModel mOrderTrackingViewModel;
    ActivityOrderTrackingBinding mActivityDirectionBinding;
    Bitmap origin_marker, destination_marker, moveit_marker;
    double deliveryBoyLat;
    double deliveryBoyLng;
    LatLng moveitLatLng;
    LatLng makeitLatLng;
    LatLng cusLatLng;
    boolean liveTracking = true;
    CountDownTimer countDownTimer;


    Analytics analytics;
    String  pageName="Live order tracking";


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    FirebaseDataReceiver dataReceiver = new FirebaseDataReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            try {
                Bundle bundle = intent.getExtras();
                if (bundle == null) return;
                String pageid = bundle.getString("pageid");

                if (pageid != null) {
                    mOrderTrackingViewModel.changeTrackingStatus();
                }

                   /* if (pageid.equals("8")||pageid.equals("7")) {
                     //   mMainViewModel.liveOrders();
                    }*/


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };
    private Marker moveitLocationMarker;
    private Marker makeitLocationMarker;
    private Marker customerLocationMarker;
    private GoogleMap mMap;
    private boolean firstTimeFlag = true;
    private DatabaseReference mFirebaseTransportRef;
    private LinkedList<Map<String, Object>> mTransportStatuses = new LinkedList<>();
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    public static Intent newIntent(Context context) {
        return new Intent(context, OrderTrackingActivity.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int wantedWidth, int wantedHeight) {
        Bitmap output = Bitmap.createBitmap(wantedWidth, wantedHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Matrix m = new Matrix();
        m.setScale((float) wantedWidth / bitmap.getWidth(), (float) wantedHeight / bitmap.getHeight());
        canvas.drawBitmap(bitmap, m, new Paint());

        return output;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2, String sr) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (sr.equals("K")) {
            dist = dist * 1.609344;
        } else if (sr.equals("N")) {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void dResult(String origin, String destination) throws ApiException {
        try {
            DirectionsResult results = null;
            results = getDirectionsDetails(origin, destination, TravelMode.DRIVING);

            if (results != null) {
                addPolyline(results, mMap);
                positionCamera(results.routes[overview], mMap);
                addMarkersToMap(results, mMap);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

    }

    public void createMarker() {
        Drawable oDrawable = getResources().getDrawable(R.drawable.delivery_man_map_marker);
        Bitmap oBitmap = ((BitmapDrawable) oDrawable).getBitmap();
        moveit_marker = scaleBitmap(oBitmap, 80, 117);

        Drawable dDrawable = getResources().getDrawable(R.drawable.kitchen_map_marker);
        Bitmap dBitmap = ((BitmapDrawable) dDrawable).getBitmap();
        destination_marker = scaleBitmap(dBitmap, 80, 117);

        Drawable eDrawable = getResources().getDrawable(R.drawable.eat_marker);
        Bitmap eBitmap = ((BitmapDrawable) eDrawable).getBitmap();
        origin_marker = scaleBitmap(eBitmap, 80, 117);




        /*int width = oBitmap.getWidth();
        int height = oBitmap.getHeight();
        float scaleWidth = ((float) 80) / width;
        float scaleHeight = ((float) 140) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
         origin_marker = Bitmap.createBitmap(oBitmap, 0, 0, width, height,
                matrix, false);*/


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDirectionBinding = getViewDataBinding();
        mOrderTrackingViewModel.setNavigator(this);


        setSupportActionBar(mActivityDirectionBinding.header);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        analytics=new Analytics(this,pageName);

       /* mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);*/


        // startService(new Intent(this, TrackerService.class));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        createMarker();


    }

    @Override
    public int getBindingVariable() {
        return BR.orderTrackingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_tracking;
    }

    @Override
    public OrderTrackingViewModel getViewModel() {
        return mOrderTrackingViewModel;
    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        /*try {

         *//*    https://mapstyle.withgoogle.com/*//*

            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }*/


        if (!liveTracking) {
            liveTracking = true;
            makeitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(destination_marker)).position(makeitLatLng));
            //  moveToCurrentLocation(moveitLatLng);
            customerLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(cusLatLng));


            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(makeitLatLng);
            builder.include(cusLatLng);
            LatLngBounds bounds = builder.build();

            //  int padding = 0; // offset from edges of the map in pixels


            int width = mActivityDirectionBinding.mapSize.getMeasuredWidth();
            int height = mActivityDirectionBinding.mapSize.getMeasuredHeight();
            int padding = (int) (width * 0.10);

            try {


                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mMap.moveCamera(cu);

            } catch (Exception ee) {
                ee.printStackTrace();
                mOrderTrackingViewModel.getOrderDetails();
            }
        }


       /* SharedPreferences pref = getSharedPreferences("MyPref", 0);
        try {

            String strsdsd = pref.getString(AppConstants.LATITUDE, null);
            String strLong = pref.getString(AppConstants.LONGITUDE, null);

            LatLng latLng = new LatLng(Double.parseDouble(strsdsd), Double.parseDouble(strLong));

            if (firstTimeFlag && mMap != null) {
                //   animateCamera(currentLocation);

                //  sendRequest(String.valueOf(currentLocation.getLatitude()) + "," + String.valueOf(currentLocation.getLongitude()), sDesLatLang);
                try {
                    moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(latLng));
                    dResult(strsdsd + "," + strLong, sDesLatLang);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
                moveToCurrentLocation(latLng);


                firstTimeFlag = false;
            }

            if (distance(currentLocation.getLatitude(), currentLocation.getLongitude(), Double.parseDouble(strsdsd), Double.parseDouble(strLong), "K") <= 0.5) {
                mOrderTrackingViewModel.desReached.set(true);
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch (NumberFormatException r)
        {
            r.printStackTrace();
        }*/


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tracking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.refresh) {
            mOrderTrackingViewModel.getOrderDetails();
        } else if (id == R.id.order_cancel) {
            Intent intent = OrderHelpActivity.newIntent(this);
            intent.putExtra("name", mOrderTrackingViewModel.orderTrackingResponse.getResult().get(0).getMoveitdetail().getName());
            intent.putExtra("number", String.valueOf(mOrderTrackingViewModel.orderTrackingResponse.getResult().get(0).getMoveitdetail().getPhoneno()));
            intent.putExtra("charge", String.valueOf(mOrderTrackingViewModel.orderTrackingResponse.getResult().get(0).getPrice() - mOrderTrackingViewModel.orderTrackingResponse.getResult().get(0).getServiceCharge()));
            intent.putExtra("status", mOrderTrackingViewModel.track.get());
            intent.putExtra("message", mOrderTrackingViewModel.orderTrackingResponse.getResult().get(0).getCancellationMessage());
            startActivity(intent);
        }
        // return true;
        return super.onOptionsItemSelected(item);
    }

    private void showMarker1(LatLng currentLocation) {

        if (moveitLocationMarker == null)
            moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(currentLocation));
        else
            MarkerAnimation.animateMarkerToGB(moveitLocationMarker, currentLocation, new LatLngInterpolator.Spherical());

    }

    private void showMarker(Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        // googleMap.clear();
        if (moveitLocationMarker == null)
            moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(moveitLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    @Override
    public void onStop() {
        super.onStop();

        try {
            unregisterReceiver(dataReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        registerReceiver(dataReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        //startService(new Intent(OrderTrackingActivity.this, TrackerService.class));
        //startLocationTracking();
        super.onResume();
        registerWifiReceiver();
        mOrderTrackingViewModel.getOrderDetails();
    }

    private DirectionsResult getDirectionsDetails(String origin, String destination, TravelMode mode) throws ApiException {
        DateTime now = new DateTime();
        try {
            return DirectionsApi.newRequest(getGeoContext())
                    .mode(mode)
                    .origin(origin)
                    .destination(destination)
                    .departureTime(now)
                    .await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setupGoogleMapScreenSettings(GoogleMap mMap) {
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setTrafficEnabled(false);
        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);
    }

    private void addMarkersToMap(DirectionsResult results, GoogleMap mMap) {
        //  mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].startLocation.custlat,results.routes[overview].legs[overview].startLocation.custlng)).title(results.routes[overview].legs[overview].startAddress));
        mMap.addMarker(new MarkerOptions().position(new LatLng(results.routes[overview].legs[overview].endLocation.lat, results.routes[overview].legs[overview].endLocation.lng)).title(results.routes[overview].legs[overview].startAddress).icon(BitmapDescriptorFactory.fromBitmap(destination_marker)).snippet(getEndLocationTitle(results)));
    }

    private void positionCamera(DirectionsRoute route, GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(route.legs[overview].startLocation.lat, route.legs[overview].startLocation.lng), 12));
    }

    private void addPolyline(DirectionsResult results, GoogleMap mMap) {
        try {
            List<LatLng> decodedPath = PolyUtil.decode(results.routes[overview].overviewPolyline.getEncodedPath());
            //    PolylineOptions rectLine = new PolylineOptions().width(4).color(getResources().getColor(R.color.cyan));

            mMap.addPolyline(new PolylineOptions().addAll(decodedPath).color(getResources().getColor(R.color.red)));
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private String getEndLocationTitle(DirectionsResult results) {
        return "Time :" + results.routes[overview].legs[overview].duration.humanReadable + " DistanceNew :" + results.routes[overview].legs[overview].distance.humanReadable;
    }

    private GeoApiContext getGeoContext() {
        GeoApiContext geoApiContext = new GeoApiContext();
        return geoApiContext
                .setQueryRateLimit(3)
                .setApiKey(getString(R.string.map_key))
                .setConnectTimeout(1, TimeUnit.SECONDS)
                .setReadTimeout(1, TimeUnit.SECONDS)
                .setWriteTimeout(1, TimeUnit.SECONDS);
    }

    @Override
    public void callDeliveryMan(String number) {

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);

    }

    @Override
    public void tracking(String cusLat, String cusLng, double makeitLat, double makeitLng) {

        try {

            LatLngBounds bounds = null;

            makeitLatLng = new LatLng(makeitLat, makeitLng);

            cusLatLng = new LatLng(Double.parseDouble(cusLat), Double.parseDouble(cusLng));
            liveTracking = false;

            if (mMap != null) {
                liveTracking = true;
                makeitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(destination_marker)).position(makeitLatLng));
                //  moveToCurrentLocation(moveitLatLng);
                customerLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(cusLatLng));



                try {

                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(makeitLatLng);
                    builder.include(cusLatLng);
                    bounds = builder.build();

                } catch (Exception rr) {
                    rr.printStackTrace();
                }
                //  int padding = 0; // offset from edges of the map in pixels

                int width = mActivityDirectionBinding.mapSize.getMeasuredWidth();
                int height = mActivityDirectionBinding.mapSize.getMeasuredHeight();
                int padding = (int) (width * 0.10);


                try {

                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                    mMap.moveCamera(cu);

                } catch (Exception ee) {
                    ee.printStackTrace();
                    mOrderTrackingViewModel.getOrderDetails();

                }

            }
        } catch (Exception rr) {
            rr.printStackTrace();
        }

    }

    @Override
    public void orderPickedUp(Integer moveitId) {
        loadPreviousStatuses(moveitId);
    }

    @Override
    public void clickBack() {
        finish();
    }

    @Override
    public void orderDetails(Integer orderId) {

        Intent intent = OrderDetailsActivity.newIntent(this);
        intent.putExtra("orderId",
                String.valueOf(orderId));
        startActivity(intent);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void startTrackingTimer(long minuts) {


      /*  if (countDownTimer != null) countDownTimer.cancel();


        if (minuts * 60000 > 0) {

            CountDownTimer countDownTimer = new CountDownTimer(minuts * 60000, 1000) {

                public void onTick(long millisUntilFinished) {
                    //  mLoginViewModelMain.timer.set(String.valueOf(millisUntilFinished / 1000));


                }

                public void onFinish() {

                    if (moveitLatLng != null)

                        mOrderTrackingViewModel.getOrderETA(String.valueOf(moveitLatLng.latitude), String.valueOf(moveitLatLng.longitude));
                }

            }.start();
        }*/
    }

    private void loadPreviousStatuses(Integer moveitId) {

        DatabaseReference ref = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location");
        GeoFire geoFire = new GeoFire(ref);

        Query locationDataQuery =FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location").child(String.valueOf(moveitId));
        locationDataQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //The dataSnapshot should hold the actual data about the location
              //  dataSnapshot.getChild("name").getValue(String.class); //should return the name of the location and dataSnapshot.getChild("description").getValue(String.class); //should return the description of the locations

                dataSnapshot.child("l").getValue();



              List<Double> gg= (List<Double>) dataSnapshot.child("l").getValue();



                moveitLatLng = new LatLng(gg.get(0),gg.get(1));

                if (distance(cusLatLng.latitude, cusLatLng.longitude, gg.get(0),gg.get(1), "K") <= 2) {
                    //  mOrderTrackingViewModel.orderDeliveryStatus.set("Your food is almost there");

                    if (moveitLocationMarker == null) {
                        if (mMap!=null)
                            moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(moveit_marker)).position(moveitLatLng));
                    }

                    showMarker1(moveitLatLng);
                  //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveitLatLng, 20));
                }






                /*Map<String, Object> objectMap = (HashMap<String, Object>)dataSnapshot.getValue();

                for (Object obj : objectMap.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;

                    }
                }







                List<Double> messages =  dataSnapshot.child("l").getValue();



                Object o=   dataSnapshot.child("name").getValue(String.class);


            String lt=requestPojo.getLat();
            String ln=requestPojo.getLon();



                Log.e("lat", requestPojo.getLat());
                Log.e("lng", requestPojo.getLon());

                Log.e("lat", requestPojo.getLat());
                Log.e("lng", requestPojo.getLon());
                Log.e("lat", requestPojo.getLat());
                Log.e("lng", requestPojo.getLon());
                Log.e("lat", requestPojo.getLat());
                Log.e("lng", requestPojo.getLon());*/







               /* if (dataSnapshot != null) {
                    for (DataSnapshot transportStatus : dataSnapshot.getChildren()) {
                        mTransportStatuses.add(Integer.parseInt(transportStatus.getKey()),
                                (Map<String, Object>) transportStatus.getValue());
                    }
                }
                if (mTransportStatuses.size() != 0) {

                    Map<String, Object> status = mTransportStatuses.get(0);
                    Location locationForStatus = new Location("");
                    locationForStatus.setLatitude((double) status.get("lat"));
                    locationForStatus.setLongitude((double) status.get("lng"));
                    LatLng latLng = new LatLng((double) status.get("lat"), (double) status.get("lng"));

                    if (distance(cusLatLng.latitude, cusLatLng.longitude, (double) status.get("lat"), (double) status.get("lng"), "K") <= 2) {


                        mOrderTrackingViewModel.orderDeliveryStatus.set("Your food is almost there");

                        if (moveitLocationMarker == null) {
                            moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(latLng));
                        }

                        showMarker1(latLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                }*/





                /*if (location != null) {
                    System.out.println(String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));

                    Log.e("loc", String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));


                    mOrderTrackingViewModel.getOrderETA(String.valueOf( location.latitude), String.valueOf(location.longitude));


                    moveitLatLng = new LatLng(location.latitude, location.longitude);

                    if (distance(cusLatLng.latitude, cusLatLng.longitude, location.latitude, location.longitude, "K") <= 2) {
                        //  mOrderTrackingViewModel.orderDeliveryStatus.set("Your food is almost there");

                        if (moveitLocationMarker == null) {
                            if (mMap!=null)
                                moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(moveit_marker)).position(moveitLatLng));
                        }

                        showMarker1(moveitLatLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveitLatLng, 20));
                    }


                } else {
                    System.out.println(String.format("There is no location for key %s in GeoFire", key));
                }*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });














        /*geoFire.getLocation(String.valueOf(moveitId), new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                if (location != null) {
                    System.out.println(String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));

                    Log.e("loc", String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));


                    mOrderTrackingViewModel.getOrderETA(String.valueOf( location.latitude), String.valueOf(location.longitude));


                    moveitLatLng = new LatLng(location.latitude, location.longitude);

                    if (distance(cusLatLng.latitude, cusLatLng.longitude, location.latitude, location.longitude, "K") <= 2) {
                        //  mOrderTrackingViewModel.orderDeliveryStatus.set("Your food is almost there");

                        if (moveitLocationMarker == null) {
                            if (mMap!=null)
                            moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(moveit_marker)).position(moveitLatLng));
                        }

                        showMarker1(moveitLatLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveitLatLng, 20));
                    }


                } else {
                    System.out.println(String.format("There is no location for key %s in GeoFire", key));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("There was an error getting the GeoFire location: " + databaseError);
            }

        });*/





       // GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(cusLatLng.latitude, cusLatLng.longitude), 2);

       /* geoQuery.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {

            @Override
            public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                // ...
            }

            @Override
            public void onDataExited(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {
                // ...
            }

            @Override
            public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {
                // ...


                if (dataSnapshot.getKey().equals(String.valueOf(moveitId))) {

                    moveitLatLng = new LatLng(location.latitude, location.longitude);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (moveitLocationMarker == null) {
                                if (mMap != null)
                                    moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(moveit_marker)).position(moveitLatLng));
                            } else {
                                MarkerAnimation.animateMarkerToGB(moveitLocationMarker, moveitLatLng, new LatLngInterpolator.Spherical());
                            }
                            //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveitLatLng, 20));
                        }
                    });

                }

                *//*try {
                    LatLng latLng = new LatLng(location.latitude, location.longitude);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    moveitLocationMarker = mMap.addMarker(markerOptions);

                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(20));


                    if (moveitLocationMarker == null) {
                        moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(moveit_marker)).position(moveitLatLng));
                    } else {
                        MarkerAnimation.animateMarkerToGB(moveitLocationMarker, moveitLatLng, new LatLngInterpolator.Spherical());
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveitLatLng, 20));
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
            }

            @Override
            public void onGeoQueryReady() {
                // ...
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                // ...
            }

        });
*/


       /* geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String username, GeoLocation location) {





                Query locationDataQuery = new FirebaseDatabase.getInstance().child("locations").child(key);




                locationDataQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //The dataSnapshot should hold the actual data about the location
                        dataSnapshot.getChild("name").getValue(String.class); //should return the name of the location and dataSnapshot.getChild("description").getValue(String.class); //should return the description of the locations
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });










            }

            @Override
            public void onKeyExited(String username) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {
                if (key.equals(String.valueOf(moveitId))) {
                    moveitLatLng = new LatLng(location.latitude, location.longitude);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (moveitLocationMarker == null) {
                                if (mMap != null)
                                    moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(moveit_marker)).position(moveitLatLng));
                            } else {
                                MarkerAnimation.animateMarkerToGB(moveitLocationMarker, moveitLatLng, new LatLngInterpolator.Spherical());
                            }
                            //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(moveitLatLng, 20));
                        }
                    });
                }
            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }

        });*/


      /*  //  String transportId = mPrefs.getString(getString(R.string.transport_id), "");
        // FirebaseAnalytics.getInstance(this).setUserProperty("transportID", String.valueOf(MoveitId));
        FirebaseAnalytics.getInstance(this).setUserProperty("transportID", String.valueOf(orderID));

        String path = getString(R.string.firebase_path) + String.valueOf(orderID);



        //raw-location/153

        // mFirebaseTransportRef = FirebaseDatabase.getInstance().getReference(path);

        // Manually configure Firebase Options
        *//*FirebaseOptions options = new FirebaseOptions.Builder()
                .setDatabaseUrl("https://tovologies-1550475998119.firebaseio.com") // Required for RTDB.
                .build();


        // Initialize with secondary app.
        FirebaseApp.initializeApp(this *//**//* Context *//**//*, options, "secondary");

// Retrieve secondary app.
        FirebaseApp secondary = FirebaseApp.getInstance("secondary");*//*
// Get the database for the other app.
        mFirebaseTransportRef = FirebaseDatabase.getInstance("https://tovologies-1550475998119.firebaseio.com").getReference(path);

        // mFirebaseTransportRef = FirebaseDatabase.getInstance().getReference(path);


        //   mFirebaseTransportRef = FirebaseDatabase.getInstance("https://tovologies-1550475998119.firebaseio.com").getReference(path);


        mFirebaseTransportRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot != null) {
                    for (DataSnapshot transportStatus : snapshot.getChildren()) {
                        mTransportStatuses.add(Integer.parseInt(transportStatus.getKey()),
                                (Map<String, Object>) transportStatus.getValue());
                    }
                }
                if (mTransportStatuses.size() != 0) {

                    Map<String, Object> status = mTransportStatuses.get(0);
                    Location locationForStatus = new Location("");
                    locationForStatus.setLatitude((double) status.get("lat"));
                    locationForStatus.setLongitude((double) status.get("lng"));
                    LatLng latLng = new LatLng((double) status.get("lat"), (double) status.get("lng"));

                    if (distance(cusLatLng.latitude, cusLatLng.longitude, (double) status.get("lat"), (double) status.get("lng"), "K") <= 2) {


                        mOrderTrackingViewModel.orderDeliveryStatus.set("Your food is almost there");

                        if (moveitLocationMarker == null) {
                            moveitLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(latLng));
                        }

                        showMarker1(latLng);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // TODO: Handle gracefully
            }
        });*/
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


}

