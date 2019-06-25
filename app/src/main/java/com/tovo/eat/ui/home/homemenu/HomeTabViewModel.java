package com.tovo.eat.ui.home.homemenu;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
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
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.list.RegionListRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeTabViewModel extends BaseViewModel<HomeTabNavigator> {





    public ObservableList<KitchenResponse.Result> kitchenItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<KitchenResponse.Result>> kitchenItemsLiveData;


    public ObservableList<KitchenResponse.Result> getKitchenItemViewModels() {
        return kitchenItemViewModels;
    }

    public void setKitchenItemViewModels(ObservableList<KitchenResponse.Result> kitchenItemViewModels) {
        this.kitchenItemViewModels = kitchenItemViewModels;
    }

    public MutableLiveData<List<KitchenResponse.Result>> getKitchenItemsLiveData() {
        return kitchenItemsLiveData;
    }

    public void setKitchenItemsLiveData(MutableLiveData<List<KitchenResponse.Result>> kitchenItemsLiveData) {
        this.kitchenItemsLiveData = kitchenItemsLiveData;
    }

    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {
        kitchenItemViewModels.clear();
        kitchenItemViewModels.addAll(ordersItems);
    }




    public ObservableList<RegionsResponse.Result> regionItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<RegionsResponse.Result>> regionItemsLiveData;

    RegionSearchModel regionSearchModel = new RegionSearchModel();













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

    public void addRegionItemsToList(List<RegionsResponse.Result> ordersItems) {
        regionItemViewModels.clear();
        regionItemViewModels.addAll(ordersItems);
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

                        try {


                            regionItemsLiveData.setValue(response.getResult());

                            Log.e("Region----response:", response.toString());




                        }catch (Exception ee){

                            ee.printStackTrace();

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());


                }
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

            ee.printStackTrace();

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



    public final ObservableField<String> addressTitle = new ObservableField<>();

    public ObservableBoolean favIcon=new ObservableBoolean();



    public HomeTabViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenItemsLiveData = new MutableLiveData<>();
        regionItemsLiveData = new MutableLiveData<>();
        fetchRepos();
        fetchRepos(0);
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






    public void fetchRepos() {

        // getNavigator().kitchenListLoading();


        if (getDataManager().getCurrentLat() == null) {

            //   getNavigator().kitchenListLoading();



        } else {


            if (!getDataManager().getIsFav()) {

/*
            if (getDataManager().getCurrentLat() == null) {

                //   getNavigator().noAddressFound1();
                getNavigator().kitchenListLoaded();

            } else {*/


                //    getNavigator().addressAdded1();


                if (!MvvmApp.getInstance().onCheckNetWork()) {

                   // getNavigator().kitchenListLoaded();
                    return;

                } else {

                    FilterRequestPojo filterRequestPojo;

                    if (getDataManager().getFilterSort() != null) {

                        Gson sGson = new GsonBuilder().create();
                        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

                        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                        filterRequestPojo.setLat(getDataManager().getCurrentLat());
                        filterRequestPojo.setLon(getDataManager().getCurrentLng());

                        if (filterRequestPojo.getCusinelist() != null) {

                            if (filterRequestPojo.getCusinelist().size() == 0)
                                filterRequestPojo.setCusinelist(null);
                        }

                        if (filterRequestPojo.getRegionlist() != null) {

                            if (filterRequestPojo.getRegionlist().size() == 0)
                                filterRequestPojo.setRegionlist(null);
                        }


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
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_LIST_URL, new JSONObject(getDataManager().getFilterSort()), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (response != null) {
                                    KitchenResponse kitchenResponse;
                                    Gson sGson = new GsonBuilder().create();
                                    kitchenResponse = sGson.fromJson(response.toString(), KitchenResponse.class);

                                    kitchenItemsLiveData.setValue(kitchenResponse.getResult());
                                    Log.e("Kitchen----response:", response.toString());


                                //    getNavigator().kitchenListLoaded();

                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                              //   Log.e("", ""+error.getMessage());
                           //     getNavigator().kitchenListLoaded();
                            }
                        }) {

                            /**
                             * Passing some request headers
                             */
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
                    } catch (Exception ee){

                        ee.printStackTrace();

                    }


                }


            } else {

                try {
                    setIsLoading(true);
                    GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_FAV_KITCHEN_LIST_URL + getDataManager().getCurrentUserId(), KitchenResponse.class, new Response.Listener<KitchenResponse>() {
                        @Override
                        public void onResponse(KitchenResponse response) {
                            if (response != null) {
                                kitchenItemsLiveData.setValue(response.getResult());
                            //    Log.e("----response:---------", response.toString());
                           //     getNavigator().kitchenListLoaded();

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                       //     Log.e("", error.getMessage());
                       //     getNavigator().kitchenListLoaded();
                        }
                    });


                    MvvmApp.getInstance().addToRequestQueue(gsonRequest);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception ee){

                    ee.printStackTrace();

                }
            }
        }
    }


}
