package com.tovo.eat.ui.address.add;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.MvvmApp;
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
    protected android.location.LocationListener locationListener;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    CardView cardView;
    boolean isFirstTime;
    Location mLocation;
    boolean isGPS;

    ProgressDialog dialog;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MvvmApp.getInstance().startActivity(inIntent);

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
        finish();
        hideKeyboard();
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
        } else {
            if (mAddAddressViewModel.getDataManager().getAddressId() != 0 && mAddAddressViewModel.getDataManager().getCurrentLat() != null) {

                LatLng latLng = new LatLng(Double.parseDouble(mAddAddressViewModel.getDataManager().getCurrentLat()), Double.parseDouble(mAddAddressViewModel.getDataManager().getCurrentLng()));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                initCameraIdle();

            } else {
                turnOnGps();
            }
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddAddressBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);
        //  startLocationTracking();

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Getting your location..");
        dialog.setTitle("Please Wait!");
        // dialog.show();

        isFirstTime = true;

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);


                if (mAddAddressViewModel.getDataManager().getAddressId() != 0 && mAddAddressViewModel.getDataManager().getCurrentLat() != null) {

                    LatLng latLng = new LatLng(Double.parseDouble(mAddAddressViewModel.getDataManager().getCurrentLat()), Double.parseDouble(mAddAddressViewModel.getDataManager().getCurrentLng()));
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    initCameraIdle();

                } else {
                    turnOnGps();
                }
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
                        //getLocation();
                        if (!dialog.isShowing())
                            dialog.show();

                        if (map != null) {
                            Location location = getLocation();


                            //  getUserLocation();

                           /* if (location != null) {

                                if (dialog.isShowing())
                                    dialog.dismiss();

                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                initCameraIdle();
                            }*/
                            //  initCameraIdle();
                        }

                    }
                } else {

                    turnOnGps();
                }
            }
        });

    }


    private void getUserLocation() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {

                                if (dialog.isShowing())
                                    dialog.dismiss();

                                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                initCameraIdle();


                              /*  latitude = location.getLatitude();
                                longitude = location.getLongitude();*/
                            }
                        }
                    });
        }
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppConstants.GPS_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    /*locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);*/

//                    getLocation();


                    turnOnGps();

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
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

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);


        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);

                mAddAddressViewModel.locationAddress.set(fetchedAddress.getAddressLine(0));

                mAddAddressViewModel.area.set(fetchedAddress.getSubLocality());
                mAddAddressViewModel.house.set(fetchedAddress.getFeatureName());


                mAddAddressViewModel.saveAddress(String.valueOf(fetchedAddress.getLatitude()), String.valueOf(fetchedAddress.getLongitude()), fetchedAddress.getPostalCode());


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
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public Location getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);

                return null;

            }

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            Location lastKnownLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                return lastKnownLocationGPS;
            } else {
                Location loc = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                return loc;
            }
        } else {
            return null;
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
        if (requestCode == AppConstants.GPS_REQUEST) {

            if (resultCode == Activity.RESULT_OK) {


                if (!dialog.isShowing())
                    dialog.show();

                turnOnGps();


            } else {
                turnOnGps();
            }


        }
    }


    private void printToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        mLocation = location;

        if (location != null) {

            /*if (dialog.isShowing())
                dialog.dismiss();*/

            if (isFirstTime) {
                if (dialog.isShowing())
                    dialog.dismiss();
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                initCameraIdle();
                isFirstTime = false;
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

}