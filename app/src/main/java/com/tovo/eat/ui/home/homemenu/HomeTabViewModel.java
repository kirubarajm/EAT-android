package com.tovo.eat.ui.home.homemenu;


import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class HomeTabViewModel extends BaseViewModel<HomeTabNavigator> {

    public final ObservableField<String> addressTitle = new ObservableField<>();

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

        getNavigator().favourites();


    }


}
