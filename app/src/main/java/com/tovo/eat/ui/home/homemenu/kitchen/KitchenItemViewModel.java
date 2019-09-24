package com.tovo.eat.ui.home.homemenu.kitchen;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tovo.eat.R;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class KitchenItemViewModel {



    public final ObservableField<String> ratings = new ObservableField<>();

    public final ObservableField<String> kitchen_name = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchen_image = new ObservableField<>();
    public final ObservableField<String> offer = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> cuisines = new ObservableField<>();


    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean isRated = new ObservableBoolean();
    public final ObservableBoolean isEta = new ObservableBoolean();
    public final ObservableBoolean seriviceable = new ObservableBoolean();


    public final KitchenItemViewModelListener mListener;
    public final KitchenResponse.Result mKitchenList;


    int favID = 0;


    public KitchenItemViewModel(KitchenItemViewModelListener mListener, KitchenResponse.Result mKitchenList) {
        this.mListener = mListener;
        this.mKitchenList = mKitchenList;

        this.kitchen_image.set(mKitchenList.getMakeitimg());


        seriviceable.set(mKitchenList.isServiceableStatus());


        if (mKitchenList.getEta() == null) {
            isEta.set(false);
        } else {
            this.eta.set(mKitchenList.getEta());
            isEta.set(true);

        }

        if (mKitchenList.getFavid() != null)
            favID = mKitchenList.getFavid();


        this.region.set(mKitchenList.getRegionname());

        if (mKitchenList.getRating() != null) {
            isRated.set(true);
            this.ratings.set(String.valueOf(mKitchenList.getRating()));
        } else {
            isRated.set(false);
        }


        cuisines.set("by " + mKitchenList.getMakeitusername() + ", from ");


        this.offer.set(String.valueOf(mKitchenList.getCostfortwo()));
        this.kitchen_name.set(mKitchenList.getMakeitbrandname());

        if (mKitchenList.getIsfav() != null) {
            if (mKitchenList.getIsfav().equals("0")) {
                this.isFavourite.set(false);
            } else {
                this.isFavourite.set(true);
            }
        } else {
            this.isFavourite.set(false);
        }
    }

    public void fav() {

        if (isFavourite.get()) {

            if (favID != 0) {

                new Analytics().sendClickData(AppConstants.SCREEN_HOME,AppConstants.CLICK_REMOVE_FROM_FAV);

                removeFavourite();
            }
        } else {

            new Analytics().sendClickData(AppConstants.SCREEN_HOME,AppConstants.CLICK_ADD_TO_FAV);
            addFavourite(mKitchenList.getMakeituserid());
        }

    }

    public void removeFavourite() {

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        int userId = appPreferencesHelper.getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        if (favID != 0)
            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL + favID, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        isFavourite.set(false);
                        mListener.removeFavourites(0);
                        favID = 0;
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
            }


    }


    public void addFavourite(Integer kitchenId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        int userId = appPreferencesHelper.getCurrentUserId();


        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(userId), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        if (response.getFavid() != null) {
                            favID = response.getFavid();
                            isFavourite.set(true);
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    public void onItemClick() {
        mListener.onItemClick(mKitchenList.getMakeituserid());
    }

    public interface KitchenItemViewModelListener {
        void onItemClick(Integer id);

        void addFavourites(Integer id, String fav);

        void removeFavourites(Integer favId);
    }

}
