package com.tovo.eat.ui.account.favorites;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

public class FavouritesViewModel extends BaseViewModel<FavouritesNavigator> {

    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();

    public ObservableBoolean cart = new ObservableBoolean();

    public ObservableBoolean optionmenu = new ObservableBoolean();

    public ObservableBoolean isFavourite = new ObservableBoolean();
    public Long kitchenid;
    int favId;
    int makeitId;

    public FavouritesViewModel(DataManager dataManager) {
        super(dataManager);
        optionmenu.set(true);
    }

    public void goBack() {
        getNavigator().goBack();
    }


    public String getCartPojoDetails() {
        return getDataManager().getCartDetails();
    }

    public void kitchen() {
        if (!optionmenu.get()) {
            optionmenu.set(true);
            getNavigator().kitchen();
        }
    }

    public void dish() {
        if (optionmenu.get()) {
            optionmenu.set(false);
            getNavigator().dish();
        }
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
                        if (getNavigator() != null)
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

    public void addFavourite(Integer kitchenId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        if (getNavigator() != null)
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


                kitchenid = cartRequestPojo.getMakeitUserid();


                for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {
                    count = count + cartRequestPojo.getCartitems().get(i).getQuantity();
                    price = price + ((cartRequestPojo.getCartitems().get(i).getPrice()) * cartRequestPojo.getCartitems().get(i).getQuantity());
                }
                if (count <= 0) {
                    cart.set(false);
                } else {
                    if (count == 1) {
                        cartItems.set(String.valueOf(count) + " Item");
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Item");
                    } else {
                        cartItems.set(String.valueOf(count) + " Items");
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Items");
                    }
                }
            }
        }
    }


    public void viewCart() {
        getNavigator().viewCart();
    }

    public void back() {
        getNavigator().back();
    }


}
