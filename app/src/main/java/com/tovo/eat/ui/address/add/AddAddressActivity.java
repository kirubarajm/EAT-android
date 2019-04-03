package com.tovo.eat.ui.address.add;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import com.tovo.eat.databinding.ActivityAddAddressBinding;
import com.tovo.eat.ui.base.BaseActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

public class AddAddressActivity extends BaseActivity<ActivityAddAddressBinding, AddAddressViewModel> implements AddAddressNavigator {


    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    public ActivityAddAddressBinding mActivityAddAddressBinding;
    @Inject
    public AddAddressViewModel mAddAddressViewModel;

    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng center;
    CardView cardView;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityAddAddressBinding = getViewDataBinding();
        mAddAddressViewModel.setNavigator(this);


        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.getUiSettings().setZoomControlsEnabled(true);
                LatLng latLng = new LatLng(13.007479, 80.206195);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                initCameraIdle();
            }
        });


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

                mAddAddressViewModel.area.set(fetchedAddress.getLocality());

                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i < fetchedAddress.getMaxAddressLineIndex(); i++) {
                    strAddress.append(fetchedAddress.getAddressLine(i)).append(" ");

                }


              //  mAddAddressViewModel.locationAddress.set(strAddress.toString());
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


                   // mAddAddressViewModel.locationAddress.set(place.getAddress().toString());


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

}