package com.tovo.eat.ui.track;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import com.tovo.eat.R;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderTrackingViewModel extends BaseViewModel<OrderTrackingNavigator> {


    public final ObservableField<String> lng = new ObservableField<>();
    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> moveitName = new ObservableField<>();
    public final ObservableField<String> moveitPhone = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();
    public final ObservableField<String> sItems = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();
    public final ObservableField<String> orderTime = new ObservableField<>();


    public final ObservableField<String> orderReceivedStatus = new ObservableField<>();
    public final ObservableField<String> orderPreparedStatus = new ObservableField<>();
    public final ObservableField<String> orderDeliveryStatus = new ObservableField<>();


    public final ObservableBoolean isReeceived = new ObservableBoolean();
    public final ObservableBoolean isPrepared = new ObservableBoolean();
    public final ObservableBoolean isDeliverd = new ObservableBoolean();
    public final ObservableBoolean orderAccepted = new ObservableBoolean();
    public final ObservableBoolean delivered = new ObservableBoolean();

    public final ObservableBoolean dataLoaded = new ObservableBoolean();

    public final ObservableBoolean dunzoOrder = new ObservableBoolean();

    public final ObservableBoolean dunzoTrackingTimer = new ObservableBoolean();

    public final ObservableBoolean iconReeceived = new ObservableBoolean();
    public final ObservableBoolean iconPrepared = new ObservableBoolean();
    public final ObservableBoolean iconDeliverd = new ObservableBoolean();
    public final ObservableBoolean isCanceled = new ObservableBoolean();


    public final ObservableField<String> deliveredORcancel = new ObservableField<>();
    public final ObservableField<String> iconDeliveredORcancel = new ObservableField<>();


    public final ObservableBoolean track = new ObservableBoolean();


    public OrderTrackingResponse orderTrackingResponse;

    public CountDownTimer countDownTimer;


    public boolean singleTime = false;

    String deliveryManNumber;
    String dunzoTaskid;

    String dunzoClientId = "c6c3acd5-e254-4404-ad06-c0c1d2aafd1e";
    String dunzoAuth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkIjp7InJvbGUiOjEwMCwidWlkIjoiZTRiNDU5ZTktODIxOS00M2Q2LWEyYWQtZDJlODhkOTBlYmI1In0sIm1lcmNoYW50X3R5cGUiOm51bGwsImNsaWVudF9pZCI6ImM2YzNhY2Q1LWUyNTQtNDQwNC1hZDA2LWMwYzFkMmFhZmQxZSIsImF1ZCI6Imh0dHBzOi8vaWRlbnRpdHl0b29sa2l0Lmdvb2dsZWFwaXMuY29tL2dvb2dsZS5pZGVudGl0eS5pZGVudGl0eXRvb2xraXQudjEuSWRlbnRpdHlUb29sa2l0IiwibmFtZSI6IkFQSVVTRVIiLCJ1dWlkIjoiZTRiNDU5ZTktODIxOS00M2Q2LWEyYWQtZDJlODhkOTBlYmI1Iiwicm9sZSI6MTAwLCJkdW56b19rZXkiOiIwNmRkZGU1Yy1jODlkLTRiZjgtYjBhMi0wY2Q3NWE2NTVkYWQiLCJleHAiOjE3MjkzNDA1NTYsInYiOjAsImlhdCI6MTU3MzgyMDU1Niwic2VjcmV0X2tleSI6Ik5vbmUifQ.HsPdmLdbiTyUonH6oJ07WivrgytvFBiJOgxsIhNMvGE";


    public OrderTrackingViewModel(DataManager dataManager) {
        super(dataManager);
        // getOrderDetails();
        track.set(false);
        orderId.set("order #" + String.valueOf(getDataManager().getOrderId()));
        dunzoTrackingTimer.set(false);
    }

    public void callDeliveryMan() {

        getNavigator().callDeliveryMan(deliveryManNumber);

    }

    public void backClick() {
        getNavigator().clickBack();

    }


    public void orderDetails() {
        getNavigator().orderDetails(getDataManager().getOrderId());

    }

    public void getOrderDetails(Long orderid) {


        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_ORDER_DETAILS_URL + orderid, OrderTrackingResponse.class, new Response.Listener<OrderTrackingResponse>() {
                @Override
                public void onResponse(OrderTrackingResponse response) {

                    dataLoaded.set(true);

                    orderTrackingResponse = response;

                    if (response != null) {


                        // getNavigator().orderPickedUp(response.getResult().get(0).getMoveitUserId());


                        Log.e("----response:---------", response.toString());

                        if (response.getStatus()) {


                            if (response.getResult() != null && response.getResult().size() > 0) {


                                // getNavigator().orderPickedUp(response.getResult().get(0).getMoveitUserId());

                                addressTitle.set(response.getResult().get(0).getLocality());

                                if (response.getResult().get(0).getMakeitdetail().getBrandName() != null) {
                                    kitchenName.set(response.getResult().get(0).getMakeitdetail().getBrandName());

                                } else {
                                    kitchenName.set(response.getResult().get(0).getMakeitdetail().getName());
                                }

                                DateFormat dateFormat = null;

                                Date deliverydate = null;
                                String outputDateStr = "";
                                try {
                                    String strDate = response.getResult().get(0).getDeliverytime();
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


                                try {
                                    String strDate = response.getResult().get(0).getOrdertime();
                                    DateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                    DateFormat currentFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String outputDateStr2 = "";
                                    //Date  date1 = new Date(strDate);
                                    Date date = currentFormat2.parse(strDate);
                                    outputDateStr2 = dateFormat2.format(date);

                                    orderTime.set(outputDateStr2);

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                } catch (Exception e) {
                                    e.printStackTrace();

                                }


                                if (response.getResult().get(0).getDeliveryVendor()!=null&&response.getResult().get(0).getDeliveryVendor() == 0) {
                                    dunzoOrder.set(false);
                                    if (response.getResult().get(0).getOrderstatus() > 4) {
                                        singleTime = true;
                                        getMoveitlatlng(response.getResult().get(0).getMoveitUserId());
                                    }
                                }else {
                                    if (response.getResult().get(0).getOrderstatus() > 4) {
                                        if (response.getResult().get(0).getOrdertype()==0){
                                            dunzoOrder.set(true);
                                        }
                                    }
                                }


                          /*  Date dDate= null;

                            Calendar currentCal= Calendar.getInstance();

                            Date currentDate=currentCal.getTime();

                           String currentDae=dateFormat.format(currentDate);

                            try {
                                currentDate  = dateFormat.parse(currentDae);
                                dDate= dateFormat.parse(outputDateStr);


                                long diff = dDate.getTime()-currentDate.getTime();
                                long seconds = diff / 1000;
                                long minutes = seconds / 60;
                                long hours = minutes / 60;
                                long days = hours / 24;

                                Log.e("MINUTS", String.valueOf(minutes));

                                getNavigator().startTrackingTimer(minutes);


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }*/


                                if (response.getResult().get(0).getMoveitdetail() != null && response.getResult().get(0).getMoveitdetail().getName() != null) {
                                    track.set(true);
                                    moveitName.set(response.getResult().get(0).getMoveitdetail().getName());
                                    moveitPhone.set(response.getResult().get(0).getMoveitdetail().getPhoneno());
                                    deliveryManNumber = response.getResult().get(0).getMoveitdetail().getPhoneno();
                                }else {
                                    track.set(false);
                                }


                                if (getNavigator() != null)
                                    getNavigator().tracking(response.getResult().get(0).getCusLat(), response.getResult().get(0).getCusLon(), response.getResult().get(0).getMakeitdetail().getLat(), response.getResult().get(0).getMakeitdetail().getLon());


                                StringBuilder itemsBuilder = new StringBuilder();
                                for (int i = 0; i < response.getResult().get(0).getItems().size(); i++) {
                                    itemsBuilder.append("\n").append(response.getResult().get(0).getItems().get(i).getProductName()).append(" x ").append(response.getResult().get(0).getItems().get(i).getQuantity());
                                }
                                String items = itemsBuilder.toString();

                                //  if (items.isEmpty())

                                products.set(items);


                                total.set(String.valueOf(response.getResult().get(0).getPrice()));


                                orderReceivedStatus.set(response.getResult().get(0).getTrackingstatus().getMessage1());
                                orderPreparedStatus.set(response.getResult().get(0).getTrackingstatus().getMessage2());
                                orderDeliveryStatus.set(response.getResult().get(0).getTrackingstatus().getMessage3());


                       /* 0 -orderput   - est user
                        1- order accept - makeit user
                        2- order Prepare - makeit user
                      //  3- order packed - makeit user
                        4- kitchen reached - Move it
                        5- order pickedup - Move it
                        6- order delivered - Move it*/

                                if (response.getResult().get(0).getOrderstatus() == 0) {

                                    isReeceived.set(true);
                                    isPrepared.set(false);
                                    isDeliverd.set(false);

                                    iconReeceived.set(false);
                                    iconPrepared.set(false);
                                    iconDeliverd.set(false);


                                } else if (response.getResult().get(0).getOrderstatus() == 1) {
                                    isReeceived.set(false);
                                    isPrepared.set(true);
                                    isDeliverd.set(false);

                                    iconReeceived.set(true);
                                    iconPrepared.set(false);
                                    iconDeliverd.set(false);

                                    orderAccepted.set(true);

                                } else if (response.getResult().get(0).getOrderstatus() == 2) {
                                    isReeceived.set(false);
                                    isPrepared.set(false);
                                    isDeliverd.set(true);

                                    iconReeceived.set(true);
                                    iconPrepared.set(true);
                                    iconDeliverd.set(false);

                                    orderAccepted.set(true);
                                } else if (response.getResult().get(0).getOrderstatus() == 3) {
                                    isReeceived.set(false);
                                    isPrepared.set(false);
                                    isDeliverd.set(true);

                                    iconReeceived.set(true);
                                    iconPrepared.set(true);
                                    iconDeliverd.set(false);
                                    orderAccepted.set(true);

                                } else if (response.getResult().get(0).getOrderstatus() == 4) {
                                    isReeceived.set(false);
                                    isPrepared.set(false);
                                    isDeliverd.set(true);

                                    iconReeceived.set(true);
                                    iconPrepared.set(true);
                                    iconDeliverd.set(false);
                                    orderAccepted.set(true);

                                } else if (response.getResult().get(0).getOrderstatus() == 5) {
                                    isReeceived.set(false);
                                    isPrepared.set(false);
                                    isDeliverd.set(true);

                                    iconReeceived.set(true);
                                    iconPrepared.set(true);
                                    iconDeliverd.set(false);
                                    orderAccepted.set(true);

                                    if (response.getResult().get(0).getDeliveryVendor() == 0) {
                                        if (getNavigator() != null)
                                            getNavigator().orderPickedUp(response.getResult().get(0).getMoveitUserId());
                                    } else if (response.getResult().get(0).getDeliveryVendor() == 1) {
                                        if (response.getResult().get(0).getDunzoTaskid() != null) {
                                            getDunzoLatLng(response.getResult().get(0).getDunzoTaskid());
                                        }
                                    }

                                } else if (response.getResult().get(0).getOrderstatus() == 6) {
                                    isReeceived.set(false);
                                    isPrepared.set(false);
                                    isDeliverd.set(true);

                                    iconReeceived.set(true);
                                    iconPrepared.set(true);
                                    iconDeliverd.set(true);

                                    orderAccepted.set(false);

                                    isCanceled.set(false);
                                    deliveredORcancel.set("You order has been delivered");
                                    iconDeliveredORcancel.set(MvvmApp.getInstance().getString(R.string.icon_selected));

                                    delivered.set(true);

                                    if (countDownTimer != null)
                                        countDownTimer.cancel();


                                } else if (response.getResult().get(0).getOrderstatus() >= 7) {
                                    isReeceived.set(false);
                                    isPrepared.set(false);
                                    isDeliverd.set(true);

                                    iconReeceived.set(true);
                                    iconPrepared.set(true);
                                    iconDeliverd.set(true);

                                    deliveredORcancel.set("Sorry! Your order canceled.");
                                    iconDeliveredORcancel.set(MvvmApp.getInstance().getString(R.string.icon_error));

                                    isCanceled.set(true);
                                    orderAccepted.set(false);
                                    delivered.set(true);

                                    if (countDownTimer != null)
                                        countDownTimer.cancel();


                                }
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


    public void getMoveitlatlng(int moveitId) {
        DatabaseReference ref = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location");
        GeoFire geoFire = new GeoFire(ref);
        Query locationDataQuery = FirebaseDatabase.getInstance("https://moveit-a9128.firebaseio.com/").getReference("location").child(String.valueOf(moveitId));


        locationDataQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("l").getValue() != null) {

                    if (singleTime) {
                        List<Double> gg = (List<Double>) dataSnapshot.child("l").getValue();
                        getOrderETA(String.valueOf(gg.get(0)), String.valueOf(gg.get(1)));
                        singleTime = false;
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

    public void changeTrackingStatus() {


        dataLoaded.set(false);

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_ORDER_DETAILS_URL + getDataManager().getOrderId(), OrderTrackingResponse.class, new Response.Listener<OrderTrackingResponse>() {
                @Override
                public void onResponse(OrderTrackingResponse response) {

                    dataLoaded.set(true);

                    if (response != null) {


                        if (response.getResult() != null && response.getResult().size() > 0) {


                            orderTrackingResponse = response;

                            if (response.getResult().get(0).getDeliveryVendor() == 1) {
                                dunzoAuth = response.getResult().get(0).getAuthorization();
                                dunzoClientId = response.getResult().get(0).getDunzoClientId();

                            }


                            try {
                                String strDate = response.getResult().get(0).getDeliverytime();
                                DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

                                // DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
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


                            if (response.getResult().get(0).getDeliveryVendor()!=null&&response.getResult().get(0).getDeliveryVendor() == 0) {
                                dunzoOrder.set(false);
                                if (response.getResult().get(0).getOrderstatus() > 4) {
                                    singleTime = true;
                                    getMoveitlatlng(response.getResult().get(0).getMoveitUserId());
                                }
                            }else {
                                if (response.getResult().get(0).getOrderstatus() > 4) {
                                    if (response.getResult().get(0).getOrdertype()==0){
                                        dunzoOrder.set(true);
                                    }
                                }
                            }




                            if (response.getResult().get(0).getMoveitdetail()!=null&&response.getResult().get(0).getMoveitdetail().getName() != null) {
                                track.set(true);
                                moveitName.set(response.getResult().get(0).getMoveitdetail().getName());
                                moveitPhone.set(response.getResult().get(0).getMoveitdetail().getPhoneno());
                                deliveryManNumber = response.getResult().get(0).getMoveitdetail().getPhoneno();

                            }

                            orderReceivedStatus.set(response.getResult().get(0).getTrackingstatus().getMessage1());
                            orderPreparedStatus.set(response.getResult().get(0).getTrackingstatus().getMessage2());
                            orderDeliveryStatus.set(response.getResult().get(0).getTrackingstatus().getMessage3());

                            if (response.getResult().get(0).getOrderstatus() == 0) {

                                isReeceived.set(true);
                                isPrepared.set(false);
                                isDeliverd.set(false);

                                iconReeceived.set(false);
                                iconPrepared.set(false);
                                iconDeliverd.set(false);


                            } else if (response.getResult().get(0).getOrderstatus() == 1) {
                                isReeceived.set(false);
                                isPrepared.set(true);
                                isDeliverd.set(false);

                                iconReeceived.set(true);
                                iconPrepared.set(false);
                                iconDeliverd.set(false);

                                orderAccepted.set(true);

                            } else if (response.getResult().get(0).getOrderstatus() == 2) {
                                isReeceived.set(false);
                                isPrepared.set(false);
                                isDeliverd.set(true);

                                iconReeceived.set(true);
                                iconPrepared.set(true);
                                iconDeliverd.set(false);

                                orderAccepted.set(true);
                            } else if (response.getResult().get(0).getOrderstatus() == 3) {
                                isReeceived.set(false);
                                isPrepared.set(false);
                                isDeliverd.set(true);

                                iconReeceived.set(true);
                                iconPrepared.set(true);
                                iconDeliverd.set(false);
                                orderAccepted.set(true);

                            } else if (response.getResult().get(0).getOrderstatus() == 4) {
                                isReeceived.set(false);
                                isPrepared.set(false);
                                isDeliverd.set(true);

                                iconReeceived.set(true);
                                iconPrepared.set(true);
                                iconDeliverd.set(false);
                                orderAccepted.set(true);

                            } else if (response.getResult().get(0).getOrderstatus() == 5) {
                                isReeceived.set(false);
                                isPrepared.set(false);
                                isDeliverd.set(true);

                                iconReeceived.set(true);
                                iconPrepared.set(true);
                                iconDeliverd.set(false);
                                orderAccepted.set(true);


                                if (response.getResult().get(0).getDeliveryVendor() == 0) {
                                    if (getNavigator() != null)
                                        getNavigator().orderPickedUp(response.getResult().get(0).getMoveitUserId());
                                } else if (response.getResult().get(0).getDeliveryVendor() == 1) {
                                    if (response.getResult().get(0).getDunzoTaskid() != null)
                                        getDunzoLatLng(response.getResult().get(0).getDunzoTaskid());


                                }


                            } else if (response.getResult().get(0).getOrderstatus() == 6) {
                                isReeceived.set(false);
                                isPrepared.set(false);
                                isDeliverd.set(true);

                                iconReeceived.set(true);
                                iconPrepared.set(true);
                                iconDeliverd.set(true);


                                isCanceled.set(false);
                                deliveredORcancel.set("You order has been delivered");
                                iconDeliveredORcancel.set(MvvmApp.getInstance().getString(R.string.icon_selected));


                                orderAccepted.set(false);
                                delivered.set(true);

                                if (countDownTimer != null)
                                    countDownTimer.cancel();

                            } else if (response.getResult().get(0).getOrderstatus() >= 7) {
                                isReeceived.set(false);
                                isPrepared.set(false);
                                isDeliverd.set(true);

                                iconReeceived.set(true);
                                iconPrepared.set(true);
                                iconDeliverd.set(true);

                                deliveredORcancel.set("Sorry! Your order canceled.");
                                iconDeliveredORcancel.set(MvvmApp.getInstance().getString(R.string.icon_error));

                                isCanceled.set(true);
                                orderAccepted.set(false);
                                delivered.set(true);

                                if (countDownTimer != null)
                                    countDownTimer.cancel();


                            }
                        }
                    } else {
                        if (getNavigator() != null)
                            getNavigator().showToast(response.getMessage());

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


    public void dunzoTimer() {
        dunzoTrackingTimer.set(true);
        countDownTimer = new CountDownTimer(5000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {
                getDunzoLatLng(dunzoTaskid);
            }

            @Override
            public void onFinish() {
                try {
                    dunzoTimer();
                    if (getNavigator() == null)
                        countDownTimer.cancel();
                } catch (Exception e) {
                    Log.e("Error", "Error: ");
                }
            }
        }.start();


    }

    public void getDunzoLatLng(String taskid) {


        dunzoTaskid = taskid;


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                AppConstants.DUNZO_URL + taskid + AppConstants.DUNZO_STATUS, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if (response != null) {
                    Gson gson = new Gson();
                    DunzoResponse dunzoResponse = gson.fromJson(response.toString(), DunzoResponse.class);

                    if (dunzoResponse.getRunner() != null && dunzoResponse.getRunner().getLocation() != null && dunzoResponse.getRunner().getLocation().getLat() != null) {

                        if (getNavigator() != null)
                            getNavigator().DunzoTracking(dunzoResponse.getRunner().getLocation().getLat(), dunzoResponse.getRunner().getLocation().getLng());


                        if (!dunzoTrackingTimer.get()) {
                            dunzoTimer();
                        }


                    }


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", dunzoAuth);
                headers.put("client-id", dunzoClientId);
                headers.put("Accept-Language", "en_US");
                return headers;

            }
        };

        // Adding request to request queue
        MvvmApp.getInstance().addToRequestQueue(jsonObjReq);










      /*  try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.DUNZO_URL+taskid+"/status?test=true", DunzoResponse.class, new Response.Listener<DunzoResponse>() {
                @Override
                public void onResponse(DunzoResponse response) {

                    if (response != null) {
                        if (response.getRunner() != null && response.getRunner().getLocation() != null && response.getRunner().getLocation().getLat() != null) {
                            getNavigator().DunzoTracking(response.getRunner().getLocation().getLat(), response.getRunner().getLocation().getLng());
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

        }*/


    }



}
