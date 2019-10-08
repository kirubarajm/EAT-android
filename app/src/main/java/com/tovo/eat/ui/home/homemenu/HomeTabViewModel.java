package com.tovo.eat.ui.home.homemenu;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.geofire.GeoFire;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.LiveOrderResponsePojo;
import com.tovo.eat.ui.home.homemenu.collection.CollectionRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.list.RegionDetailsRequest;
import com.tovo.eat.ui.track.DeliveryTimeRequest;
import com.tovo.eat.ui.track.OrderTrackingResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableBoolean isLiveOrder = new ObservableBoolean();
    public final ObservableBoolean showFunnel = new ObservableBoolean();
    public ObservableBoolean isVeg = new ObservableBoolean();
    public ObservableBoolean emptyRegion = new ObservableBoolean();
    public ObservableBoolean emptyKitchen = new ObservableBoolean();
    public ObservableBoolean fullEmpty = new ObservableBoolean();
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
    private int orderId;

    public boolean singleTime=false;

    public HomeTabViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenItemsLiveData = new MutableLiveData<>();
        regionItemsLiveData = new MutableLiveData<>();
        storiesItemsLiveData = new MutableLiveData<>();
        collectionItemLiveData = new MutableLiveData<>();
        couponListItemsLiveData = new MutableLiveData<>();

        fullEmpty.set(false);
        showFunnel.set(getDataManager().getFunnelStatus());
    }


    public void loadAllApis() {
        fetchStories();
        fetchCoupons();
        fetchCollections();
        // fetchKitchen();
        fetchRepos(getDataManager().getRegionId());
    }

 public void closeFunnel() {
     showFunnel.set(false);
    }


    public ObservableList<KitchenResponse.Result> getKitchenItemViewModels() {
        return kitchenItemViewModels;
    }


    public MutableLiveData<List<KitchenResponse.Result>> getKitchenItemsLiveData() {
        return kitchenItemsLiveData;
    }


    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {


        if (ordersItems != null) {
            kitchenItemViewModels.clear();
            kitchenItemViewModels.addAll(ordersItems);
        }

    }

    public MutableLiveData<List<KitchenResponse.Collection>> getCollectionItemLiveData() {
        return collectionItemLiveData;
    }


    public MutableLiveData<List<CouponListResponse.Result>> getCouponListItemsLiveData() {
        return couponListItemsLiveData;
    }


    public MutableLiveData<List<RegionsResponse.Result>> getregionItemsLiveData() {
        return regionItemsLiveData;
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

    public void trackLiveOrder() {

        getNavigator().trackLiveOrder(orderId);


    }


    public void liveOrders() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_LIVE_ORDER_URL + getDataManager().getCurrentUserId(), LiveOrderResponsePojo.class, new Response.Listener<LiveOrderResponsePojo>() {
                @Override
                public void onResponse(LiveOrderResponsePojo response) {
                    if (response != null) {
                        setIsLoading(false);


                        if (response.getStatus()) {

                            if (response.getResult() != null && response.getResult().size() > 0) {

                                showFunnel.set(false);
                                getDataManager().setFunnelStatus(false);

                                if (response.getResult().get(0).isOnlinePaymentStatus()) {

                                    isLiveOrder.set(true);

                                    kitchenImage.set(response.getResult().get(0).getMakeitimage());

                                    kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                                    orderId = response.getResult().get(0).getOrderid();

                                    getDataManager().setOrderId(orderId);



                                    if (response.getResult().get(0).getOrderstatus()>4){
                                        singleTime=true;
                                        getMoveitlatlng(response.getResult().get(0).getMoveitUserId());


                                    }




                                    StringBuilder itemsBuilder = new StringBuilder();

                                    for (int i = 0; i < response.getResult().get(0).getItems().size(); i++) {
                                        itemsBuilder.append(response.getResult().get(0).getItems().get(i).getProductName());

                                        if (response.getResult().get(0).getItems().size() - 1 != i) {
                                            itemsBuilder.append(" , ");
                                        }
                                    }

                                    String item = itemsBuilder.toString();
                                    products.set(item);
                                    // 2019-05-09T13:21:54.000Z
                                    try {
                                        String strDate = response.getResult().get(0).getDeliverytime();
                                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                                        DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String outputDateStr = "";
                                        //Date  date1 = new Date(strDate);
                                        Date date = currentFormat.parse(strDate);
                                        outputDateStr = dateFormat.format(date);
                                        eta.set(outputDateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }

                                    ////////////
                                } else {
                                    isLiveOrder.set(false);
                                }


                            } else {
                                isLiveOrder.set(false);

                            }
                        } else {
                            isLiveOrder.set(false);
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    setIsLoading(false);
                    isLiveOrder.set(false);
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
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
                        if (response.getStatus()) {
                            if (null != response.getResult() && response.getResult().size() > 0) {
                                //  couponListItemViewModels.addAll(response.getResult());
                                couponListItemsLiveData.setValue(response.getResult());

                                if (!couponAdded) {
                                    if (kitchenItemViewModels.size() > 0) {
                                        KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                        kitchenResponse1.setCoupons(response.getResult());
                                        kitchenItemViewModels.add(Math.round(kitchenItemViewModels.size() / 2) + 1, kitchenResponse1);
                                        couponAdded = true;
                                        // kitchenItemsLiveData.setValue(kitchenItemViewModels);
                                    }

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
                            emptyRegion.set(true);
                            ee.printStackTrace();

                        }
                    } else {

                        emptyRegion.set(true);
                    }
                    try {
                        getNavigator().regionsLoaded(response);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        emptyRegion.set(true);
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    emptyRegion.set(true);
                    try {
                        getNavigator().regionsLoaded(null);

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        emptyRegion.set(true);
                    }

                }
            }, AppConstants.API_VERSION_TWO);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


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


        if (getDataManager().getCurrentLat() == null) {

        } else {

            if (!getDataManager().getIsFav()) {

                if (!MvvmApp.getInstance().onCheckNetWork()) {

                    return;

                } else {

                    FilterRequestPojo filterRequestPojo;
                    {
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
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_LIST_URL, new JSONObject(getDataManager().getFilterSort()), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                if (response != null) {

                                    KitchenResponse kitchenResponse;
                                    Gson sGson = new GsonBuilder().create();
                                    kitchenResponse = sGson.fromJson(response.toString(), KitchenResponse.class);

                                    if (kitchenResponse.getResult().size() > 0) {
                                        fullEmpty.set(false);


                                        if (kitchenResponse.getResult().get(0).isServiceableStatus()){
                                            getDataManager().setFunnelStatus(false);
                                            showFunnel.set(false);
                                        }


                                        if (collectionItemViewModels.size() > 0) {
                                            KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                            kitchenResponse1.setCollection(collectionItemViewModels);
                                            kitchenResponse.getResult().add(Math.round(kitchenResponse.getResult().size() / 2), kitchenResponse1);
                                            collectionAdded = true;

                                        }
                                        if (couponListItemViewModels.size() > 0) {



                                            if (kitchenResponse.getResult().size()>4){
                                                KitchenResponse.Result kitchenResponse2 = new KitchenResponse.Result();
                                                kitchenResponse2.setCoupons(couponListItemViewModels);
                                                kitchenResponse.getResult().add(2, kitchenResponse2);
                                                couponAdded = true;
                                            }else{
                                                KitchenResponse.Result kitchenResponse2 = new KitchenResponse.Result();
                                                kitchenResponse2.setCoupons(couponListItemViewModels);
                                                kitchenResponse.getResult().add(Math.round(kitchenResponse.getResult().size() / 2) + 1, kitchenResponse2);
                                                couponAdded = true;
                                            }

                                        }


                                    /*    KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                        kitchenResponse1.setMakeitbrandname("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                                        kitchenItemViewModels.clear();
                                        kitchenItemViewModels.add(3, kitchenResponse1);
                                        kitchenResponse.setResult(kitchenItemViewModels);
*/
                                        //  kitchenItemsLiveData.setValue(null);

                                        kitchenItemsLiveData.setValue(kitchenResponse.getResult());

                                        //  kitchenItemViewModelstemp.addAll(kitchenResponse.getResult());
                                        //   addKitchenItemsToList(kitchenItemViewModelstemp);
                                        try {

                                            getNavigator().kitchenLoaded();
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    } else {
                                        fullEmpty.set(true);
                                        try {

                                            getNavigator().kitchenLoaded();
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    }

                                    //    getNavigator().kitchenListLoaded();

                                } else {
                                    fullEmpty.set(true);
                                    try {

                                        getNavigator().kitchenLoaded();
                                    } catch (Exception ee) {
                                        ee.printStackTrace();
                                    }
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
                                fullEmpty.set(true);
                            }
                        }) {

                            /**
                             * Passing some request headers
                             */
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return AppConstants.setHeaders(AppConstants.API_VERSION_TWO);
                            }
                        };
                        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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

    public void fetchKitchenFilter() throws NullPointerException {

        if (getDataManager().getCurrentLat() == null) {
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


                                            if (kitchenResponse.getResult().size()>4){
                                                KitchenResponse.Result kitchenResponse2 = new KitchenResponse.Result();
                                                kitchenResponse2.setCoupons(couponListItemViewModels);
                                                kitchenResponse.getResult().add(2, kitchenResponse2);
                                                couponAdded = true;
                                            }else{
                                                KitchenResponse.Result kitchenResponse2 = new KitchenResponse.Result();
                                                kitchenResponse2.setCoupons(couponListItemViewModels);
                                                kitchenResponse.getResult().add(Math.round(kitchenResponse.getResult().size() / 2) + 1, kitchenResponse2);
                                                couponAdded = true;
                                            }




                                        }


                                    /*    KitchenResponse.Result kitchenResponse1 = new KitchenResponse.Result();
                                        kitchenResponse1.setMakeitbrandname("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

                                        kitchenItemViewModels.clear();
                                        kitchenItemViewModels.add(3, kitchenResponse1);
                                        kitchenResponse.setResult(kitchenItemViewModels);
*/
                                        //  kitchenItemsLiveData.setValue(null);

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
                                        try {

                                            getNavigator().kitchenLoaded();
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    }

                                    //    getNavigator().kitchenListLoaded();

                                } else {
                                    emptyKitchen.set(true);
                                    try {
                                        getNavigator().kitchenLoaded();
                                    } catch (Exception ee) {
                                        ee.printStackTrace();
                                    }
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
                                return AppConstants.setHeaders(AppConstants.API_VERSION_TWO);
                            }
                        };
                        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

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

                                            for (int l = 0; l < response.getResult().get(i).getStories().size(); l++) {

                                                int id = response.getResult().get(i).getStories().get(l).getId();

                                                for (int k = 0; k < localStoriesResponse.getResult().get(j).getStories().size(); k++) {
                                                    Log.i("Seen_Sid_" + sid, "" + localStoriesResponse.getResult().get(j).getStories().get(k).getId());
                                                    if (id == (localStoriesResponse.getResult().get(j).getStories().get(k).getId())) {
                                                        Log.i("Seen_Sid_" + sid + "_id_" + id, "" + localStoriesResponse.getResult().get(j).getStories().get(k).isSeen());
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
                                if (newStories.size() == 0) {
                                    completeStories.setResult(response.getResult());
                                } else {
                                    newStories.addAll(oldStories);
                                    completeStories.setResult(newStories);
                                }


                                Gson gson = new Gson();
                                String json = gson.toJson(completeStories);
                                getDataManager().setStoriesList(null);
                                getDataManager().setStoriesList(json);

                                storiesItemsLiveData.setValue(completeStories.getResult());

                                try {
                                    getNavigator().getFullStories(completeStories);

                                } catch (NullPointerException e) {
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

                                } catch (NullPointerException e) {
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

                            } catch (NullPointerException e) {
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

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


    public void storiesRefresh() {
        StoriesResponse localStoriesResponse;
        if (getDataManager().getStoriesList() != null) {
            Gson sGson = new GsonBuilder().create();
            localStoriesResponse = sGson.fromJson(getDataManager().getStoriesList(), StoriesResponse.class);
            List<StoriesResponse.Result> newStories = new ArrayList<>();
            List<StoriesResponse.Result> oldStories = new ArrayList<>();
            for (int i = 0; i < localStoriesResponse.getResult().size(); i++) {
                if (localStoriesResponse.getResult().get(i).getStories().size() > 0)
                    if (!localStoriesResponse.getResult().get(i).getStories().get(localStoriesResponse.getResult().get(i).getStories().size() - 1).isSeen()) {
                        newStories.add(localStoriesResponse.getResult().get(i));
                    } else {
                        oldStories.add(localStoriesResponse.getResult().get(i));
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

                            } catch (NullPointerException e) {
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


    public void getMoveitlatlng(int moveitId){
        DatabaseReference ref = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location");
        GeoFire geoFire = new GeoFire(ref);
        Query locationDataQuery = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location").child(String.valueOf(moveitId));

        locationDataQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ( singleTime) {
                    if (dataSnapshot.child("l").getValue() != null) {

                        List<Double> gg = (List<Double>) dataSnapshot.child("l").getValue();
                        getOrderETA(String.valueOf(gg.get(0)), String.valueOf(gg.get(1)));
                        singleTime=true;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    public void getOrderETA(String lat, String lng) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_ORDER_ETA, OrderTrackingResponse.class, new DeliveryTimeRequest(lat, lng, getDataManager().getOrderId()), new Response.Listener<OrderTrackingResponse>() {
                @Override
                public void onResponse(OrderTrackingResponse response) {

                    if (response != null) {

                        if (response.getStatus()) {


                            DateFormat dateFormat = null;

                            Date deliverydate = null;
                            String outputDateStr = "";
                            try {
                                String strDate = response.getDeliverytime();
                                dateFormat = new SimpleDateFormat("hh:mm a");

                                // DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                                DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                                //Date  date1 = new Date(strDate);
                                deliverydate = currentFormat.parse(strDate);
                                outputDateStr = dateFormat.format(deliverydate);
                                eta.set(outputDateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Log.e("", error.getMessage());
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
