package com.tovo.eat.ui.search;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.ArrayList;
import java.util.List;


public class SearchItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> type = new ObservableField<>();

    public final ObservableBoolean isFavourite = new ObservableBoolean();

    public final SearchItemViewModelListener mListener;
    private final SearchResponse.Result result;


    public SearchItemViewModel(SearchItemViewModelListener mListener, SearchResponse.Result result) {

        this.mListener = mListener;
        this.result = result;
        this.title.set(result.getName());


        if (result.getType() == 1) {

            this.type.set("DISH");


        } else if (result.getType() == 2) {
            this.type.set("KITCHEN");


        } else if (result.getType() == 3) {
            this.type.set("REGION");
        }

    }


    public void onItemClick() {
        mListener.onItemClick(result);

    }

    public interface SearchItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick(SearchResponse.Result result);


    }

}
