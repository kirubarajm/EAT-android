package com.tovo.eat.ui.address.add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
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
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.SingleShotLocationProvider;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding, AddAddressViewModel> implements AddAddressNavigator, LocationListener {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public ActivityAddAddressBinding mActivityAddAddressBinding;
    @Inject
    public AddAddressViewModel mAddAddressViewModel;
    protected LocationManager locationManager;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    Location mLocation;
    Dialog locationDialog;
    ProgressDialog dialog;
    FusedLocationProviderClient fusedLocationClient;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ADD_ADDRESS;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(AddAddressActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, AddAddressActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.addAddressViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_address;
    }

    @Override
    public AddAddressViewModel getViewModel() {

        return mAddAddressViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void addressSaved() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_SAVE);
        finish();
        hideKeyboard();
    }

    @Override
    public void emptyFields() {
        Toast.makeText(this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myLocationn() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_ADDRESS_CURRENT_LOCATION);
        turnOnGps();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showLocationDialog() {
        locationDialog = new Dialog(this);
        locationDialog.setCancelable(false);
        locationDialog.setContentView(R.layout.dialog_get_location);

        ButtonTextView text = locationDialog.findViewById(R.id.allowgps);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDialog.dismiss();
                turnOnGps();

            }
        });

        ButtonTextView dialogButton = locationDialog.findViewById(R.id.cancelgps);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDialog.dismiss();
                finish();

            }
        });

        locationDialog.show();

    }


    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddAddressBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);
        //  startLocationTracking();


        analytics = new Analytics(this, pageName);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setMessage("Getting your location..");
        dialog.setTitle("Please Wait!");
        // dialog.show();


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                turnOnGps();
                initCameraIdle();
            }
        });


    }

    public void turnOnGps() {

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                if (isGPSEnable) {
                    if (ActivityCompat.checkSelfPermission(AddAddressActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AddAddressActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddAddressActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);
                    } else {
                        if (!dialog.isShowing())
                            dialog.show();
                        getUserLocation();
                    }
                } else {
                    showLocationDialog();
                }
            }
        });

    }

    private void getUserLocation() {

        SingleShotLocationProvider.requestSingleUpdate(AddAddressActivity.this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                        if (dialog.isShowing())
                            dialog.dismiss();
                        if (location != null)
                            if (map != null) {
                                LatLng latLng = new LatLng(location.latitude, location.longitude);
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                initCameraIdle();
                            }
                    }
                });
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case AppConstants.GPS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    turnOnGps();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);

                }
                return;
            }
        }
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
        new AsyncTaskAddress().execute(latitude, longitude);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (!place.getAddress().toString().contains(place.getName())) {

                }

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16);
                map.animateCamera(cameraUpdate);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                printToast("Error in retrieving place info");

            }
        } else if (requestCode == AppConstants.GPS_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {


                if (!dialog.isShowing())
                    dialog.show();

                turnOnGps();


            } else {

                showLocationDialog();

            }


        } else if (requestCode == AppConstants.INTERNET_ERROR_REQUEST_CODE) {


        }
    }

    private void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;
        if (map != null) {
            if (location != null) {
                if (dialog.isShowing())
                    dialog.dismiss();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                initCameraIdle();
                if (locationManager != null)
                    locationManager.removeUpdates(this);
            }
        }
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
        registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        if (dialog.isShowing()) dialog.dismiss();
        super.onBackPressed();

    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private class AsyncTaskAddress extends AsyncTask<Double, Address, Address> {


        @Override
        protected Address doInBackground(Double... doubles) {
            Geocoder geocoder = new Geocoder(AddAddressActivity.this, Locale.ENGLISH);

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(doubles[0], doubles[1], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null)
                if (addresses.size() > 0) {
                    Address fetchedAddress = addresses.get(0);

                    return fetchedAddress;

                }


            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Address fetchedAddress) {
            super.onPostExecute(fetchedAddress);


            if (fetchedAddress != null) {









               /* String address = fetchedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = fetchedAddress.getLocality();
                String state = fetchedAddress.getAdminArea();
                String country = fetchedAddress.getCountryName();
                String postalCode = fetchedAddress.getPostalCode();
                String knownName = fetchedAddress.getFeatureName();


                Toast.makeText(AddAddressActivity.this, address+"\n"+city+"\n"+state+"\n"+country+"\n"+postalCode+"\n"+knownName, Toast.LENGTH_LONG).show();*/





                mAddAddressViewModel.locationAddress.set(fetchedAddress.getAddressLine(0));

                mAddAddressViewModel.area.set(fetchedAddress.getSubLocality());
                //    mAddAddressViewModel.house.set(fetchedAddress.getFeatureName());


                mAddAddressViewModel.saveAddress(String.valueOf(fetchedAddress.getLatitude()), String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getPostalCode());


                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");

                }

            } else {
                printToast("Unable to find your address please mark your location on map..");
            }

        }
    }


}