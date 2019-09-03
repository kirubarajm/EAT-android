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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class KitchenItemViewModel {


    //   public final ObservableField<Integer> sales_emp_id = new ObservableField<Integer>();

    public final ObservableField<String> ratings = new ObservableField<>();

    public final ObservableField<String> kitchen_name = new ObservableField<>();

    public final ObservableField<String> kitchen_type = new ObservableField<>();
    public final ObservableField<String> favourite = new ObservableField<>();
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
        //  this.date.set(mSalesList.getDate());

        //    this.ratings.set(String.valueOf(mKitchenList.getRatings()));

        //  this.kitchen_type.set(mKitchenList.getKitchenType());
        this.kitchen_image.set(mKitchenList.getMakeitimg());



        seriviceable.set(mKitchenList.isServiceableStatus());


       /* if (!mKitchenList.isServiceableStatus()) {

            // mListItemLiveProductsBinding.kitchenTile.setAlpha(1);
            mListItemLiveProductsBinding.kitchenTile.setBackgroundColor(MvvmApp.getInstance().getResources().getColor(R.color.gray));
            mListItemLiveProductsBinding.kitchenName.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));
            mListItemLiveProductsBinding.region.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            mListItemLiveProductsBinding.image.setColorFilter(filter);
            mListItemLiveProductsBinding.service1.setVisibility(View.VISIBLE);
            // mListItemLiveProductsBinding.rating.setVisibility(View.GONE);

        }*/







        if (mKitchenList.getEta() == null) {
            isEta.set(false);
        } else {
            this.eta.set(mKitchenList.getEta());
            isEta.set(true);

        }

        if (mKitchenList.getFavid() != null)
            favID = mKitchenList.getFavid();

        //  this.favourite.set(mKitchenList.getFavourite());
        //  this.offer.set(mKitchenList.getOffer());

        this.region.set(mKitchenList.getRegionname());

        if (mKitchenList.getRating() != null) {
            isRated.set(true);
            this.ratings.set(String.valueOf(mKitchenList.getRating()));
        } else {
            isRated.set(false);
        }

/*
        StringBuilder itemsBuilder = new StringBuilder();

        if (mKitchenList.getCuisines() != null) {

            for (int i = 0; i < mKitchenList.getCuisines().size(); i++) {

                itemsBuilder.append(mKitchenList.getCuisines().get(i).getCuisinename());

                if (mKitchenList.getCuisines().size() - 1 == i) {

                } else {
                    itemsBuilder.append(" | ");

                }
            }
        }

        String items = itemsBuilder.toString();
        cuisines.set(items);*/


        cuisines.set("by " + mKitchenList.getMakeitusername() + ", from ");


        this.offer.set(String.valueOf(mKitchenList.getCostfortwo()));
        this.kitchen_name.set(mKitchenList.getMakeitbrandname());

       /* if (mKitchenList.getMakeitbrandname() != null)
            if (mKitchenList.getMakeitbrandname().isEmpty()) {
                this.kitchen_name.set(mKitchenList.getMakeitusername());
            } else {
                this.kitchen_name.set(mKitchenList.getMakeitbrandname());
            }*/

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
            // isFavourite.set(false);
            if (favID != 0)
                removeFavourite();
        } else {
            //isFavourite.set(true);
            addFavourite(mKitchenList.getMakeituserid());
        }


        /*if (mKitchenList.getIsfav().equals("0")) {
            isFavourite.set(true);
            mListener.addFavourites(mKitchenList.getMakeituserid(), mKitchenList.getIsfav());
        } else if (mKitchenList.getIsfav().equals("1")) {

            if (mKitchenList.getFavid() != null) {
                isFavourite.set(false);
                mListener.removeFavourites(mKitchenList.getFavid());
            }

        }*/
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
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("accept-version", AppConstants.API_VERSION_ONE);
                        AppPreferencesHelper preferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);

                        headers.put("Authorization","Bearer "+preferencesHelper.getApiToken());
                            headers.put("apptype",AppConstants.APP_TYPE_ANDROID);

                        return headers;
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
        //  mListener.onItemClick(isJobCompleted,Integer.parseInt(sales_emp_id.toString()) , Integer.parseInt(makeit_userid.toString()), date.toString(), name.toString(), email.toString(), phoneno.toString(), brandname.toString(),address.toString(),lat.toString(),lng.toString(),localityid.toString());
    }

    public interface KitchenItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick(Integer id);

        void addFavourites(Integer id, String fav);

        void removeFavourites(Integer favId);
    }

}
