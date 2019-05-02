package com.tovo.eat.ui.orderplaced;

import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class OrderPlacedViewModel extends BaseViewModel<OrderPlacedNavigator> {


    public OrderPlacedViewModel(DataManager dataManager) {
        super(dataManager);

    }



}
