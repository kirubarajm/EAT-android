package com.tovo.eat.ui.home.homemenu;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
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
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.homemenu.collection.CollectionRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.list.RegionDetailsRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeTabViewModel extends BaseViewModel<HomeTabNavigator> {

    public final ObservableField<String> firstStory = new ObservableField<>();
    public final ObservableField<String> firstRegion = new ObservableField<>();
    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> region1 = new ObservableField<>();
    public final ObservableField<String> region2 = new ObservableField<>();
    public final ObservableField<String> slogan1 = new ObservableField<>();
    public final ObservableField<String> slogan2 = new ObservableField<>();
    public ObservableBoolean isVeg = new ObservableBoolean();
    public ObservableBoolean emptyRegion = new ObservableBoolean();
    public ObservableBoolean emptyKitchen = new ObservableBoolean();
    public ObservableBoolean regionTitleLoaded = new ObservableBoolean();
    public ObservableList<KitchenResponse.Result> kitchenItemViewModels = new ObservableArrayList<>();
    public ObservableList<KitchenResponse.Result> kitchenItemViewModelstemp = new ObservableArrayList<>();
    public ObservableList<KitchenResponse.Collection> collectionItemViewModels = new ObservableArrayList<>();
    public ObservableList<RegionsResponse.Result> regionItemViewModels = new ObservableArrayList<>();
    public RegionsResponse regionResult;
    public ObservableBoolean favIcon = new ObservableBoolean();
    public ObservableList<StoriesResponse.Result> storiesItemViewModelstemp = new ObservableArrayList<>();
    public ObservableList<StoriesResponse.Result> storiesItemViewModels = new ObservableArrayList<>();
    public ObservableList<CouponListResponse.Result> couponListItemViewModels = new ObservableArrayList<>();
    RegionSearchModel regionSearchModel = new RegionSearchModel();
    List<StoriesResponse.Result> storiesResponseList = new ArrayList<>();
    boolean collectionAdded = false;
    boolean couponAdded = false;
    private MutableLiveData<List<KitchenResponse.Result>> kitchenItemsLiveData;
    private MutableLiveData<List<RegionsResponse.Result>> regionItemsLiveData;
    private MutableLiveData<List<StoriesResponse.Result>> storiesItemsLiveData;
    private MutableLiveData<List<KitchenResponse.Collection>> collectionItemLiveData;
    private MutableLiveData<List<CouponListResponse.Result>> couponListItemsLiveData;


    public HomeTabViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenItemsLiveData = new MutableLiveData<>();
        regionItemsLiveData = new MutableLiveData<>();
        storiesItemsLiveData = new MutableLiveData<>();
        collectionItemLiveData = new MutableLiveData<>();
        couponListItemsLiveData = new MutableLiveData<>();

       /* if (getDataManager().getVegType() == 1) {
            isVeg.set(true);
        }*/
    }


    public void loadAllApis() {
        fetchStories();
        fetchCoupons();
        fetchCollections();
        fetchKitchen();
        fetchRepos(getDataManager().getRegionId());
    }


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

    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {
        kitchenItemViewModels.clear();
/*
        //  if (collectionItemViewModels.size() > 0) {
        KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
        kitchenResponse1.setCollection(collectionItemViewModels);
        ordersItems.add(Math.round(ordersItems.size() / 2), kitchenResponse1);
        //   }
        //  if (couponListItemViewModels.size() > 0) {
        KitchenResponse.Result kitchenResponse2 = new KitchenResponse.Result();
        kitchenResponse2.setCoupons(couponListItemViewModels);
        ordersItems.add(2, kitchenResponse2);
        //  }*/

        kitchenItemViewModels.addAll(ordersItems);

    }

    public MutableLiveData<List<KitchenResponse.Collection>> getCollectionItemLiveData() {
        return collectionItemLiveData;
    }

    public void setCollectionItemLiveData(MutableLiveData<List<KitchenResponse.Collection>> collectionItemLiveData) {
        this.collectionItemLiveData = collectionItemLiveData;
    }

    public MutableLiveData<List<CouponListResponse.Result>> getCouponListItemsLiveData() {
        return couponListItemsLiveData;
    }

    public void setCouponListItemsLiveData(MutableLiveData<List<CouponListResponse.Result>> couponListItemsLiveData) {
        this.couponListItemsLiveData = couponListItemsLiveData;
    }

    public ObservableList<RegionsResponse.Result> getregionItemViewModels() {
        return regionItemViewModels;
    }

    public void setregionItemViewModels(ObservableList<RegionsResponse.Result> regionItemViewModels) {
        this.regionItemViewModels = regionItemViewModels;
    }

    public MutableLiveData<List<RegionsResponse.Result>> getregionItemsLiveData() {
        return regionItemsLiveData;
    }

    public void setregionItemsLiveData(MutableLiveData<List<RegionsResponse.Result>> regionItemsLiveData) {
        this.regionItemsLiveData = regionItemsLiveData;
    }

    public void addRegionItemsToList(List<RegionsResponse.Result> ordersItems) {
        regionItemViewModels.clear();
        regionItemViewModels.addAll(ordersItems);
    }


    public void addCouponItemsToList(List<CouponListResponse.Result> ordersItems) {
        couponListItemViewModels.clear();
        couponListItemViewModels.addAll(ordersItems);
    }

    public void addCollectionItemsToList(List<KitchenResponse.Collection> ordersItems) {
        collectionItemViewModels.clear();
        collectionItemViewModels.addAll(ordersItems);
    }


    public void vegType() {
       /* if (isVeg.get()) {
            isVeg.set(false);
            getDataManager().saveVegType(0);

        } else {
            isVeg.set(true);
            getDataManager().saveVegType(1);
        }

        fetchKitchen();*/
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


                        //      getNavigator().toastMessage(response.getMessage());

                        //fetchRepos();

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


    public void addFavourite(Integer kitchenId, String fav) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new KitchenFavRequest(String.valueOf(getDataManager().getCurrentUserId()), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


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


    public void fetchCoupons() throws NullPointerException {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_COUPON_LIST_URL, CouponListResponse.class, new CollectionRequest(getDataManager().getCurrentUserId()), new Response.Listener<CouponListResponse>() {
                @Override
                public void onResponse(CouponListResponse response) {
                    if (response != null) {

                        if (response.getResult().size() > 0) {
                            //  couponListItemViewModels.addAll(response.getResult());
                            couponListItemsLiveData.setValue(response.getResult());

                            if (!couponAdded) {
                                if (kitchenItemViewModels.size() > 0) {
                                    KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                    kitchenResponse1.setCoupons(response.getResult());
                                    kitchenItemViewModels.add(Math.round(kitchenItemViewModels.size() / 2)+1, kitchenResponse1);
                                    couponAdded = true;
                                    // kitchenItemsLiveData.setValue(kitchenItemViewModels);
                                }

                            }

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Log.e("", error.getMessage());
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


    public void fetchRepos(Integer regionId) throws NullPointerException {


        //   if (getDataManager().getCurrentLat() == null) {

        //   getNavigator().kitchenListLoading();


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_REGION_LIST, RegionsResponse.class, new RegionDetailsRequest(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId(), regionId, getDataManager().getVegType()), new Response.Listener<RegionsResponse>() {
                @Override
                public void onResponse(RegionsResponse response) {


                    if (response != null) {

                        try {
                            if (response.getResult().size() > 0) {
                                emptyRegion.set(false);
                                //getNavigator().regionsLoaded(response);
                                regionItemsLiveData.setValue(response.getResult());


                                region1.set(response.getResult().get(0).getRegionname());
                                slogan1.set(response.getResult().get(0).getTagline());

                                regionTitleLoaded.set(true);
                            } else {
                                emptyRegion.set(true);


                                getNavigator().dataLoaded();


                            }
                            Log.e("Region----response:", response.toString());

                            regionResult = response;


                        } catch (Exception ee) {

                            ee.printStackTrace();

                        }
                    }
                    try{
                    getNavigator().regionsLoaded(response);

                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    try {
                    getNavigator().regionsLoaded(null);

                }catch (NullPointerException e){
                    e.printStackTrace();
                }

                }
            }, AppConstants.API_VERSION_TWO);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


       /*
        } else {
            if (!MvvmApp.getInstance().onCheckNetWork()) {

                getNavigator().kitchenListLoaded();
                return;

            } else {

                FilterRequestPojo filterRequestPojo;

                if (getDataManager().getFilterSort() != null) {

                    Gson sGson = new GsonBuilder().create();
                    filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);
                    filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                    filterRequestPojo.setLat(getDataManager().getCurrentLat());
                    filterRequestPojo.setLon(getDataManager().getCurrentLng());


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
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_REGION_LIST, new JSONObject(getDataManager().getFilterSort()), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (response != null) {
                                RegionsResponse regionsResponse;
                                Gson sGson = new GsonBuilder().create();
                                regionsResponse = sGson.fromJson(response.toString(), RegionsResponse.class);

                                regionItemsLiveData.setValue(regionsResponse.getResult());

                                Log.e("Kitchen----response:", response.toString());


                                getNavigator().kitchenListLoaded();

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Log.e("", error.getMessage());
                            getNavigator().kitchenListLoaded();
                        }
                    }) {

                        *//**
         * Passing some request headers
         *//*
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            return headers;
                        }
                    };

                    MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (JSONException j) {
                    j.printStackTrace();
                }


            }*/

        // }
    }

    public void selectAddress() {
        getNavigator().selectAddress();

    }

    public String updateAddressTitle() {
        addressTitle.set(getDataManager().getCurrentAddressTitle());
        return getDataManager().getCurrentAddressTitle();

    }

    public void filter() {
        getNavigator().filter();
    }

    public void setCurrentFragment(Integer id) {
        getDataManager().setCurrentFragment(id);
    }

    public void favourites() {
        getDataManager().setIsFav(true);
        getNavigator().favourites();
    }

    public boolean isAddressAdded() {

        if (getDataManager().getCurrentLat() == null) {

            return false;
        } else {
            favIcon.set(true);
            return true;
        }

    }

    public void currentLatLng(double lat, double lng) {
        if (getDataManager().getAddressId() == 0) {
            getDataManager().setCurrentAddressTitle("Current location");
            getDataManager().setCurrentLat(lat);
            getDataManager().setCurrentLng(lng);
            getNavigator().disconnectGps();
            getNavigator().loaded();
            addressTitle.set("Current location");
        }


    }

    public void fetchKitchen() throws NullPointerException {

        // getNavigator().kitchenListLoading();


        if (getDataManager().getCurrentLat() == null) {

            //   getNavigator().kitchenListLoading();


        } else {

            if (!getDataManager().getIsFav()) {

                if (!MvvmApp.getInstance().onCheckNetWork()) {

                    return;

                } else {

                    FilterRequestPojo filterRequestPojo;

                    if (getDataManager().getFilterSort() != null) {

                        Gson sGson = new GsonBuilder().create();
                        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

                        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                        filterRequestPojo.setLat(getDataManager().getCurrentLat());
                        filterRequestPojo.setLon(getDataManager().getCurrentLng());
                        //    filterRequestPojo.setVegtype(getDataManager().getVegType());

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
                        filterRequestPojo.setVegtype(getDataManager().getVegType());

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

                                    if (kitchenResponse.getResult().size() > 0) {
                                        emptyKitchen.set(false);


                                        if (collectionItemViewModels.size() > 0) {
                                            KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                            kitchenResponse1.setCollection(collectionItemViewModels);
                                            kitchenResponse.getResult().add(Math.round(kitchenResponse.getResult().size() / 2), kitchenResponse1);
                                            collectionAdded = true;
                                        }
                                        if (couponListItemViewModels.size() > 0) {
                                            KitchenResponse.Result kitchenResponse2 = new KitchenResponse.Result();
                                            kitchenResponse2.setCoupons(couponListItemViewModels);
                                            kitchenResponse.getResult().add(Math.round(kitchenResponse.getResult().size() / 2)+1, kitchenResponse2);
                                            couponAdded = true;
                                        }


                                    /*    KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                        kitchenResponse1.setMakeitbrandname("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                                        kitchenItemViewModels.clear();
                                        kitchenItemViewModels.add(3, kitchenResponse1);
                                        kitchenResponse.setResult(kitchenItemViewModels);
*/
                                        kitchenItemsLiveData.setValue(kitchenResponse.getResult());

                                        //  kitchenItemViewModelstemp.addAll(kitchenResponse.getResult());
                                        //   addKitchenItemsToList(kitchenItemViewModelstemp);
                                        try {

                                            getNavigator().kitchenLoaded();
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    } else {
                                        emptyKitchen.set(true);
                                    }

                                    //    getNavigator().kitchenListLoaded();

                                } else {
                                    emptyKitchen.set(true);
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Log.e("", ""+error.getMessage());
                                try {

                                    getNavigator().kitchenLoaded();
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                                emptyKitchen.set(true);
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
                                //  headers.put("Authorization","Bearer");
                                headers.put("Authorization", "Bearer " + getDataManager().getApiToken());
                                return headers;
                            }
                        };

                        MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (JSONException j) {
                        j.printStackTrace();
                    } catch (Exception ee) {

                        ee.printStackTrace();

                    }


                }


            }
        }
    }

    public void fetchStories() throws NullPointerException {
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_STORIES_LIST, StoriesResponse.class, new Response.Listener<StoriesResponse>() {
                @Override
                public void onResponse(StoriesResponse response) {




                    if (response != null) {

                        //getDataManager().setStoriesList(null);
                        //storiesResponseList.add(response.getResult());

                        for (int i = 0; i < response.getResult().size(); i++) {

                            if (response.getResult().get(i).getStories().size() > 0) {
                                StoriesResponse.Result.Story result = new StoriesResponse.Result.Story();
                                result.setTitle(response.getResult().get(i).getTitle());
                                result.setSubtitle(response.getResult().get(i).getDescription());
                                result.setUrl(response.getResult().get(i).getStoryImg());
                                result.setDuration(response.getResult().get(i).getDuration());
                                result.setMediatype(0);

                                response.getResult().get(i).getStories().add(0, result);
                            }
                        }


                        StoriesResponse localStoriesResponse = new StoriesResponse();

                        Gson sGson = new GsonBuilder().create();
                        localStoriesResponse = sGson.fromJson(getDataManager().getStoriesList(), StoriesResponse.class);


                        if (localStoriesResponse != null) {
                            if (localStoriesResponse.getResult().size() > 0) {

                                for (int i = 0; i < response.getResult().size(); i++) {

                                    int sid = response.getResult().get(i).getStoryid();

                                    for (int j = 0; j < localStoriesResponse.getResult().size(); j++) {

                                        if (sid == localStoriesResponse.getResult().get(j).getStoryid()) {

                                            for (int l = 0; l < response.getResult().get(j).getStories().size(); l++) {

                                                int id = response.getResult().get(j).getStories().get(l).getId();

                                                for (int k = 0; k < localStoriesResponse.getResult().get(j).getStories().size(); k++) {

                                                    if (id == (localStoriesResponse.getResult().get(j).getStories().get(k).getId())) {

                                                        response.getResult().get(i).getStories().get(k).setSeen(localStoriesResponse.getResult().get(j).getStories().get(k).isSeen());

                                                    }

                                                }
                                            }

                                        }

                                    }
                                }


                                List<StoriesResponse.Result> newStories = new ArrayList<>();
                                List<StoriesResponse.Result> oldStories = new ArrayList<>();


                                for (int i = 0; i < response.getResult().size(); i++) {

                                    if (response.getResult().get(i).getStories().size() > 0)

                                        if (!response.getResult().get(i).getStories().get(response.getResult().get(i).getStories().size() - 1).isSeen()) {

                                            newStories.add(response.getResult().get(i));

                                        } else {

                                            oldStories.add(response.getResult().get(i));
                                        }


                                }

                                StoriesResponse completeStories = new StoriesResponse();
                                newStories.addAll(oldStories);
                                completeStories.setResult(newStories);


                                Gson gson = new Gson();
                                String json = gson.toJson(completeStories);
                                getDataManager().setStoriesList(null);
                                getDataManager().setStoriesList(json);

                                storiesItemsLiveData.setValue(completeStories.getResult());

                                try {
                                getNavigator().getFullStories(completeStories);

                                }catch (NullPointerException e){
                                    e.printStackTrace();
                                }


                            } else {
                                Gson gson = new Gson();
                                String json = gson.toJson(response);
                                getDataManager().setStoriesList(null);
                                getDataManager().setStoriesList(json);

                                storiesItemsLiveData.setValue(response.getResult());
                                try {
                                getNavigator().getFullStories(response);

                            }catch (NullPointerException e){
                                e.printStackTrace();
                            }

                        }

                        } else {

                            Gson gson = new Gson();
                            String json = gson.toJson(response);
                            getDataManager().setStoriesList(null);
                            getDataManager().setStoriesList(json);


                            if (null != response.getResult())
                                storiesItemsLiveData.setValue(response.getResult());
                            try {
                            getNavigator().getFullStories(response);

                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }

                    }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        getNavigator().getFullStories(null);

                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void fetchCollections() throws NullPointerException {
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_COLLECTION_LIST, KitchenResponse.Result.class, new CollectionRequest(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId()), new Response.Listener<KitchenResponse.Result>() {
                @Override
                public void onResponse(KitchenResponse.Result response) {
                    if (response != null) {
                        if (response.getCollection().size() > 0) {
                            collectionItemLiveData.setValue(response.getCollection());
                            try {
                            getNavigator().collectionLoaded();

                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }

                            if (!collectionAdded) {
                                if (kitchenItemViewModels.size() > 0) {
                                    KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                    kitchenResponse1.setCollection(collectionItemViewModels);
                                    kitchenItemViewModels.add(Math.round(kitchenItemViewModels.size() / 2), kitchenResponse1);
                                    collectionAdded = true;
                                    // kitchenItemsLiveData.setValue(kitchenItemViewModels);
                                }

                            }


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
        }

    }

    public void addStoriesImagesList(List<StoriesResponse.Result> resultList) {
        storiesItemViewModels.clear();
        storiesItemViewModels.addAll(resultList);


    }

    public ObservableList<StoriesResponse.Result> getStoriesItemViewModels() {
        return storiesItemViewModels;
    }

    public MutableLiveData<List<StoriesResponse.Result>> getStoriesItemsImages() {
        return storiesItemsLiveData;
    }
}
