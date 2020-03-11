package com.tovo.eat.ui.kitchendetails.viewproduct;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsListRequest;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import java.util.List;

public class ViewProductViewModel extends BaseViewModel<ViewProductNavigator> {


    public ObservableList<KitchenDetailsResponse.ProductList> productListViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDetailsResponse.ProductList>> productListLiveData;


    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> subtitle = new ObservableField<>();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();
    public ObservableBoolean cart = new ObservableBoolean();
    public ObservableBoolean loading = new ObservableBoolean();
    public ObservableBoolean isFavourite = new ObservableBoolean();
    public ObservableBoolean isProductAvailable = new ObservableBoolean();
    public ObservableField<String> region = new ObservableField<>();

    public Long makeitId;
    int favId;

    public ViewProductViewModel(DataManager dataManager) {
        super(dataManager);
        subtitle.set("Bestsellers and recommended");
        productListLiveData = new MutableLiveData<>();
    }
    public void goBack() {
        getNavigator().goBack();
    }

    public void addProductsToList(List<KitchenDetailsResponse.ProductList> productList) {
        productListViewModels.clear();
        productListViewModels.addAll(productList);
    }

    public MutableLiveData<List<KitchenDetailsResponse.ProductList>> getProductListLiveData() {
        return productListLiveData;
    }

    public void fav() {
        if (isFavourite.get()) {
            removeFavourite(favId);
            isFavourite.set(false);

        } else {
            addFavourite(makeitId);
            isFavourite.set(true);
        }
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
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

    public void addFavourite(Long kitchenId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        getNavigator().toastMessage(response.getMessage());
                        if (response.getFavid() != null)
                            favId = response.getFavid();
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

    public void totalCart() {
        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);
        cart.set(false);
        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequestPojo();
        int count = 0;
        int price = 0;
        if (cartRequestPojo.getCartitems() != null) {
            if (cartRequestPojo.getCartitems().size() == 0) {
                cart.set(false);
            } else {
                for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {
                    count = count + cartRequestPojo.getCartitems().get(i).getQuantity();
                    price = price + ((cartRequestPojo.getCartitems().get(i).getPrice()) * cartRequestPojo.getCartitems().get(i).getQuantity());
                }
                if (count <= 0) {
                    cart.set(false);
                } else {
                    if (count == 1) {
                        cartItems.set(count + " Item");
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Item");
                    } else {
                        cartItems.set(count + " Items");
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Items");
                    }
                }
            }
        }
    }
    public void viewCart() {
        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_DETAILS, AppConstants.CLICK_VIEW_CART);
        getNavigator().viewCart();
    }

    public void fetchRepos(Long kitchenId,int vegtype) {
loading.set(true);
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_DISH_LIST_URL, KitchenDetailsResponse.class,
                    new KitchenDetailsListRequest(String.valueOf(getDataManager().getCurrentLat()), String.valueOf(getDataManager().getCurrentLng()),
                            kitchenId, getDataManager().getCurrentUserId(), vegtype), new Response.Listener<KitchenDetailsResponse>() {
                @Override
                public void onResponse(KitchenDetailsResponse response) {
                    if (response != null) {
                        setIsLoading(false);
                        totalCart();

                        if (response.getResult() != null && response.getResult().size() > 0) {

                            if (response.getResult().get(0).getProduct() != null && response.getResult().get(0).getProduct().size() > 0) {
                                productListLiveData.setValue(response.getResult().get(0).getProduct().get(0).getProductList());
                            }


                        } else {
                            isProductAvailable.set(false);
                        }
                    }
                    loading.set(false);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    loading.set(false);
                }
            }, AppConstants.API_VERSION_TWO_TWO);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
}
