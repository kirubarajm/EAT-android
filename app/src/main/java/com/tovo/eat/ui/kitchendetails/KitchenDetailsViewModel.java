package com.tovo.eat.ui.kitchendetails;

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
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import java.util.List;

public class KitchenDetailsViewModel extends BaseViewModel<KitchenDetailsNavigator> {


    public ObservableList<KitchenDetailsResponse.Product> productListViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDetailsResponse.Product>> productListLiveData;

    public ObservableList<KitchenDetailsResponse.KitchenPage> headerItemViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDetailsResponse.KitchenPage>> headerItemLiveData;

    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> makeitName = new ObservableField<>();

    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> noProductsString = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();
    public ObservableBoolean cart = new ObservableBoolean();
    public ObservableBoolean isFavourite = new ObservableBoolean();
    public ObservableBoolean isVegOnly = new ObservableBoolean();

    public ObservableBoolean isProductAvailable = new ObservableBoolean();
    public ObservableBoolean serviceablestatus = new ObservableBoolean();

    public ObservableField<String> region = new ObservableField<>();
    public ObservableField<String> localityName = new ObservableField<>();

    public ObservableField<String> headerContent1 = new ObservableField<>();
    public ObservableField<String> headerContent2 = new ObservableField<>();

    public Long makeitId;

    int favId;
    String isFav;
    int vegid;


    public KitchenDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        headerItemLiveData = new MutableLiveData<>();
        productListLiveData = new MutableLiveData<>();

        isProductAvailable.set(true);
        serviceablestatus.set(true);
    }


    public void addProductsToList(List<KitchenDetailsResponse.Product> productList) {
        productListViewModels.clear();
        productListViewModels.addAll(productList);
    }

     public void addHeadersToList(List<KitchenDetailsResponse.KitchenPage> headers) {
        headerItemViewModels.clear();
         headerItemViewModels.addAll(headers);
    }




    public MutableLiveData<List<KitchenDetailsResponse.Product>> getProductListLiveData() {
        return productListLiveData;
    }

    public void setProductListLiveData(MutableLiveData<List<KitchenDetailsResponse.Product>> productListLiveData) {
        this.productListLiveData = productListLiveData;
    }

    public MutableLiveData<List<KitchenDetailsResponse.KitchenPage>> getHeaderItemLiveData() {
        return headerItemLiveData;
    }

    public void setHeaderItemLiveData(MutableLiveData<List<KitchenDetailsResponse.KitchenPage>> headerItemLiveData) {
        this.headerItemLiveData = headerItemLiveData;
    }

    public void goBack() {
        getNavigator().goBack();
    }

    public void vegType() {
        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_DETAILS, AppConstants.CLICK_VEG_ONLY);
        if (!isVegOnly.get()) {
            isVegOnly.set(true);
            getDataManager().saveVegType(1);
            vegid = 1;
            fetchVegProducts();
        } else {

            isVegOnly.set(false);
            getDataManager().saveVegType(0);
            vegid = 0;
            fetchRepos(makeitId);
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

    public void fetchRepos(Long kitchenId) {


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_DISH_LIST_URL, KitchenDetailsResponse.class,
                    new KitchenDetailsListRequest(String.valueOf(getDataManager().getCurrentLat()), String.valueOf(getDataManager().getCurrentLng()),
                            kitchenId, getDataManager().getCurrentUserId(), vegid), new Response.Listener<KitchenDetailsResponse>() {
                @Override
                public void onResponse(KitchenDetailsResponse response) {
                    if (response != null) {
                        setIsLoading(false);
                        totalCart();

                        if (response.getResult() != null && response.getResult().size() > 0) {

                            serviceablestatus.set(response.getResult().get(0).getServiceablestatus());

                            if (response.getResult().get(0).getProduct() != null && response.getResult().get(0).getProduct().size() > 0) {

                                productListLiveData.setValue(response.getResult().get(0).getProduct());
                            } else {

                                isProductAvailable.set(false);
                                noProductsString.set("Currently not serviceable.");

                            }

                            headerContent1.set(response.getResult().get(0).getKitchen_page_header_content1());
                            headerContent2.set(response.getResult().get(0).getKitchen_page_header_content2());
                            makeitName.set("by "+response.getResult().get(0).getMakeitusername());

                            if (response.getResult().get(0).getKitchenPage() != null && response.getResult().get(0).getKitchenPage().size() > 0) {

                                headerItemLiveData.setValue(response.getResult().get(0).getKitchenPage());
                            }


                            makeitId = response.getResult().get(0).getMakeituserid();

                            isFav = response.getResult().get(0).getIsfav();

                            if (response.getResult().get(0).getFavid() != null) {
                                favId = response.getResult().get(0).getFavid();
                            }
                            if (response.getResult().get(0).getIsfav().equals("0")) {
                                isFavourite.set(false);
                            } else {
                                isFavourite.set(true);
                            }
                            if (response.getResult().get(0).getMakeitbrandname().isEmpty()) {

                                kitchenName.set(response.getResult().get(0).getMakeitusername());

                            } else {
                                kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                            }

                            region.set("from " + response.getResult().get(0).getRegionname());
                            localityName.set("Lives in " + response.getResult().get(0).getLocalityname());

                        } else {
                            isProductAvailable.set(false);
                        }
                    }

                    getNavigator().dishListLoaded(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().loadError();
                }
            }, AppConstants.API_VERSION_TWO_TWO);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }


    public void fetchVegProducts() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_DISH_LIST_URL, KitchenDetailsResponse.class,
                    new KitchenDetailsListRequest(String.valueOf(getDataManager().getCurrentLat()), String.valueOf(getDataManager().getCurrentLng()),
                            makeitId, getDataManager().getCurrentUserId(), vegid), new Response.Listener<KitchenDetailsResponse>() {
                @Override
                public void onResponse(KitchenDetailsResponse response) {
                    if (response != null) {
                        setIsLoading(false);
                        totalCart();

                        if (response.getResult() != null && response.getResult().size() > 0) {

                            serviceablestatus.set(response.getResult().get(0).getServiceablestatus());

                            if (response.getResult().get(0).getProduct() != null && response.getResult().get(0).getProduct().size() > 0) {
                                productListLiveData.setValue(response.getResult().get(0).getProduct());

                            } else {

                                isProductAvailable.set(false);
                                noProductsString.set("No veg products available.");

                            }




                            if (response.getResult().get(0).getKitchenPage() != null && response.getResult().get(0).getKitchenPage().size() > 0) {

                                headerItemLiveData.setValue(response.getResult().get(0).getKitchenPage());
                            }


                            makeitId = response.getResult().get(0).getMakeituserid();

                            isFav = response.getResult().get(0).getIsfav();

                            if (response.getResult().get(0).getFavid() != null) {
                                favId = response.getResult().get(0).getFavid();
                            }
                            if (response.getResult().get(0).getIsfav().equals("0")) {
                                isFavourite.set(false);
                            } else {
                                isFavourite.set(true);
                            }
                            if (response.getResult().get(0).getMakeitbrandname().isEmpty()) {

                                kitchenName.set(response.getResult().get(0).getMakeitusername());

                            } else {
                                kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                            }

                            region.set("from " + response.getResult().get(0).getRegionname());
                            localityName.set("Lives in " + response.getResult().get(0).getLocalityname());

                        } else {
                            isProductAvailable.set(false);
                        }


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().dishListLoaded(null);
                }
            }, AppConstants.API_VERSION_TWO_TWO);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void viewCart() {
        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_DETAILS, AppConstants.CLICK_VIEW_CART);
        getNavigator().viewCart();
    }


}
