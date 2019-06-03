package com.tovo.eat.ui.search;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.address.add.AddressResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class SearchViewModel extends BaseViewModel<SearchNavigator> {


    public ObservableList<SearchResponse.Result> searchItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<SearchResponse.Result>> searchItemsLiveData;


    public SearchViewModel(DataManager dataManager) {
        super(dataManager);
        searchItemsLiveData = new MutableLiveData<>();

    }

    public ObservableList<SearchResponse.Result> getSearchItemViewModels() {
        return searchItemViewModels;
    }

    public void setSearchItemViewModels(ObservableList<SearchResponse.Result> searchItemViewModels) {
        this.searchItemViewModels = searchItemViewModels;
    }

    public MutableLiveData<List<SearchResponse.Result>> getSearchItemsLiveData() {
        return searchItemsLiveData;
    }

    public void setSearchItemsLiveData(MutableLiveData<List<SearchResponse.Result>> searchItemsLiveData) {
        this.searchItemsLiveData = searchItemsLiveData;
    }



    public void addSearchItemsToList(List<SearchResponse.Result> ordersItems) {
        searchItemViewModels.clear();
        searchItemViewModels.addAll(ordersItems);

    }


    public void fetchRepos(String data) {




        if (!MvvmApp.getInstance().onCheckNetWork()) return;


            setIsLoading(true);

            try {
                setIsLoading(true);
                GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_EXPLORE_SEARCH, SearchResponse.class, new SearchRequest(data), new Response.Listener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse response) {
                        

                        searchItemsLiveData.setValue(response.getResult());
                        getNavigator().listLoaded();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
                MvvmApp.getInstance().addToRequestQueue(gsonRequest);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }

    }

}



