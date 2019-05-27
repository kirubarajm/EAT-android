package com.tovo.eat.ui.home.region;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.region.list.RegionListRequest;
import com.tovo.eat.ui.track.OrderTrackingResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionViewModel extends BaseViewModel<RegionNavigator> {


    public ObservableList<RegionsResponse.Result> regionItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<RegionsResponse.Result>> regionItemsLiveData;

    RegionSearchModel regionSearchModel = new RegionSearchModel();

    public RegionViewModel(DataManager dataManager) {
        super(dataManager);
        regionItemsLiveData = new MutableLiveData<>();
        fetchRepos(0);
    }

    public ObservableList<RegionsResponse.Result> getregionItemViewModels() {
        return regionItemViewModels;
    }

    public void setregionItemViewModels(ObservableList<RegionsResponse.Result> regionItemViewModels) {
        this.regionItemViewModels = regionItemViewModels;
    }

    public MutableLiveData<List<RegionsResponse.Result>> getregionItemsLiveData() {
        return regionItemsLiveData;
    }

    public void setregionItemsLiveData(MutableLiveData<List<RegionsResponse.Result>> regionItemsLiveData) {
        this.regionItemsLiveData = regionItemsLiveData;
    }

    public void addKitchenItemsToList(List<RegionsResponse.Result> ordersItems) {
        regionItemViewModels.clear();
        regionItemViewModels.addAll(ordersItems);
    }


    public void saveMakeitId(Integer id) {


        getDataManager().kitchenId(id);

    }


    public void regionList() {

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER_REGION_LIST, RegionSearchModel.class, new Response.Listener<RegionSearchModel>() {
                @Override
                public void onResponse(RegionSearchModel response) {


                    regionSearchModel = response;

                    getNavigator().searchLoaded(response);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());


                }
            });


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void fetchRepos(Integer regionId) {


     //   if (getDataManager().getCurrentLat() == null) {

            //   getNavigator().kitchenListLoading();


            try {
                setIsLoading(true);
                GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_REGION_LIST, RegionsResponse.class, new RegionListRequest(getDataManager().getCurrentLat(),getDataManager().getCurrentLng(),getDataManager() .getCurrentUserId(),regionId), new Response.Listener<RegionsResponse>() {
                    @Override
                    public void onResponse(RegionsResponse response) {
                        if (response != null) {

                            regionItemsLiveData.setValue(response.getResult());

                            Log.e("Region----response:", response.toString());


                            getNavigator().kitchenListLoaded();


                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Log.e("", error.getMessage());
                        getNavigator().kitchenListLoaded();
                    }
                });

                MvvmApp.getInstance().addToRequestQueue(gsonRequest);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }



       /*
        } else {
            if (!MvvmApp.getInstance().onCheckNetWork()) {

                getNavigator().kitchenListLoaded();
                return;

            } else {

                FilterRequestPojo filterRequestPojo;

                if (getDataManager().getFilterSort() != null) {

                    Gson sGson = new GsonBuilder().create();
                    filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);
                    filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                    filterRequestPojo.setLat(getDataManager().getCurrentLat());
                    filterRequestPojo.setLon(getDataManager().getCurrentLng());


                    Gson gson = new Gson();
                    String json = gson.toJson(filterRequestPojo);
                    getDataManager().setFilterSort(json);
                } else {
                    filterRequestPojo = new FilterRequestPojo();

                    filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                    filterRequestPojo.setLat(getDataManager().getCurrentLat());
                    filterRequestPojo.setLon(getDataManager().getCurrentLng());

                    Gson gson = new Gson();
                    String json = gson.toJson(filterRequestPojo);
                    getDataManager().setFilterSort(json);
                }

                try {
                    setIsLoading(true);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_REGION_LIST, new JSONObject(getDataManager().getFilterSort()), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (response != null) {
                                RegionsResponse regionsResponse;
                                Gson sGson = new GsonBuilder().create();
                                regionsResponse = sGson.fromJson(response.toString(), RegionsResponse.class);

                                regionItemsLiveData.setValue(regionsResponse.getResult());

                                Log.e("Kitchen----response:", response.toString());


                                getNavigator().kitchenListLoaded();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Log.e("", error.getMessage());
                            getNavigator().kitchenListLoaded();
                        }
                    }) {

                        *//**
                         * Passing some request headers
                         *//*
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }
                    };

                    MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (JSONException j) {
                    j.printStackTrace();
                }


            }*/

       // }
    }
}
