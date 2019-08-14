package com.tovo.eat.ui.address.edit;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityEditAddressBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class EditAddressActivity extends BaseActivity<ActivityEditAddressBinding, EditAddressViewModel> implements EditAddressNavigator, LocationListener {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public ActivityEditAddressBinding mActivityEditAddressBinding;
    @Inject
    public EditAddressViewModel mEditAddressViewModel;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    LatLng lastPosition;
    CardView cardView;
    boolean isFirstTime;
    Location mLocation;
    Integer aid;
    boolean isGPS;


    public static Intent newIntent(Context context) {

        return new Intent(context, EditAddressActivity.class);
    }


    @Override
    public int getBindingVariable() {
        return BR.editAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_edit_address;
    }

    @Override
    public EditAddressViewModel getViewModel() {

        return mEditAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void addressSaved() {

        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void emptyFields() {


        Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void myLocationn() {
        if (mLocation != null) {
            LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            initCameraIdle();
        }
    }

    @Override
    public void setLatLng(double lat, double lng) {

        if (map != null) {
            LatLng latLng = new LatLng(lat, lng);
            lastPosition = latLng;
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
        }


    }

    @Override
    public void goBack() {
        onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityEditAddressBinding = getViewDataBinding();
        mEditAddressViewModel.setNavigator(this);
        //  startLocationTracking();

        isFirstTime = true;

        /*buildGoogleAPIClient();*/

        initCameraIdle();

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                isGPS = isGPSEnable;
            }
        });



        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            aid = intent.getExtras().getInt("aid");
        }


        mEditAddressViewModel.fetchAddress(aid);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                if (lastPosition != null)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPosition, 12));
            }
        });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);


    }

    private void initCameraIdle() {
        map.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                center = map.getCameraPosition().target;
                getAddressFromLocation(center.latitude, center.longitude);
            }
        });
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);


        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);

                mEditAddressViewModel.locationAddress.set(fetchedAddress.getAddressLine(0));

                mEditAddressViewModel.area.set(fetchedAddress.getSubLocality());
                mEditAddressViewModel.house.set(fetchedAddress.getFeatureName());


                mEditAddressViewModel.saveAddress(String.valueOf(fetchedAddress.getLatitude()), String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getPostalCode(), aid);


                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");

                }


                //  mEditAddressViewModel.locationAddress.set(strAddress.toString());
                //    txtLocationAddress.setText(strAddress.toString());

            } else {
                //   txtLocationAddress.setText("Searching Current Address");
            }

        } catch (IOException e) {
            e.printStackTrace();
            printToast("Could not get address..!");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (!place.getAddress().toString().contains(place.getName())) {


                    // mEditAddressViewModel.locationAddress.set(place.getAddress().toString());


                    //   txtLocationAddress.setText(place.getName() + ", " + place.getAddress());
                } else {
                    //   txtLocationAddress.setText(place.getAddress());
                }

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16);
                map.animateCamera(cameraUpdate);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                printToast("Error in retrieving place info");

            }
        }
    }

    private void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;

        //  if (location != null)
        //  center = new LatLng(location.getLatitude(), location.getLongitude());


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    protected void onResume() {
        super.onResume();
        mEditAddressViewModel.fetchAddress(aid);
        registerWifiReceiver();
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


    private  boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

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

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(MvvmApp.getInstance());
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
    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


}