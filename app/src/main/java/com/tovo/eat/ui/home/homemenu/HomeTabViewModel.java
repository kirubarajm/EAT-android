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
import com.tovo.eat.ui.address.list.AddressListResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.LiveOrderResponsePojo;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.ad.bottom.PromotionRequest;
import com.tovo.eat.ui.home.ad.bottom.PromotionResponse;
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
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    public final ObservableField<String> customerName = new ObservableField<>();
    public final ObservableField<Integer> pageid = new ObservableField<>();
    public final ObservableBoolean isLiveOrder = new ObservableBoolean();
    public final ObservableBoolean showFunnel = new ObservableBoolean();
    public ObservableBoolean isVeg = new ObservableBoolean();
    public ObservableBoolean emptyRegion = new ObservableBoolean();
    public ObservableBoolean emptyKitchen = new ObservableBoolean();
    public ObservableBoolean fullEmpty = new ObservableBoolean();
    public ObservableBoolean backToTop = new ObservableBoolean();
    public ObservableBoolean paginationLoading = new ObservableBoolean();
    public ObservableBoolean regionTitleLoaded = new ObservableBoolean();
    public ObservableBoolean kitchenListLoading = new ObservableBoolean();
    public ObservableList<KitchenResponse.Result> kitchenItemViewModels = new ObservableArrayList<>();
    public ObservableList<KitchenResponse.Result> kitchenItemViewModelsTemp = new ObservableArrayList<>();
    public ObservableList<KitchenResponse.Result> kitchenItemViewModelstemp = new ObservableArrayList<>();
    public ObservableList<KitchenResponse.Collection> collectionItemViewModels = new ObservableArrayList<>();
    public ObservableList<RegionsResponse.Result> regionItemViewModels = new ObservableArrayList<>();
    public RegionsResponse regionResult;
    public ObservableBoolean favIcon = new ObservableBoolean();
    public ObservableList<StoriesResponse.Result> storiesItemViewModelstemp = new ObservableArrayList<>();
    public ObservableList<StoriesResponse.Result> storiesItemViewModels = new ObservableArrayList<>();
    public ObservableList<CouponListResponse.Result> couponListItemViewModels = new ObservableArrayList<>();
    public boolean singleTime = false;
    public int pageCount = 1;
    public boolean flagKitchen, flagRegion, flagCollocetion;
    RegionSearchModel regionSearchModel = new RegionSearchModel();
    List<StoriesResponse.Result> storiesResponseList = new ArrayList<>();
    boolean collectionAdded = false;
    boolean couponAdded = false;
    public MutableLiveData<List<KitchenResponse.Result>> kitchenItemsLiveData;
    private MutableLiveData<List<RegionsResponse.Result>> regionItemsLiveData;
    private MutableLiveData<List<KitchenResponse.Story>> storiesItemsLiveData;
    private MutableLiveData<List<KitchenResponse.Collection>> collectionItemLiveData;
    private MutableLiveData<List<CouponListResponse.Result>> couponListItemsLiveData;
    private long orderId;

    AddressListResponse addressList;

    public HomeTabViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenItemsLiveData = new MutableLiveData<>();
        regionItemsLiveData = new MutableLiveData<>();
        storiesItemsLiveData = new MutableLiveData<>();
        collectionItemLiveData = new MutableLiveData<>();
        couponListItemsLiveData = new MutableLiveData<>();
        getDataManager().saveIsFilterApplied(false);
        getDataManager().saveFilterSort(null);
        pageid.set(0);
        fullEmpty.set(false);

        showFunnel.set(getDataManager().getFunnelStatus());

        kitchenListLoading.set(true);

        customerName.set(getDataManager().getCurrentUserName());
        customerName.get();
    }


    public ObservableList<RegionsResponse.Result> getRegionListAnalytics() {
        return (regionItemViewModels);
    }

    public void scrollToTop() {
        backToTop.set(false);
        getNavigator().scrollToTop();
    }

    public void checkApiSuccess() {
        if (flagCollocetion && flagKitchen && flagRegion) {
            if (getNavigator() != null) {
                getNavigator().checkApiSuccessMetrics(pageid.get());
            }
        }
    }

    public void loadAllApis() {
        //    fetchStories();
        //     fetchCoupons();
        fetchCollections();
        fetchKitchen();
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

    public ObservableList<KitchenResponse.Result> getKitchenItemAnalyticsViewModels() {
        return kitchenItemViewModels;
    }


    public void addKitchenItemsToList(List<KitchenResponse.Result> ordersItems) {


        if (ordersItems != null) {
            if (pageid.get() == 1)
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


    public void addFavourite(long kitchenId, String fav) {

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


                                    if (response.getResult().get(0).getDeliveryVendor() != null && response.getResult().get(0).getDeliveryVendor() == 0) {
                                        if (response.getResult().get(0).getOrderstatus() > 4) {
                                            singleTime = true;
                                            getMoveitlatlng(response.getResult().get(0).getMoveitUserId());
                                        }
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
       /* if (!MvvmApp.getInstance().onCheckNetWork()) return;
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
                                        IssuesListResponse.Result kitchenResponse1 = new IssuesListResponse.Result();
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

        }*/
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

                                if (getNavigator() != null)
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
                    flagRegion = true;
                    checkApiSuccess();
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    flagRegion = true;
                    checkApiSuccess();
                    emptyRegion.set(true);
                    try {
                        if (getNavigator() != null)
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
        if (getDataManager().getAddressId() == 0L) {
            getDataManager().setCurrentAddressTitle("Current location");
            getDataManager().setCurrentLat(lat);
            getDataManager().setCurrentLng(lng);
            getNavigator().disconnectGps();
            getNavigator().loaded();
            addressTitle.set("Current location");
        }


    }

    public void closeAddressAlert() {
        getNavigator().closeAddressAlert();
    }

    public void fetchKitchen() throws NullPointerException {

        pageid.set(pageid.get() + 1);


        // if (pageid.get()>pageCount) return;


        kitchenListLoading.set(true);
        String json = "";
        if (getDataManager().getCurrentLat() == null) {

        } else {

            if (!getDataManager().getIsFav()) {

                if (!MvvmApp.getInstance().onCheckNetWork()) {

                    return;

                } else {
                    FilterRequestPojo filterRequestPojo;
                    filterRequestPojo = new FilterRequestPojo();
                    filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
                    filterRequestPojo.setLat(getDataManager().getCurrentLat());
                    filterRequestPojo.setLon(getDataManager().getCurrentLng());
                    filterRequestPojo.setPageid(pageid.get());

                    if (getDataManager().getFirstAddress() != null) {
                        filterRequestPojo.setAddress(getDataManager().getFirstAddress());
                        filterRequestPojo.setLocality(getDataManager().getFirstLocatity());
                        filterRequestPojo.setCity(getDataManager().getFirstCity());
                    }

                    Gson gson = new Gson();
                    json = gson.toJson(filterRequestPojo);
                    //  getDataManager().setFilterSort(json);


                    try {
                        setIsLoading(true);
                        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"http://192.168.1.102/tovo/infinity_kitchen.json", new JSONObject(json), new Response.Listener<JSONObject>() {
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_KITCHEN_LIST_URL, new JSONObject(json), new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                kitchenListLoading.set(false);
                                if (response != null) {

                                    KitchenResponse kitchenResponse;
                                    Gson sGson = new GsonBuilder().create();
                                    kitchenResponse = sGson.fromJson(response.toString(), KitchenResponse.class);
                                    //   pageCount=kitchenResponse.getPagecount();
                                    if (kitchenResponse != null)
                                        if (kitchenResponse.getResult() != null && kitchenResponse.getResult().size() > 0) {
                                            fullEmpty.set(false);

                                            if (pageid.get() > 1) {
                                                backToTop.set(true);
                                            } else {
                                                backToTop.set(false);
                                            }


                                            if (kitchenResponse.getResult().get(0).getServiceablestatus()) {
                                                getDataManager().setFunnelStatus(false);
                                                showFunnel.set(false);
                                            }

                                            kitchenItemsLiveData.postValue(kitchenResponse.getResult());
                                            metricsAppHome(kitchenResponse.getResult());
                                            kitchenItemViewModelsTemp.addAll(kitchenResponse.getResult());

                                            try {
                                                if (getNavigator() != null)
                                                    getNavigator().kitchenLoaded();
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            }

                                            if (pageid.get() == 1)
                                                getPromotions();


                                        } else {
                                            if (pageid.get() == 1)
                                                fullEmpty.set(true);
                                            pageid.set(pageid.get() - 1);
                                            try {
                                                if (getNavigator() != null)
                                                    getNavigator().kitchenLoaded();
                                            } catch (Exception ee) {
                                                ee.printStackTrace();
                                            }
                                        }

                                    //    getNavigator().kitchenListLoaded();

                                } else {
                                    if (pageid.get() == 1)
                                        fullEmpty.set(true);
                                    pageid.set(pageid.get() - 1);

                                    try {
                                        if (getNavigator() != null)
                                            getNavigator().kitchenLoaded();
                                    } catch (Exception ee) {
                                        ee.printStackTrace();
                                    }
                                }


                               /* try {
                                    Thread.sleep(5000);
                                    paginationLoading.set(false);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }*/
                                flagKitchen = true;
                                checkApiSuccess();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Log.e("", ""+error.getMessage());
                                flagKitchen = true;
                                checkApiSuccess();
                                if (pageid.get() == 1)
                                    fullEmpty.set(true);
                                pageid.set(pageid.get() - 1);
                                kitchenListLoading.set(false);
                                paginationLoading.set(false);
                                try {
                                    if (getNavigator() != null)
                                        getNavigator().kitchenLoaded();
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }

                            }
                        }) {

                            /**
                             * Passing some request headers
                             */
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return AppConstants.setHeaders(AppConstants.API_VERSION_TWO_TWO);
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

        pageid.set(pageid.get() + 1);

        //if (pageid.get()>pageCount) return;

        kitchenListLoading.set(true);
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
                        filterRequestPojo.setPageid(pageid.get());

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
                                kitchenListLoading.set(false);

                                if (response != null) {
                                    KitchenResponse kitchenResponse;
                                    Gson sGson = new GsonBuilder().create();
                                    kitchenResponse = sGson.fromJson(response.toString(), KitchenResponse.class);


                                    //     pageCount=kitchenResponse.getPagecount();

                                    if (kitchenResponse != null && kitchenResponse.getResult() != null && kitchenResponse.getResult().size() > 0) {

                                        if (pageid.get() > 1) {
                                            backToTop.set(true);
                                        } else {
                                            backToTop.set(false);
                                        }


                                        emptyKitchen.set(false);


                                        kitchenItemsLiveData.postValue(kitchenResponse.getResult());


                                        metricsAppHome(kitchenResponse.getResult());

                                        try {

                                            getNavigator().kitchenLoaded();
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    } else {

                                        if (pageid.get() == 1)
                                            emptyKitchen.set(true);
                                        pageid.set(pageid.get() - 1);


                                        try {

                                            getNavigator().kitchenLoaded();
                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    }

                                    //    getNavigator().kitchenListLoaded();

                                } else {

                                    if (pageid.get() == 1)
                                        emptyKitchen.set(true);
                                    pageid.set(pageid.get() - 1);
                                    try {
                                        getNavigator().kitchenLoaded();
                                    } catch (Exception ee) {
                                        ee.printStackTrace();
                                    }
                                }
                                paginationLoading.set(false);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                //   Log.e("", ""+error.getMessage());
                                if (pageid.get() == 1)
                                    emptyKitchen.set(true);
                                pageid.set(pageid.get() - 1);
                                paginationLoading.set(false);
                                kitchenListLoading.set(false);
                                try {

                                    getNavigator().kitchenLoaded();
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }

                            }
                        }) {
                            /**
                             * Passing some request headers
                             */
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return AppConstants.setHeaders(AppConstants.API_VERSION_TWO_TWO);
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
       /* try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_STORIES_LIST, StoriesResponse.class, new Response.Listener<StoriesResponse>() {
                @Override
                public void onResponse(StoriesResponse response) {


                    if (response != null) {

                        //getDataManager().setStoriesList(null);
                        //storiesResponseList.add(response.getResult());
                        if (response.getResult() != null) {

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
        }*/
    }


    public void saveStory(KitchenResponse response) {
        Gson gson = new Gson();
        String json = gson.toJson(response);
        getDataManager().setStoriesList(null);
        getDataManager().setStoriesList(json);
    }



    /*public void storiesRefresh() {
        IssuesListResponse localStoriesResponse;
        if (getDataManager().getStoriesList() != null) {
            Gson sGson = new GsonBuilder().create();
            localStoriesResponse = sGson.fromJson(getDataManager().getStoriesList(), IssuesListResponse.class);
            List<IssuesListResponse.Story> newStories = new ArrayList<>();
            List<IssuesListResponse.Story> oldStories = new ArrayList<>();
            for (int i = 0; i < localStoriesResponse.getResult().size(); i++) {
                if (localStoriesResponse.getResult().get(0).getStory().get(i).getStories().size() > 0)
                    if (!localStoriesResponse.getResult().get(0).getStory().get(i).getStories().get(localStoriesResponse.getResult().get(0).getStory().get(i).getStories().size() - 1).isSeen()) {
                        newStories.add(localStoriesResponse.getResult().get(0).getStory().get(i));
                    } else {
                        oldStories.add(localStoriesResponse.getResult().get(0).getStory().get(i));
                    }
            }
            IssuesListResponse completeStories = new IssuesListResponse();
            newStories.addAll(oldStories);



            List<IssuesListResponse.Result> result=new ArrayList<>();
            IssuesListResponse.Result kl=new IssuesListResponse.Result();
            kl.setStory(newStories);
            IssuesListResponse kitchenResponse=new IssuesListResponse();
            result.add(kl);
            kitchenResponse.setResult(result);


            completeStories.setResult(result);




            Gson gson = new Gson();
            String json = gson.toJson(completeStories);
            getDataManager().setStoriesList(null);
            getDataManager().setStoriesList(json);
            if (completeStories.getResult() != null)
                storiesItemsLiveData.setValue(completeStories.getResult().get(0).getStory());
        }
    }*/

    public void fetchCollections() throws NullPointerException {
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_COLLECTION_ICON_LIST, KitchenResponse.Result.class, new CollectionRequest(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId()), new Response.Listener<KitchenResponse.Result>() {
                @Override
                public void onResponse(KitchenResponse.Result response) {

                    if (response != null) {

                        if (response.getCollection() != null && response.getCollection().size() > 0) {
                            collectionItemLiveData.setValue(response.getCollection());
                            try {
                                if (getNavigator() != null)
                                    getNavigator().collectionLoaded();

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                    flagCollocetion = true;
                    checkApiSuccess();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    flagCollocetion = true;
                    checkApiSuccess();
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

    /*public MutableLiveData<List<StoriesResponse.Result>> getStoriesItemsImages() {
        return storiesItemsLiveData;
    }*/


    public void getMoveitlatlng(int moveitId) {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location");
        GeoFire geoFire = new GeoFire(ref);
        Query locationDataQuery = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location").child(String.valueOf(moveitId));

        locationDataQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (singleTime) {
                    if (dataSnapshot.child("l").getValue() != null) {

                        List<Double> gg = (List<Double>) dataSnapshot.child("l").getValue();
                        getOrderETA(String.valueOf(gg.get(0)), String.valueOf(gg.get(1)));
                        singleTime = true;
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

    public void getPromotions() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_PROMOTION_URL, PromotionResponse.class, new PromotionRequest(getDataManager().getCurrentUserId()), new Response.Listener<PromotionResponse>() {
                @Override
                public void onResponse(PromotionResponse response) {

                    if (response != null) {

                        if (response.getStatus()) {

                            //      getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(),response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                            if (response.getResult() != null && response.getResult().size() > 0) {
                                if (response.getResult().get(0).getShowStatus()) {


                                    if (getDataManager().getCurrentUserId().equals(getDataManager().getCurrentPromotionUserId())) {

                                        if (getDataManager().getPromotionId().equals(response.getResult().get(0).getPid())) {

                                            Date c = Calendar.getInstance().getTime();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                            String currentdate = df.format(c);

                                            String promotionDate = getDataManager().getPromotionShowedDate();

                                            if (!getDataManager().getPromotionShowedDate().equals(currentdate)) {

                                                //     if (!getDataManager().getPromotionSeen()) {

                                                if (getDataManager().getPromotionDisplayedCount() <= response.getResult().get(0).getNumberoftimes()) {
                                                    getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                                                }

                                                //    }

                                            }


                                        } else {
                                            getDataManager().savePromotionDisplayedCount(0);
                                            getDataManager().savePromotionSeen(false);
                                            getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                                        }
                                    } else {
                                        getDataManager().savePromotionDisplayedCount(0);
                                        getDataManager().savePromotionSeen(false);
                                        getNavigator().showPromotions(response.getResult().get(0).getPromotionUrl(), response.getResult().get(0).getFullScreen(), response.getResult().get(0).getPromotionType(), response.getResult().get(0).getPid());

                                    }
                                }

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

    public void metricsAppHome(List<KitchenResponse.Result> kitchenItemViewModel) {

        List<KitchenResponse.Result> kicthenListAnalytics;
        List<RegionsResponse.Result> regionListAnalytics;
        ArrayList<String> serviceableKitchenListAnalytics;
        ArrayList<String> unServiceableKitchenListAnalytics;
        ArrayList<String> regionForListAnalytics;


        try {
            kicthenListAnalytics = new ArrayList<>();
            regionListAnalytics = new ArrayList<>();
            serviceableKitchenListAnalytics = new ArrayList<>();
            unServiceableKitchenListAnalytics = new ArrayList<>();
            regionForListAnalytics = new ArrayList<>();
            int serviceableCount = 0, unServiceableCount = 0, regionCount = 0;
            StringBuilder serviceableKitchenSb = new StringBuilder();
            StringBuilder unServiceableKitchenSb = new StringBuilder();
            StringBuilder regionSb = new StringBuilder();

            //  kicthenListAnalytics = mHomeTabViewModel.kitchenItemViewModels;
            kicthenListAnalytics = kitchenItemsLiveData.getValue();
            kicthenListAnalytics.addAll(kitchenItemViewModel);
            regionListAnalytics = regionItemViewModels;

            for (int i = 0; i < kicthenListAnalytics.size(); i++) {
                if (kicthenListAnalytics.get(i).getType() == 0) {
                    if (kicthenListAnalytics.get(i).getServiceablestatus()) {
                        serviceableKitchenListAnalytics.add(String.valueOf(kicthenListAnalytics.get(i).getMakeituserid()));
                        serviceableCount = serviceableKitchenListAnalytics.size();
                        serviceableKitchenSb.append(kicthenListAnalytics.get(i).getMakeituserid()).append(",");
                    } else {
                        unServiceableKitchenListAnalytics.add(String.valueOf(kicthenListAnalytics.get(i).getMakeituserid()));
                        unServiceableCount = unServiceableKitchenListAnalytics.size();
                        unServiceableKitchenSb.append(kicthenListAnalytics.get(i).getMakeituserid()).append(",");
                    }
                }
            }

            for (int j = 0; j < regionListAnalytics.size(); j++) {
                regionForListAnalytics.add(regionListAnalytics.get(j).getRegionname());
                regionCount = regionForListAnalytics.size();
                regionSb.append(regionListAnalytics.get(j).getRegionid()).append(",");
            }

            String regionList = regionSb.toString();
            String strServiceableKitchenSb = serviceableKitchenSb.toString();
            String strUnServiceableKitchenSb = unServiceableKitchenSb.toString();
            String addressTitle = getDataManager().getCurrentAddressTitle();

            String strRegionList = "", strServiceableKitchen = "", strUnServiceableKitchen = "";
            if (regionList.length() > 0) {
                strRegionList = regionList.substring(0, regionList.length() - 1);
            }
            if (strServiceableKitchenSb.length() > 0) {
                strServiceableKitchen = strServiceableKitchenSb.substring(0, strServiceableKitchenSb.length() - 1);
            }
            if (strUnServiceableKitchenSb.length() > 0) {
                strUnServiceableKitchen = strUnServiceableKitchenSb.substring(0, strUnServiceableKitchenSb.length() - 1);
            }

            new Analytics().appHomeMetrics("", serviceableCount, unServiceableCount, regionCount, addressTitle, /*screenName,*/
                    strServiceableKitchen, strUnServiceableKitchen, strRegionList, pageid.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAddressList() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_ADD_ADDRESS_LIST_URL + getDataManager().getCurrentUserId(), AddressListResponse.class, new Response.Listener<AddressListResponse>() {
                @Override
                public void onResponse(AddressListResponse response) {
                    if (response != null && response.getResult().size() > 0) {
                        addressList = response;
                        getNavigator().addressListLoaded(true);
                    } else {
                        getNavigator().addressListLoaded(false);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getNavigator().addressListLoaded(false);
                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
            getNavigator().addressListLoaded(false);
        } catch (Exception ee) {
            getNavigator().addressListLoaded(false);
            ee.printStackTrace();

        }
    }

    public void changeAddress(int pos) {
        String title = addressList.getResult().get(pos).getAddressTitle();
        String address = addressList.getResult().get(pos).getAddress();
        String area = addressList.getResult().get(pos).getLocality();
        long aid = addressList.getResult().get(pos).getAid();
        double lat = addressList.getResult().get(pos).getLat();
        double lng = addressList.getResult().get(pos).getLon();
        getDataManager().updateCurrentAddress(title, address, lat, lng, area, aid);
        getDataManager().setCurrentAddress(address);
        updateAddressTitle();

        String tit = getDataManager().getCurrentAddressTitle();
        String tit2 = getDataManager().getCurrentAddress();
        String tit3 = getDataManager().getCurrentAddressArea();
        String tit4 = getDataManager().getCurrentLat();

        pageid.set(0);

        if (isAddressAdded()) {
           fetchKitchen();
            favIcon.set(true);

        } else {
            getNavigator().getMainLocation();
        }

    }

}
