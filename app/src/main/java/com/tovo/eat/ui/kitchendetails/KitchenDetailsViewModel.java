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

import java.util.ArrayList;
import java.util.List;

public class KitchenDetailsViewModel extends BaseViewModel<KitchenDetailsNavigator> {


    public ObservableList<KitchenDishResponse.Result> favAndTodayMenuArrayListViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDishResponse.Result>> favAndTodayMenuArrayListLiveData;

    public ObservableList<KitchenDishResponse.Productlist> favoriteArrayViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDishResponse.Productlist>> favoriteArrayViewLiveData;
    public ObservableList<KitchenDishResponse.Productlist> todaysMenuArrayViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDishResponse.Productlist>> todaysMenuArrayViewLiveData;
    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> kitchenImage = new ObservableField<>();
    public ObservableField<String> kitchenCategory = new ObservableField<>();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();
    public ObservableBoolean cart = new ObservableBoolean();
    public ObservableBoolean optionmenu = new ObservableBoolean();
    public ObservableBoolean isFavourite = new ObservableBoolean();
    public ObservableBoolean isVegOnly = new ObservableBoolean();
    public ObservableField<KitchenDishResponse> kitchenDishModells = new ObservableField<>();
    public ObservableList<KitchenDishResponse.Kitchenmenuimage> kitchenInfoImagesListViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDishResponse.Kitchenmenuimage>> kitchenInfoImagesListLiveData;
    public ObservableList<KitchenDishResponse.Foodbadge> foodBadgeObservableArrayListViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDishResponse.Foodbadge>> foodBadgesMutableLiveData;
    ////
    public ObservableBoolean favorites = new ObservableBoolean();
    public ObservableBoolean todaysMenu = new ObservableBoolean();
    public ObservableBoolean specialities = new ObservableBoolean();
    public ObservableBoolean foodBadges = new ObservableBoolean();
    public ObservableBoolean imageOrVideo = new ObservableBoolean();
    public ObservableBoolean isProductAvailable = new ObservableBoolean();
    public ObservableBoolean serviceablestatus = new ObservableBoolean();
    public ObservableList<KitchenDishResponse.Specialitem> specialItemsListViewModels = new ObservableArrayList<>();
    public MutableLiveData<List<KitchenDishResponse.Specialitem>> specialitemsMutableLiveData;
    public ObservableField<String> region = new ObservableField<>();
    public ObservableField<String> localityName = new ObservableField<>();
    public ObservableField<String> memberType = new ObservableField<>();
    public ObservableField<String> rating = new ObservableField<>();
    public ObservableField<String> memberTypeDesc = new ObservableField<>();
    public ObservableField<String> memberTypeIcon = new ObservableField<>();
    public ObservableField<String> about = new ObservableField<>();
    public ObservableField<String> signatureImageUrl = new ObservableField<>();
    public ObservableField<String> ratingCount = new ObservableField<>();


    List<KitchenDishResponse.Productlist> favoriteProductlists = new ArrayList<>();
    List<KitchenDishResponse.Productlist> todaysMenuProductlists = new ArrayList<>();
    int favId;
    int makeitId;
    String isFav;
    int vegid;
    List<KitchenDishResponse.Kitchenmenuimage> commonKitchenImagesList = new ArrayList<>();
    List<KitchenDishResponse.Kitchenmenuimage> kitchenmenuimageArrayList = new ArrayList<>();
    List<KitchenDishResponse.Kitchenmenuimage> kitchenInfoTempArray = new ArrayList<>();
    List<KitchenDishResponse.Kitcheninfoimage> kitchenInfoimageArrayList = new ArrayList<>();
    KitchenDishResponse.Kitchenmenuimage kitchenmenuimage;
    private MutableLiveData<List<KitchenDishResponse.Productlist>> dishItemsLiveData;

    public KitchenDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        favoriteArrayViewLiveData = new MutableLiveData<>();
        todaysMenuArrayViewLiveData = new MutableLiveData<>();
        foodBadgesMutableLiveData = new MutableLiveData<>();
        favAndTodayMenuArrayListLiveData = new MutableLiveData<>();
        specialitemsMutableLiveData = new MutableLiveData<>();
        foodBadgesMutableLiveData = new MutableLiveData<>();
        dishItemsLiveData = new MutableLiveData<>();
        kitchenInfoImagesListLiveData = new MutableLiveData<>();
        optionmenu.set(true);
        imageOrVideo.set(true);
        isProductAvailable.set(true);
        serviceablestatus.set(true);

        //    AlertDialog.Builder builder=new AlertDialog.Builder(getDataManager().);
       /* ConnectivityManager cm =
                (ConnectivityManager)getDataManager(). getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();*/


    }

    public MutableLiveData<List<KitchenDishResponse.Productlist>> getKitchenItemsLiveData() {
        return dishItemsLiveData;
    }

    public String getCartPojoDetails() {

        return getDataManager().getCartDetails();
    }


    public void goBack() {
        getNavigator().goBack();
    }

    public void vegType() {
        if (!isVegOnly.get()) {
            isVegOnly.set(true);
            getDataManager().saveVegType(1);
            vegid = 1;
            fetchVegProducts();
        } else {

            isVegOnly.set(false);
            getDataManager().saveVegType(0);
            vegid = 0;
            fetchVegProducts();
        }
    }


    public void menu() {
        if (!optionmenu.get()) {
            optionmenu.set(true);
            getNavigator().animChanges(true);
        }

        kitchenInfoImagesListLiveData = new MutableLiveData<>();
        commonKitchenImagesList = new ArrayList<>();
        if (kitchenmenuimageArrayList != null) {
            commonKitchenImagesList = kitchenmenuimageArrayList;
            kitchenInfoImagesListLiveData.setValue(commonKitchenImagesList);
            addkitchenCommonImagesList(commonKitchenImagesList);
        }

        getNavigator().update(kitchenmenuimageArrayList.size());
    }

    public void info() {
        if (optionmenu.get()) {
            optionmenu.set(false);
            getNavigator().animChanges(false);
        }

        kitchenInfoImagesListLiveData = new MutableLiveData<>();
        if (kitchenInfoTempArray != null) {
            commonKitchenImagesList = kitchenInfoTempArray;
            addkitchenCommonImagesList(commonKitchenImagesList);
        }

        getNavigator().update(kitchenInfoTempArray.size());
    }

    public void fav() {
        if (isFavourite.get()) {
            removeFavourite(favId);
            isFavourite.set(false);

        } else {
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

    public void fetchRepos(Integer kitchenId) {


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_DISH_LIST_URL, KitchenDishResponse.class,
                    new KitchenDetailsListRequest(String.valueOf(getDataManager().getCurrentLat()), String.valueOf(getDataManager().getCurrentLng()),
                            kitchenId, getDataManager().getCurrentUserId(), vegid), new Response.Listener<KitchenDishResponse>() {
                @Override
                public void onResponse(KitchenDishResponse response) {
                    if (response != null) {
                        setIsLoading(false);
                        totalCart();

                        if (response.getResult() != null && response.getResult().size() > 0) {

                            isProductAvailable.set(true);


                            serviceablestatus.set(response.getResult().get(0).isServiceableStatus());

                            if (response.getResult().get(0).getProductlist() != null && response.getResult().get(0).getProductlist().size() > 0) {


                                for (int i = 0; i < response.getResult().get(0).getProductlist().size(); i++) {
                                    if (response.getResult().get(0).getProductlist().get(i).getProductimage() != null && !response.getResult().get(0).getProductlist().get(i).getProductimage().equals("") && !response.getResult().get(0).getProductlist().get(i).getProductimage().equals("null")) {
                                        favoriteProductlists.add(response.getResult().get(0).getProductlist().get(i));
                                    } else {
                                        todaysMenuProductlists.add(response.getResult().get(0).getProductlist().get(i));
                                    }
                                }
                                favoriteArrayViewLiveData.setValue(favoriteProductlists);
                                todaysMenuArrayViewLiveData.setValue(todaysMenuProductlists);

                                if (favoriteProductlists.size() > 0) {
                                    favorites.set(true);
                                }
                                if (todaysMenuProductlists.size() > 0) {
                                    todaysMenu.set(true);
                                }

                            } else {
                                favoriteProductlists.clear();
                                todaysMenuProductlists.clear();
                                favoriteArrayViewLiveData.setValue(favoriteProductlists);
                                todaysMenuArrayViewLiveData.setValue(todaysMenuProductlists);
                                favorites.set(false);
                                todaysMenu.set(false);
                                isProductAvailable.set(false);

                            }
                            ratingCount.set("(" + (response.getResult().get(0).getRatingCount()) + ")");


                            signatureImageUrl.set(response.getResult().get(0).getKitchensignature());


                            favAndTodayMenuArrayListLiveData.setValue(response.getResult());
                            foodBadgesMutableLiveData.setValue(response.getResult().get(0).getFoodbadge());
                            specialitemsMutableLiveData.setValue(response.getResult().get(0).getSpecialitems());

                            if (response.getResult().get(0).getFoodbadge().size() > 0) {
                                foodBadges.set(true);
                            }
                            if (response.getResult().get(0).getSpecialitems().size() > 0) {
                                specialities.set(true);
                            }

                            kitchenmenuimageArrayList = response.getResult().get(0).getKitchenmenuimage();
                            kitchenInfoimageArrayList = response.getResult().get(0).getKitcheninfoimage();

                            int count = kitchenmenuimageArrayList.size();
                            getNavigator().update(count);
                            /////menu slider details
                            commonKitchenImagesList = kitchenmenuimageArrayList;
                            if (commonKitchenImagesList != null) {
                                kitchenInfoImagesListLiveData.setValue(commonKitchenImagesList);
                            }

                            ////info slider details
                            kitchenInfoTempArray = new ArrayList<>();
                            for (int i = 0; i < kitchenInfoimageArrayList.size(); i++) {
                                kitchenmenuimage = new KitchenDishResponse.Kitchenmenuimage();
                                kitchenmenuimage.setType(kitchenInfoimageArrayList.get(i).getType());
                                kitchenmenuimage.setImgUrl(kitchenInfoimageArrayList.get(i).getImgUrl());

                                kitchenInfoTempArray.add(kitchenmenuimage);
                            }

                            makeitId = response.getResult().get(0).getMakeituserid();
                            kitchenImage.set(response.getResult().get(0).getMakeitimg());
                            isFav = response.getResult().get(0).getIsfav();
                            kitchenCategory.set(response.getResult().get(0).getLocalityname());

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

                            rating.set(String.valueOf(response.getResult().get(0).getRating()));
                            region.set("from " + response.getResult().get(0).getRegionname());
                            localityName.set("Lives in " + response.getResult().get(0).getLocalityname());
                            memberType.set(String.valueOf(response.getResult().get(0).getMemberType()));

                            about.set(response.getResult().get(0).getAbout());

                            memberTypeDesc.set(response.getResult().get(0).getMemberTypeName());
                            memberTypeIcon.set(response.getResult().get(0).getMemberTypeIcon());


                          /*  if (memberType.equals("1")) {
                                memberTypeDesc.set("Gold member");
                            } else if (memberType.equals("2")) {
                                memberTypeDesc.set("Silver member");
                            } else if (memberType.equals("3")) {
                                memberTypeDesc.set("Bronze member");
                            } else {
                                memberTypeDesc.set("null");
                            }*/
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
                    getNavigator().dishListLoaded(null);
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }


    public void fetchVegProducts() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_DISH_LIST_URL, KitchenDishResponse.class,
                    new KitchenDetailsListRequest(String.valueOf(getDataManager().getCurrentLat()), String.valueOf(getDataManager().getCurrentLng()),
                            makeitId, getDataManager().getCurrentUserId(), vegid), new Response.Listener<KitchenDishResponse>() {
                @Override
                public void onResponse(KitchenDishResponse response) {
                    if (response != null) {
                        setIsLoading(false);
                        totalCart();

                        if (response.getResult() != null && response.getResult().size() != 0) {
                            favoriteProductlists.clear();
                            todaysMenuProductlists.clear();
                            serviceablestatus.set(response.getResult().get(0).isServiceableStatus());

                            isProductAvailable.set(true);

                            if (response.getResult().get(0).getProductlist() != null && response.getResult().get(0).getProductlist().size() > 0) {
                                for (int i = 0; i < response.getResult().get(0).getProductlist().size(); i++) {
                                    if (response.getResult().get(0).getProductlist().get(i).getProductimage() != null && !response.getResult().get(0).getProductlist().get(i).getProductimage().equals("") && !response.getResult().get(0).getProductlist().get(i).getProductimage().equals("null")) {

                                        favoriteProductlists.add(response.getResult().get(0).getProductlist().get(i));
                                    } else {
                                        todaysMenuProductlists.add(response.getResult().get(0).getProductlist().get(i));
                                    }
                                }
                                favoriteArrayViewLiveData.setValue(favoriteProductlists);
                                todaysMenuArrayViewLiveData.setValue(todaysMenuProductlists);

                                if (favoriteProductlists.size() > 0) {
                                    favorites.set(true);
                                }
                                if (todaysMenuProductlists.size() > 0) {
                                    todaysMenu.set(true);
                                }
                                getNavigator().dishListLoaded(response);

                            }else {
                                favoriteProductlists.clear();
                                todaysMenuProductlists.clear();
                                favoriteArrayViewLiveData.setValue(favoriteProductlists);
                                todaysMenuArrayViewLiveData.setValue(todaysMenuProductlists);
                                favorites.set(false);
                                todaysMenu.set(false);
                                isProductAvailable.set(false);
                            }
                        } else {
                            favoriteProductlists.clear();
                            todaysMenuProductlists.clear();
                            favoriteArrayViewLiveData.setValue(favoriteProductlists);
                            todaysMenuArrayViewLiveData.setValue(todaysMenuProductlists);
                            favorites.set(false);
                            todaysMenu.set(false);
                            isProductAvailable.set(false);

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    getNavigator().dishListLoaded(null);
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void viewCart() {
        getNavigator().viewCart();
    }


    public void addkitchenCommonImagesList(List<KitchenDishResponse.Kitchenmenuimage> kitchenCommonimageList) {
        kitchenInfoImagesListViewModels.clear();
        kitchenInfoImagesListViewModels.addAll(kitchenCommonimageList);
    }

    public ObservableList<KitchenDishResponse.Kitchenmenuimage> getKicthenCommonImagesItemViewModels() {
        return kitchenInfoImagesListViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Kitchenmenuimage>> getKitchenCommonImages() {
        return kitchenInfoImagesListLiveData;
    }

    public void addFoodBadgesImagesList(List<KitchenDishResponse.Foodbadge> kitchenCommonimageList) {
        foodBadgeObservableArrayListViewModels.clear();
        foodBadgeObservableArrayListViewModels.addAll(kitchenCommonimageList);
    }

    public ObservableList<KitchenDishResponse.Foodbadge> getFoodBadgesImagesItemViewModels() {
        return foodBadgeObservableArrayListViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Foodbadge>> getFoodBadgesImages() {
        return foodBadgesMutableLiveData;
    }

    public void addSpecialItemsImagesList(List<KitchenDishResponse.Specialitem> kitchenCommonImageList) {
        specialItemsListViewModels.clear();
        specialItemsListViewModels.addAll(kitchenCommonImageList);
    }

    public ObservableList<KitchenDishResponse.Specialitem> getSpecialItemsImagesItemViewModels() {
        return specialItemsListViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Specialitem>> getSpeialItemsImages() {
        return specialitemsMutableLiveData;
    }


    public void addFavTodaysMenuItemsImagesList(List<KitchenDishResponse.Result> productlists) {
        favAndTodayMenuArrayListViewModels.clear();
        favAndTodayMenuArrayListViewModels.addAll(productlists);
    }

    public ObservableList<KitchenDishResponse.Result> getFavTodaysMenuImagesItemViewModels() {
        return favAndTodayMenuArrayListViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Result>> getFavTodaysMenuItemsImages() {
        return favAndTodayMenuArrayListLiveData;
    }


    ///////////

    public void addFav1ImagesList(List<KitchenDishResponse.Productlist> productlists) {
        favoriteArrayViewModels.clear();
        favoriteArrayViewModels.addAll(productlists);
    }

    public ObservableList<KitchenDishResponse.Productlist> getFav1ItemViewModels() {
        return favoriteArrayViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Productlist>> getFav1ItemsImages() {
        return favoriteArrayViewLiveData;
    }

    public void addTodaysImagesList(List<KitchenDishResponse.Productlist> productlists) {
        todaysMenuArrayViewModels.clear();
        todaysMenuArrayViewModels.addAll(productlists);
    }

    public ObservableList<KitchenDishResponse.Productlist> getTodaysItemViewModels() {
        return todaysMenuArrayViewModels;
    }

    public MutableLiveData<List<KitchenDishResponse.Productlist>> getTodaysItemsImages() {
        return todaysMenuArrayViewLiveData;
    }

}
