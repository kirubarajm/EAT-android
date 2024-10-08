package com.tovo.eat.ui.home.homemenu.dish;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
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
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DishViewModel extends BaseViewModel<DishNavigator> {


    public ObservableList<DishResponse.Result> dishItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<DishResponse.Result>> dishItemsLiveData;

    public ObservableBoolean emptyDish=new ObservableBoolean();




    public DishViewModel(DataManager dataManager) {
        super(dataManager);
        dishItemsLiveData = new MutableLiveData<>();


        fetchRepos();
    }

    public ObservableList<DishResponse.Result> getKitchenItemViewModels() {
        return dishItemViewModels;
    }

    public void setKitchenItemViewModels(ObservableList<DishResponse.Result> kitchenItemViewModels) {
        this.dishItemViewModels = kitchenItemViewModels;
    }

    public MutableLiveData<List<DishResponse.Result>> getKitchenItemsLiveData() {
        return dishItemsLiveData;
    }

    public void setKitchenItemsLiveData(MutableLiveData<List<DishResponse.Result>> kitchenItemsLiveData) {
        this.dishItemsLiveData = kitchenItemsLiveData;
    }

    public void addDishItemsToList(List<DishResponse.Result> ordersItems) {
        dishItemViewModels.clear();
        dishItemViewModels.addAll(ordersItems);

    }


    public String datas() {
        return getDataManager().getCartDetails();
    }


    public void removeFavourite(Integer favId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL + favId, CommonResponse.class, null, new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        getNavigator().toastMessage(response.getMessage());


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            },AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }

    }


    public void addFavourite(Integer dishId, String fav) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new DishFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(dishId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        getNavigator().toastMessage(response.getMessage());


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            },AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }


    }


    public String getCartPojoDetails() {

        return getDataManager().getCartDetails();
    }

    public void fetchRepos() {


//        getNavigator().dishLoading();

        String json=null;
        if (getDataManager().getCurrentLat() == null) {

            //   getNavigator().kitchenListLoading();


        } else {


            if (getDataManager().getCurrentLat() == null) {


            } else {


                if (!getDataManager().getIsFav()) {

/*
            if (getDataManager().getCurrentLat() == null) {

                // getNavigator().noAddressFound();
                getNavigator().dishListLoaded();

            } else {*/
                    //   getNavigator().addressAdded();
                    if (!MvvmApp.getInstance().onCheckNetWork()) return;

                    //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

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
                         json = gson.toJson(filterRequestPojo);

                    } else {
                        filterRequestPojo = new FilterRequestPojo();

                        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                        filterRequestPojo.setLat(getDataManager().getCurrentLat());
                        filterRequestPojo.setLon(getDataManager().getCurrentLng());

                        Gson gson = new Gson();
                         json = gson.toJson(filterRequestPojo);
                    }

                    try {
                        setIsLoading(true);
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_DISH_LIST_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                if (response != null) {
                                    DishResponse dishResponse;
                                    Gson sGson = new GsonBuilder().create();
                                    dishResponse = sGson.fromJson(response.toString(), DishResponse.class);

                                    if (dishResponse.getResult().size()>0){
                                        dishItemsLiveData.setValue(dishResponse.getResult());
                                        emptyDish.set(false);
                                    }else {
                                        emptyDish.set(true);

                                    }



                                    Log.e("----response:---------", response.toString());
                                    getNavigator().dishListLoaded();


                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Log.e("", error.getMessage());
                                emptyDish.set(true);
                                getNavigator().dishListLoaded();
                            }
                        }) {

                            /**
                             * Passing some request headers
                             */
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {

                                return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);

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


                } else {

                    try {
                        setIsLoading(true);
                        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_FAV_DISH_LIST_URL + getDataManager().getCurrentUserId(), DishResponse.class, new Response.Listener<DishResponse>() {
                            @Override
                            public void onResponse(DishResponse response) {
                                if (response != null) {


                                    if (response.getResult().size()>0){
                                        dishItemsLiveData.setValue(response.getResult());
                                        emptyDish.set(false);

                                    }else {

                                        emptyDish.set(true);
                                    }


                                    Log.e("----response:---------", response.toString());
                                  getNavigator().dishListLoaded();

                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Log.e("", error.getMessage());
                                emptyDish.set(true);
                                getNavigator().dishListLoaded();
                            }
                        },AppConstants.API_VERSION_TWO);


                        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


}



