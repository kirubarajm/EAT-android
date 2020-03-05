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
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.home.homemenu.dish.DishFavRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import java.util.ArrayList;
import java.util.List;


public class KitchenImageItemViewModel {


    public final ObservableField<String> headerName = new ObservableField<>();
    public final ObservableField<String> icon = new ObservableField<>();

    private final KitchenDetailsResponse.KitchenPage headers;

    public KitchenImageItemViewModel(KitchenDetailsResponse.KitchenPage headers) {

        this.headers = headers;
        headerName.set(headers.getHeaderContent());
        icon.set(headers.getHeaderIconUrl());


    }



}
