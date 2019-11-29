package com.tovo.eat.ui.cart.coupon;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.collection.CollectionRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class CouponListViewModel extends BaseViewModel<CouponListNavigator> {
    public final ObservableBoolean notClickable = new ObservableBoolean();
    public final ObservableBoolean emptyCoupon = new ObservableBoolean();
    public ObservableList<CouponListResponse.Result> couponListItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CouponListResponse.Result>> couponListItemsLiveData;


    public CouponListViewModel(DataManager dataManager) {
        super(dataManager);
        couponListItemsLiveData = new MutableLiveData<>();

        fetchRepos();
    }


    public void saveCouponId(int id, String coupon) {
        getDataManager().saveCouponId(id);
        getDataManager().saveCouponCode(coupon);
    }


    public MutableLiveData<List<CouponListResponse.Result>> getcouponListItemsLiveData() {
        return couponListItemsLiveData;
    }

    public void addDishItemsToList(List<CouponListResponse.Result> ordersItems) {
        couponListItemViewModels.clear();
        couponListItemViewModels.addAll(ordersItems);

    }

    public void goBack() {
        getNavigator().goBack();
    }

    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_COUPON_LIST_URL, CouponListResponse.class, new CollectionRequest(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId()), new Response.Listener<CouponListResponse>() {
                @Override
                public void onResponse(CouponListResponse response) {
                    if (response != null) {

                        if (response.getResult().size() == 0) {
                            emptyCoupon.set(true);
                            if (getNavigator() != null)
                                getNavigator().noList();

                        } else {
                            emptyCoupon.set(false);
                            couponListItemsLiveData.setValue(response.getResult());
                        }
                    }
                    if (getNavigator() != null)
                        getNavigator().listLoaded();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Log.e("", error.getMessage());
                    if (getNavigator() != null)
                        getNavigator().listLoaded();
                    emptyCoupon.set(true);
                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            getNavigator().listLoaded();
            ee.printStackTrace();

        }
    }

    public void checkCoupon(String code) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_COUPON_CHECK_URL, CouponListResponse.class, new CouponCheckRequest(getDataManager().getCurrentUserId(), code), new Response.Listener<CouponListResponse>() {
                @Override
                public void onResponse(CouponListResponse response) {
                    if (response != null) {


                        if (response.getStatus()) {

                            if (response.getResult().size() != 0) {
                                if (getNavigator() != null)
                                    getNavigator().couponValid(response.getResult().get(0).getCid());

                                saveCouponId(response.getResult().get(0).getCid(), response.getResult().get(0).getCouponName());


                            }
                        } else {
                            if (getNavigator() != null)
                                getNavigator().showToast(response.getMessage());
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


}
