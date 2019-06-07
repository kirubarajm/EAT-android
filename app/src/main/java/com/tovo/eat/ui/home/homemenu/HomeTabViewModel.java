package com.tovo.eat.ui.home.homemenu;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class HomeTabViewModel extends BaseViewModel<HomeTabNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

    public ObservableBoolean favIcon=new ObservableBoolean();



    public HomeTabViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void selectAddress() {
        getNavigator().selectAddress();

    }

    public String updateAddressTitle() {
        addressTitle.set(getDataManager().getCurrentAddressTitle());
        return getDataManager().getCurrentAddressTitle();

    }


    public void filter() {
        getNavigator().filter();
    }



    public void setCurrentFragment(Integer id){
        getDataManager().setCurrentFragment(id);
    }

    public void favourites(){
        getDataManager().setIsFav(true);
        getNavigator().favourites();
    }



    public boolean isAddressAdded() {

        if (getDataManager().getAddressId() == 0) {

            return false;
        } else {
            favIcon.set(true);
            return true;
        }

    }

    public void currentLatLng(double lat, double lng) {
        if (getDataManager().getAddressId() == 0) {
            getDataManager().setCurrentAddressTitle("Current location");
            getDataManager().setCurrentLat(lat);
            getDataManager().setCurrentLng(lng);
            getNavigator().disconnectGps();
            getNavigator().loaded();
            addressTitle.set("Current location");
        }

    }
}
