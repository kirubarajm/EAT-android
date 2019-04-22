package com.tovo.eat.ui.track;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import com.tovo.eat.utilities.LatLngInterpolator;
import com.tovo.eat.utilities.MarkerAnimation;

import org.joda.time.DateTime;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;


public class OrderTrackingActivity extends BaseActivity<ActivityOrderTrackingBinding, OrderTrackingViewModel> implements
        OrderTrackingNavigator, OnMapReadyCallback {


    private static final String TAG = OrderTrackingActivity.class.getSimpleName();
    private static final int overview = 0;
    @Inject
    OrderTrackingViewModel mOrderTrackingViewModel;
    ActivityOrderTrackingBinding mActivityDirectionBinding;
    Bitmap origin_marker, destination_marker;
    double deliveryBoyLat;
    double deliveryBoyLng;
    LatLng moveitLatLng;
    LatLng cusLatLng;
    private Marker currentLocationMarker;
    private Marker customerLocationMarker;
    private GoogleMap mMap;
    private boolean firstTimeFlag = true;
    private DatabaseReference mFirebaseTransportRef;
    private LinkedList<Map<String, Object>> mTransportStatuses = new LinkedList<>();
    private FirebaseRemoteConfig mFirebaseRemoteConfig;


    boolean liveTracking=true;


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
        origin_marker = scaleBitmap(oBitmap, 114, 200);

        Drawable dDrawable = getResources().getDrawable(R.drawable.kitchen_map_marker);
        Bitmap dBitmap = ((BitmapDrawable) dDrawable).getBitmap();
        destination_marker = scaleBitmap(dBitmap, 114, 200);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDirectionBinding = getViewDataBinding();
        mOrderTrackingViewModel.setNavigator(this);

        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);


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

        if (!liveTracking) {
            liveTracking= true;
            currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(moveitLatLng));
            moveToCurrentLocation(moveitLatLng);
            customerLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(destination_marker)).position(cusLatLng));
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
                    currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(latLng));
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


    private void showMarker1(LatLng currentLocation) {

        if (currentLocationMarker == null)
            currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(currentLocation));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, currentLocation, new LatLngInterpolator.Spherical());

    }


    private void showMarker(Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        // googleMap.clear();
        if (currentLocationMarker == null)
            currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(latLng));
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMap = null;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        //startService(new Intent(OrderTrackingActivity.this, TrackerService.class));

        //startLocationTracking();

        super.onResume();
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

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);

    }

    @Override
    public void tracking(String cusLat, String cusLng, double moveitLat, double moveitLng) {


        moveitLatLng = new LatLng(moveitLat, moveitLng);
        cusLatLng = new LatLng(Double.parseDouble(cusLat), Double.parseDouble(cusLng));


        liveTracking=false;

        if (mMap != null) {
            liveTracking= true;
            currentLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(origin_marker)).position(moveitLatLng));
            moveToCurrentLocation(moveitLatLng);
            customerLocationMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(destination_marker)).position(cusLatLng));
        }
    }

    @Override
    public void orderPickedUp(Integer MoveitId) {


        loadPreviousStatuses(MoveitId);

    }

    @Override
    public void clickBack() {
        finish();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }


    private void loadPreviousStatuses(Integer MoveitId) {
        //  String transportId = mPrefs.getString(getString(R.string.transport_id), "");
        // FirebaseAnalytics.getInstance(this).setUserProperty("transportID", String.valueOf(MoveitId));
        FirebaseAnalytics.getInstance(this).setUserProperty("transportID", String.valueOf(MoveitId));
        String path = getString(R.string.firebase_path) + String.valueOf(MoveitId);
        mFirebaseTransportRef = FirebaseDatabase.getInstance().getReference(path);
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


                    showMarker1(latLng);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // TODO: Handle gracefully
            }
        });
    }

    private boolean locationIsAtStatus(Location location, int statusIndex) {
        if (mTransportStatuses.size() <= statusIndex) {
            return false;
        }
        Map<String, Object> status = mTransportStatuses.get(statusIndex);
        Location locationForStatus = new Location("");
        locationForStatus.setLatitude((double) status.get("lat"));
        locationForStatus.setLongitude((double) status.get("lng"));
        float distance = location.distanceTo(locationForStatus);
        Log.d(TAG, String.format("Distance from status %s is %sm", statusIndex, distance));
        return distance < mFirebaseRemoteConfig.getLong("LOCATION_MIN_DISTANCE_CHANGED");
    }


    private void requestLocationUpdates(String MoveitId) {
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        final String path = getString(R.string.firebase_path) + "/" + getString(R.string.transport_id);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();

                    if (location != null) {
                        Log.e("", "location update " + location);
                        ref.setValue(location);

                        String filter = "location";
                        Intent intent = new Intent(filter);
                        intent.putExtra("lat", location.getLatitude());
                        intent.putExtra("lon", location.getLongitude());
                        intent.putExtra("SSS", "String value");
                        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                    }
                }
            }, null);
        }


    }

}

