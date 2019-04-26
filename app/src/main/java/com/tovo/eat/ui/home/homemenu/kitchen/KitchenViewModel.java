package com.tovo.eat.ui.home.homemenu.kitchen;

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
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class KitchenViewModel extends BaseViewModel<KitchenNavigator> {


    public ObservableList<KitchenResponse.Result> kitchenItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<KitchenResponse.Result>>kitchenItemsLiveData;


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

    public KitchenViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenItemsLiveData = new MutableLiveData<>();

    //    AlertDialog.Builder builder=new AlertDialog.Builder(getDataManager().);
       /* ConnectivityManager cm =
                (ConnectivityManager)getDataManager(). getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();*/

        fetchRepos();
    }

    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {
        kitchenItemViewModels.clear();
        kitchenItemViewModels.addAll(ordersItems);
    }


    public void saveMakeitId(Integer id){


        getDataManager().kitchenId(id);

    }



    public void removeFavourite(Integer favId){

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL+favId, CommonResponse.class, null,new Response.Listener<CommonResponse>() {
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
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }




    public void addFavourite(Integer kitchenId, String fav){

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(getDataManager().getCurrentUserId()),String.valueOf(kitchenId)),new Response.Listener<CommonResponse>() {
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
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }





    }



    public void fetchRepos() {

        if (getDataManager().getCurrentLat()==null){

         //   getNavigator().noAddressFound1();


        }else {


       //    getNavigator().addressAdded1();


            if (!MvvmApp.getInstance().onCheckNetWork()) return;


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
                            Log.e("----response:---------", response.toString());


                            getNavigator().kitchenListLoaded();

                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("", error.getMessage());
                        getNavigator().kitchenListLoaded();
                    }
                });

                MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (JSONException j) {
                j.printStackTrace();
            }



/*
            try {
                setIsLoading(true);
                GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_LIST_URL, KitchenResponse.class, filterRequestPojo, new Response.Listener<KitchenResponse>() {
                    @Override
                    public void onResponse(KitchenResponse response) {
                        if (response != null) {

                            kitchenItemsLiveData.setValue(response.getResult());
                            Log.e("----response:---------", response.toString());


                            KitchenViewModel.this.getNavigator().kitchenListLoaded();

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.e("", error.getMessage());
                    }
                });

                MvvmApp.getInstance().addToRequestQueue(gsonRequest);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }*/

        }
    }
}
