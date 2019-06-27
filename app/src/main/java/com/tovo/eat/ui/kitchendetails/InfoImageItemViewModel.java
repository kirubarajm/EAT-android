package com.tovo.eat.ui.kitchendetails;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.ui.home.homemenu.dish.DishFavRequest;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.ArrayList;
import java.util.List;


public class InfoImageItemViewModel {

    public final ObservableField<String> image_url = new ObservableField<>();


    public final ObservableBoolean isFavourite = new ObservableBoolean();

    public final DishItemViewModelListener mListener;
    private final KitchenDishResponse.Productlist dishList;
    private final KitchenDishResponse.Result originalResult;


    List<CartRequestPojo.Cartitem> results = new ArrayList<>();
    CartRequestPojo cartRequestPojo = new CartRequestPojo();
    CartRequestPojo.Cartitem cartRequestPojoCartitem = new CartRequestPojo.Cartitem();

    KitchenDishResponse.Result response = new KitchenDishResponse.Result();


    Integer favID;


    public InfoImageItemViewModel(DishItemViewModelListener mListener, KitchenDishResponse.Productlist dishList, KitchenDishResponse.Result response) {

        this.originalResult = response;
        this.mListener = mListener;
        this.dishList = dishList;
        this.response = response;

        image_url.set(dishList.getProductimage());


    }



    public void onItemClick() {
        mListener.onItemClick();

    }

    public interface DishItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick();

    }

}
