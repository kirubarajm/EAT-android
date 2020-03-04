package com.tovo.eat.ui.home;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.ui.home.ad.bottom.PromotionRequest;
import com.tovo.eat.ui.home.ad.bottom.PromotionResponse;
import com.tovo.eat.ui.payment.PaymentRetryRequestPojo;
import com.tovo.eat.ui.signup.namegender.TokenRequest;
import com.tovo.eat.ui.track.DeliveryTimeRequest;
import com.tovo.eat.ui.track.OrderTrackingResponse;
import com.tovo.eat.ui.update.UpdateRequest;
import com.tovo.eat.ui.update.UpdateResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MasterPojo;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;

public class MainViewModel extends BaseViewModel<MainNavigator> {

    public static final int NO_ACTION = -1, ACTION_ADD_ALL = 0, ACTION_DELETE_SINGLE = 1;
    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableBoolean titleVisible = new ObservableBoolean();
    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchenImage = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableBoolean isLiveOrder = new ObservableBoolean();
    public final ObservableBoolean isHome = new ObservableBoolean();
    public final ObservableBoolean isExplore = new ObservableBoolean();
    public final ObservableBoolean isCart = new ObservableBoolean();
    public final ObservableBoolean isMyAccount = new ObservableBoolean();
    private final ObservableField<String> appVersion = new ObservableField<>();
    private final ObservableField<String> userEmail = new ObservableField<>();
    private final ObservableField<String> userName = new ObservableField<>();
    private final ObservableField<String> userProfilePicUrl = new ObservableField<>();
    private final ObservableField<String> numOfCarts = new ObservableField<>();
    public final ObservableField<String> updateTitle = new ObservableField<>();
    public final ObservableField<String> updateAction = new ObservableField<>();
    public final ObservableField<String> screenName = new ObservableField<>();
    public final ObservableBoolean updateAvailable = new ObservableBoolean();
    public final ObservableBoolean enableLater = new ObservableBoolean();
    public final ObservableBoolean update = new ObservableBoolean();
    public LiveOrderResponsePojo liveOrderResponsePojo;
    public Long kitchenid = 0L;
    private long orderId;
    private long payment_orderId;
    private int payment_price;
    private int action = NO_ACTION;

    public MainViewModel(DataManager dataManager) {
        super(dataManager);
        screenName.set(AppConstants.SCREEN_HOME);
        getDataManager().setIsFav(false);
        masterRequest();
        checkUpdate();
        getDataManager().setIsFilterApplied(false);
        //getDataManager().appStartedAgain(true);

        Identity identity = new AnonymousIdentity.Builder()
                .withNameIdentifier(getDataManager().getCurrentUserName())
                .withEmailIdentifier(getDataManager().getCurrentUserEmail())
                .build();
        Zendesk.INSTANCE.setIdentity(identity);

    }

    public int getAction() {
        return action;
    }

    public void selectAddress() {
        getNavigator().selectAddress();

    }

    public void gotoCart(String screenName) {
        if (!isCart.get()) {

            if (kitchenid != 0L)
                new Analytics().kitchenViewcart(AppConstants.CLICK_DIRECT_VIEW_CART, kitchenid);

            getNavigator().openCart(screenName);
            isHome.set(false);
            isExplore.set(false);
            isCart.set(true);
            isMyAccount.set(false);
        }

    }

    public void trackLiveOrder() {
        getNavigator().trackLiveOrder(orderId);
    }

    public boolean isAddressAdded() {

        if (getDataManager().getCurrentLat() != null && !getDataManager().getCurrentLat().equals("0.0")) {
            return true;
        } else {
            return false;
        }

//        return getDataManager().getCurrentLat() != null || !getDataManager().getCurrentLat().equals("0.0")|| !getDataManager().getCurrentLat().equals(0.0);
    }

    public void getMoveitLatLng(int moveitId) {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location");
        GeoFire geoFire = new GeoFire(ref);

        geoFire.getLocation(String.valueOf(moveitId), new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                if (location != null) {
                    System.out.println(String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));

                    Log.e("loc", String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));


                    getOrderETA(String.valueOf(location.latitude), String.valueOf(location.longitude));


                } else {
                    System.out.println(String.format("There is no location for key %s in GeoFire", key));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("There was an error getting the GeoFire location: " + databaseError);
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

    public void liveOrders() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_LIVE_ORDER_URL + getDataManager().getCurrentUserId(), LiveOrderResponsePojo.class, new Response.Listener<LiveOrderResponsePojo>() {
                @Override
                public void onResponse(LiveOrderResponsePojo response) {
                    if (response != null) {
                        setIsLoading(false);

                        liveOrderResponsePojo = response;

                        if (response.getStatus()) {

                            if (response.getResult() != null && response.getResult().size() > 0) {


                                if (!response.getResult().get(0).isOnlinePaymentStatus()) {
                                    isLiveOrder.set(false);
                                    payment_orderId = response.getResult().get(0).getOrderid();

                                    StringBuilder itemsBuilder = new StringBuilder();

                                    for (int i = 0; i < response.getResult().get(0).getItems().size(); i++) {
                                        itemsBuilder.append(response.getResult().get(0).getItems().get(i).getProductName()).append("[").append(response.getResult().get(0).getItems().get(i).getQuantity()).append("]");

                                        if (response.getResult().get(0).getItems().size() - 1 != i) {
                                            itemsBuilder.append(" , ");
                                        }
                                    }

                                    String item = itemsBuilder.toString();


                                    getNavigator().paymentPending(response.getResult().get(0).getOrderid(), response.getResult().get(0).getMakeitbrandname(), response.getResult().get(0).getPrice(), item);

                                } else {

                                    isLiveOrder.set(true);

                                    kitchenImage.set(response.getResult().get(0).getMakeitimage());

                                    if (response.getResult().get(0).getMakeitbrandname().isEmpty()) {
                                        kitchenName.set(response.getResult().get(0).getMakeitusername());
                                    } else {

                                        kitchenName.set(response.getResult().get(0).getMakeitbrandname());
                                    }
                                    // 2019-05-09T13:21:54.000Z


                                    if (response.getResult().get(0).getOrderstatus() > 4) {
                                        if (response.getResult().get(0).getMoveitUserId() != null) {
                                            getMoveitLatLng(response.getResult().get(0).getMoveitUserId());
                                        }
                                    }

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


                                }

                                ////////////
                            } else {
                                isLiveOrder.set(false);
                            }


                        } else {
                            isLiveOrder.set(false);

                            if (response.getResult() != null && response.getResult().size() > 0) {


                                payment_orderId = response.getResult().get(0).getOrderid();
                                payment_price = response.getResult().get(0).getPrice();


                                if (!response.getResult().get(0).isOnlinePaymentStatus()) {

                                    StringBuilder itemsBuilder = new StringBuilder();

                                    for (int i = 0; i < response.getResult().get(0).getItems().size(); i++) {
                                        itemsBuilder.append(response.getResult().get(0).getItems().get(i).getProductName()).append("[").append(response.getResult().get(0).getItems().get(i).getQuantity()).append("]");

                                        if (response.getResult().get(0).getItems().size() - 1 != i) {
                                            itemsBuilder.append(" , ");
                                        }
                                    }

                                    String item = itemsBuilder.toString();


                                    getNavigator().paymentPending(response.getResult().get(0).getOrderid(), response.getResult().get(0).getMakeitbrandname(), response.getResult().get(0).getPrice(), item);

                                }

                            }
                        }


                        if (response.getOrderdetails() != null && response.getOrderdetails().size() > 0) {

                            boolean st = response.getOrderdetails().get(0).getRating();

                            if (!st) {

                                if (getDataManager().getRatingAppStatus()) {

                                    if (response.getOrderdetails().get(0).getShowRating()) {
                                        getNavigator().showOrderRating(response.getOrderdetails().get(0).getOrderid(), response.getOrderdetails().get(0).getBrandname());
                                    }

                                }
                            }
                        }

                    } else {
                        isLiveOrder.set(false);
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

    public void saveToken(String token) {
        long userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_FCM_TOKEN_URL, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        if (response.isStatus()) {


                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void gotoAccount() {

        if (!isMyAccount.get()) {
            getNavigator().openAccount();
            isHome.set(false);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(true);
        }


    }

    public void gotoExplore() {

        if (!isExplore.get()) {

            getNavigator().openExplore();
            isHome.set(false);
            isExplore.set(true);
            isCart.set(false);
            isMyAccount.set(false);

        }
    }

    public void gotoHome() {


        if (!isHome.get()) {
            getDataManager().setIsFav(false);
            getNavigator().openHome();
            isHome.set(true);
            isExplore.set(false);
            isCart.set(false);
            isMyAccount.set(false);

        }
    }

    public boolean totalCart() {
        try {
            Gson sGson = new GsonBuilder().create();
            CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);
            cart.set(false);
            if (cartRequestPojo == null)
                cartRequestPojo = new CartRequestPojo();
            int count = 0;

            if (cartRequestPojo.getCartitems() != null) {
                if (cartRequestPojo.getCartitems().size() == 0) {
                    cart.set(false);
                } else {


                    kitchenid = cartRequestPojo.getMakeitUserid();


                    for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {

                        count = count + cartRequestPojo.getCartitems().get(i).getQuantity();

                        if (count == 0) {
                            cart.set(false);
                            return false;

                        } else {
                            numOfCarts.set(String.valueOf(count));
                            cart.set(true);
                        }

                    }

                }
            }
        } catch (Exception ee) {

            ee.printStackTrace();

        }
        return false;
    }

    public ObservableField<String> getUserEmail() {
        return userEmail;
    }

    public ObservableField<String> getUserName() {
        return userName;
    }


    public ObservableField<String> getNumOfCarts() {
        return numOfCarts;
    }


    public void masterRequest() {
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER, MasterPojo.class, new Response.Listener<MasterPojo>() {
                @Override
                public void onResponse(MasterPojo response) {
                    if (response != null) {

                        Gson gson = new Gson();
                        String master = gson.toJson(response);
                        getDataManager().saveMaster("");
                        getDataManager().saveMaster(master);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Log.e("", error.getMessage());
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

    public boolean checkInternet() {
        return MvvmApp.getInstance().onCheckNetWork();
    }

    public void saveRequestData() {

        FilterRequestPojo filterRequestPojo;
        if (getDataManager().getFilterSort() != null) {
            Gson sGson = new GsonBuilder().create();
            filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);
            filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
            filterRequestPojo.setLat(getDataManager().getCurrentLat());
            filterRequestPojo.setLon(getDataManager().getCurrentLng());

            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);
            //   getDataManager().setFilterSort(json);
        } else {
            filterRequestPojo = new FilterRequestPojo();
            filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
            filterRequestPojo.setLat(getDataManager().getCurrentLat());
            filterRequestPojo.setLon(getDataManager().getCurrentLng());
            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);
            // getDataManager().setFilterSort(json);
        }
    }

    public void currentLatLng(double lat, double lng) {
        getDataManager().setCurrentAddressTitle("Current location");
        getDataManager().setCurrentLat(lat);
        getDataManager().setCurrentLng(lng);
        getNavigator().disConnectGPS();
        // getNavigator().openHome();
    }


    public void paymentSuccess(String paymentId, Integer status) {


        if (status == 1) {

            new Analytics().paymentSuccess(payment_orderId, payment_price);

        } else {
            new Analytics().paymentFailed(payment_orderId, payment_price);
        }


        JsonObjectRequest jsonObjectRequest = null;
        try {

            JSONObject json = new JSONObject();
            json.put("transactionid", paymentId);
            json.put("payment_status", status);
            json.put("orderid",payment_orderId);


            if (getDataManager().getCouponId() != 0) {
                json.put("cid", getDataManager().getCouponId());
            }

            if (getDataManager().getRefundId() != 0) {
                json.put("rcid", getDataManager().getRefundId());
                json.put("refund_balance", getDataManager().getRefundBalance());
            }


            Log.e("asdaf", json.toString());

            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_ORDER_SUCCESS, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (response.getBoolean("status")) {

                            new Analytics().orderPlaced(payment_orderId, payment_price);

                            getDataManager().setCartDetails(null);
                            getDataManager().saveRefundId(0);
                            getDataManager().saveCouponId(0);
                        }
                        getNavigator().paymentStausChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e("", error.getMessage());
                    //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

        MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

    }


    public void retry(){
        int price = liveOrderResponsePojo.getResult().get(0).getPrice();
        Long orderId =liveOrderResponsePojo.getResult().get(0).getOrderid();
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_PAYMENT_RETRY_URL, CommonResponse.class, new PaymentRetryRequestPojo(getDataManager().getCurrentUserId(),orderId),new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        if (response.isStatus()){

                            new Analytics().orderPlaced(orderId, price);

                            getDataManager().setCartDetails(null);
                            getDataManager().saveRefundId(0);
                            getDataManager().saveCouponId(0);

                        }else {
                            if (getNavigator()!=null)
                                getNavigator().retryPaymentForSamePrderID();
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

    public void checkUpdate() {
        /*   MvvmApp.getInstance().getVersionCode()*/

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FCM_FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(MvvmApp.getInstance().getVersionCode()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {

                if (response != null)
                    if (response.getResult() != null && response.getStatus()) {
                        if (getNavigator() != null)
                            getNavigator().update(response.getResult().getVersionstatus(), response.getResult().getEatforceupdate());
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);


    }

    public void getPromotions() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;


        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_ORDER_ETA, PromotionResponse.class,new PromotionRequest(getDataManager().getCurrentUserId()), new Response.Listener<PromotionResponse>() {
                @Override
                public void onResponse(PromotionResponse response) {

                    if (response != null) {

                        if (response.getStatus()) {


                           /* if (response.getResult().getShowStatus()){
                                getNavigator().showPromotions(response.getResult().getUrl(),response.getResult().getFullScreen(),response.getResult().getContentType());

                            }*/

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
