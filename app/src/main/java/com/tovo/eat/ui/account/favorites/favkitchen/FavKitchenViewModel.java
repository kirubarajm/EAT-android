package com.tovo.eat.ui.account.favorites.favkitchen;

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
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenNavigator;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavKitchenViewModel extends BaseViewModel<KitchenNavigator> {


    public ObservableList<KitchenResponse.Result> kitchenItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<KitchenResponse.Result>> kitchenItemsLiveData;
    public ObservableBoolean favFragment=new ObservableBoolean();
    public ObservableBoolean emptyKitchen=new ObservableBoolean();

    public FavKitchenViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenItemsLiveData = new MutableLiveData<>();
        if (getDataManager().getIsFav()) {
            favFragment.set(false);
        }else {
            favFragment.set(true);
        }

        fetchRepos();

    }

    public ObservableList<KitchenResponse.Result> getKitchenItemViewModels() {
        return kitchenItemViewModels;
    }
    public MutableLiveData<List<KitchenResponse.Result>> getKitchenItemsLiveData() {
        return kitchenItemsLiveData;
    }

    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {
        kitchenItemViewModels.clear();
        kitchenItemViewModels.addAll(ordersItems);
    }

    public void saveMakeitId(Long id) {
        getDataManager().kitchenId(id);
    }
    public void filter(){
        getNavigator().filter();
    }



    public void removeFavourite(Integer favId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
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

                }
            },AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }
    }


    public void addFavourite(Integer kitchenId, String fav) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        getNavigator().toastMessage(response.getMessage());
                        fetchRepos();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            },AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }
    }
    public void fetchRepos() {
                try {
                    setIsLoading(true);
                    GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_FAV_KITCHEN_LIST_URL + getDataManager().getCurrentUserId(), KitchenResponse.class, new Response.Listener<KitchenResponse>() {
                        @Override
                        public void onResponse(KitchenResponse response) {
                                if (response != null) {
                                    if (response.getResult().size()>0){
                                        emptyKitchen.set(false);
                                        kitchenItemsLiveData.setValue(response.getResult());
                                    }else {
                                        emptyKitchen.set(true);
                                    }
                                }else {

                                    emptyKitchen.set(true);
                                }
                            getNavigator().kitchenListLoaded();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            emptyKitchen.set(true);
                            getNavigator().kitchenListLoaded();
                        }
                    },AppConstants.API_VERSION_TWO);
                    MvvmApp.getInstance().addToRequestQueue(gsonRequest);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception ee){

                ee.printStackTrace();
        }
    }
}
