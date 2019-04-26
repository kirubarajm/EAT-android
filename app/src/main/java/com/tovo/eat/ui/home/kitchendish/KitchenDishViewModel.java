package com.tovo.eat.ui.home.kitchendish;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

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

import java.util.List;

public class KitchenDishViewModel extends BaseViewModel<KitchenDishNavigator> {


    public MutableLiveData<List<KitchenDishResponse.Result>> dishItemFullViewModels;

    public ObservableList<KitchenDishResponse.Productlist> dishItemViewModels = new ObservableArrayList<>();
    public ObservableList<KitchenDishResponse.Result> dishFullItemViewModels = new ObservableArrayList<>();


    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> kitchenImage = new ObservableField<>();
    public ObservableField<String> kitchenCategory = new ObservableField<>();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();


    public ObservableBoolean cart = new ObservableBoolean();


    public ObservableBoolean isFavourite = new ObservableBoolean();
    public ObservableField<KitchenDishResponse> kitchenDishModells = new ObservableField<>();
    int favId;
    int makeitId;
    String isFav;
    private MutableLiveData<List<KitchenDishResponse.Productlist>> dishItemsLiveData;


    public KitchenDishViewModel(DataManager dataManager) {
        super(dataManager);
        dishItemsLiveData = new MutableLiveData<>();

        //    AlertDialog.Builder builder=new AlertDialog.Builder(getDataManager().);
       /* ConnectivityManager cm =
                (ConnectivityManager)getDataManager(). getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();*/

        fetchRepos();
    }

    public MutableLiveData<List<KitchenDishResponse.Result>> getDishItemFullViewModels() {
        return dishItemFullViewModels;
    }

    public void setDishItemFullViewModels(MutableLiveData<List<KitchenDishResponse.Result>> dishItemFullViewModels) {
        this.dishItemFullViewModels = dishItemFullViewModels;
    }

    public ObservableList<KitchenDishResponse.Productlist> getDishItemViewModels() {
        return dishItemViewModels;
    }

    public void setDishItemViewModels(ObservableList<KitchenDishResponse.Productlist> dishItemViewModels) {
        this.dishItemViewModels = dishItemViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Productlist>> getKitchenItemsLiveData() {
        return dishItemsLiveData;
    }

    public void setKitchenItemsLiveData(MutableLiveData<List<KitchenDishResponse.Productlist>> kitchenItemsLiveData) {
        this.dishItemsLiveData = kitchenItemsLiveData;
    }

    public void addDishItemsToList(List<KitchenDishResponse.Productlist> ordersItems) {
        dishItemViewModels.clear();
        dishItemViewModels.addAll(ordersItems);

    }

    public void addKitchenDishItemsToList(List<KitchenDishResponse.Result> results) {
        dishFullItemViewModels.clear();
        dishFullItemViewModels.addAll(results);

    }


    public String getCartPojoDetails() {

        return getDataManager().getCartDetails();
    }


    public void fav() {
        if (isFavourite.get()) {
            removeFavourite(favId);
            isFavourite.set(false);

        } else  {
            addFavourite(makeitId);
            isFavourite.set(true);
        }
        //   mListener.addFavourites(originalResult.getMakeituserid(), dishList.getProductid(), dishList.getIsfav());
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
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void addFavourite(Integer kitchenId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
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

                        cartItems.set(String.valueOf(count));
                        cart.set(true);

                        cartPrice.set(String.valueOf(price));

                        items.set("Item");
                    } else {

                        cartItems.set(String.valueOf(count));
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Items");
                    }


                }
            }
        }
    }


    public void fetchRepos() {
        // if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );


        Integer kitchenId = getDataManager().getMakeitID();


        //  setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_DISH_LIST_URL, KitchenDishResponse.class, new KitchenDishListRequest(String.valueOf(getDataManager().getCurrentLat()), String.valueOf(getDataManager().getCurrentLng()), kitchenId,getDataManager().getCurrentUserId()), new Response.Listener<KitchenDishResponse>() {
            @Override
            public void onResponse(KitchenDishResponse response) {
                if (response != null) {
                    //     dishItemsLiveData.setValue(response.getResult().get(0).getProductlist());
                    //     dishItemFullViewModels.setValue(response.getResult());


                    totalCart();

                    dishFullItemViewModels.addAll(response.getResult());

                    makeitId = response.getResult().get(0).getMakeituserid();

                    if (response.getResult().get(0).getMakeitbrandname().isEmpty()) {

                        kitchenName.set(response.getResult().get(0).getMakeitusername());

                    } else {

                        kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                    }

                    kitchenImage.set(response.getResult().get(0).getMakeitimg());


                    isFav = response.getResult().get(0).getIsfav();


                    if (response.getResult().get(0).getFavid() != null) {
                        favId = response.getResult().get(0).getFavid();
                        favId = response.getResult().get(0).getFavid();
                    }
                    if (response.getResult().get(0).getIsfav().equals("0")) {
                        isFavourite.set(false);
                    } else {

                        isFavourite.set(true);
                    }


                    Log.e("----response:---------", response.toString());


                    KitchenDishViewModel.this.getNavigator().dishListLoaded();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", error.getMessage());
            }
        });


        MvvmApp.getInstance().addToRequestQueue(gsonRequest);

    }


    public void viewCart() {
        getNavigator().viewCart();
    }

    public void back(){
        getNavigator().back();
    }
}
