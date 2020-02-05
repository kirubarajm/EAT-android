package com.tovo.eat.ui.home.ad.bottom;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.CusineResponse;
import com.tovo.eat.utilities.MasterPojo;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class PromotionViewModel extends BaseViewModel<PromotionNavigator> {


    public final ObservableField<String> url = new ObservableField<>();
    public final ObservableField<Integer> selectedOptions = new ObservableField<>();
    public ObservableBoolean isSortClicked = new ObservableBoolean();
    public ObservableBoolean isRegionalClicked = new ObservableBoolean();
    public ObservableBoolean isCuisinesClicked = new ObservableBoolean();

    List<CusineResponse.Result> cuisinelists = new ArrayList<>();
    List<MasterPojo.Sort> sorts = new ArrayList<>();
    List<MasterPojo.Regionlist> regionlists = new ArrayList<>();


    public PromotionViewModel(DataManager dataManager) {
        super(dataManager);
       // url.set("https://www.whynotdeals.com//wp-content/uploads//2016/07/what-to-eat-delivery-singapore-promotion-free-delivery-ends-31-dec-2016_why-not-deals-2-e1469516136292.jpg");
        url.set("https://eattovo.s3.amazonaws.com/upload/admin/makeit/product/1578500485888-Infinity%20regions-40.jpg");

    }


    public void closeDialog(){
        getNavigator().closeDialog();
    }

}
