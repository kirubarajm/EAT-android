package com.tovo.eat.ui.cart;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.refund.RefundListResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartViewModel extends BaseViewModel<CartNavigator> {


    public final ObservableField<String> makeit_image = new ObservableField<>();

    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> grand_total = new ObservableField<>();
    public final ObservableField<String> refundFare = new ObservableField<>();
    public final ObservableField<String> couponFare = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> delivery_charge = new ObservableField<>();
    public final ObservableField<String> makeit_brand_name = new ObservableField<>();
    public final ObservableField<String> buttonText = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> current_address = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<String> localityname = new ObservableField<>();
    public final ObservableField<String> promocode = new ObservableField<>();
    public final ObservableField<String> grandTotalTitle = new ObservableField<>();
    public final ObservableField<Integer> refundBalance = new ObservableField<>();
    public final ObservableField<String> statusMessage = new ObservableField<>();
    public final ObservableField<String> cuisines = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> changeAddress = new ObservableField<>();
    public final ObservableBoolean payment = new ObservableBoolean();
    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean emptyCart = new ObservableBoolean();
    public final ObservableBoolean serviceable = new ObservableBoolean();
    public final ObservableBoolean available = new ObservableBoolean();
    public final ObservableBoolean refunds = new ObservableBoolean();
    public final ObservableBoolean refundSelected = new ObservableBoolean();
    public final ObservableBoolean couponSelected = new ObservableBoolean();
    public final ObservableBoolean refundChecked = new ObservableBoolean();


    public ObservableList<CartPageResponse.Item> cartDishItemViewModels = new ObservableArrayList<>();
    public ObservableList<RefundListResponse.Result> refundListItemViewModels = new ObservableArrayList<>();


    public MutableLiveData<List<CartPageResponse.Cartdetail>> cartBillLiveData;
    public ObservableList<CartPageResponse.Cartdetail> cartdetails = new ObservableArrayList<>();
    public int funnelStatus = 0;
    int favId;
    int makeitId;
    int totalAmount;
    String isFav;
    private MutableLiveData<List<RefundListResponse.Result>> refundListItemsLiveData;
    private String sPaymentMode = "";
    private MutableLiveData<List<CartPageResponse.Item>> dishItemsLiveData;


    public CartViewModel(DataManager dataManager) {
        super(dataManager);
        dishItemsLiveData = new MutableLiveData<>();
        refundListItemsLiveData = new MutableLiveData<>();
        cartBillLiveData = new MutableLiveData<>();
        serviceable.set(true);
        getDataManager().saveRefundId(0);
        getDataManager().saveCouponId(0);

        fetchRefunds();
    }


    public MutableLiveData<List<CartPageResponse.Cartdetail>> getCartBillLiveData() {
        return cartBillLiveData;
    }


    public ObservableList<RefundListResponse.Result> getRefundListItemViewModels() {
        return refundListItemViewModels;
    }


    public MutableLiveData<List<RefundListResponse.Result>> getRefundListItemsLiveData() {
        return refundListItemsLiveData;
    }


    public void promoCoupon() {

        getNavigator().promoList();


    }

    public void openKitchen() {

        getNavigator().gotoKitchen(makeitId);


    }

    public void promoCouponClose() {
        getDataManager().saveCouponId(0);
        couponSelected.set(false);
        fetchRepos();

    }

    public void refundView() {
        getNavigator().checkRefund();

    }

    public void redirectKitchen() {

        getNavigator().redirectHome();

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

    public void addRefundItemsToList(List<RefundListResponse.Result> results) {
        refundListItemViewModels.clear();
        refundListItemViewModels.addAll(results);

    }

    public void addBillItemsToList(List<CartPageResponse.Cartdetail> results) {
        cartdetails.clear();
        cartdetails.addAll(results);

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


    public void selectAddress() {
        getNavigator().selectAddress();
    }


    public void setAddressTitle() {

        address.set(getDataManager().getCurrentAddressTitle());
        current_address.set(getDataManager().getCurrentAddress());

        if (getDataManager().getAddressId() == 0) {
            changeAddress.set("Add Address");
        } else {

            changeAddress.set("Change");
        }

    }


    public ObservableList<CartPageResponse.Item> getCartDishItemViewModels() {
        return cartDishItemViewModels;
    }


    public MutableLiveData<List<CartPageResponse.Item>> getDishItemsLiveData() {
        return dishItemsLiveData;
    }

    public void addDishItemsToList(List<CartPageResponse.Item> ordersItems) {
        cartDishItemViewModels.clear();
        cartDishItemViewModels.addAll(ordersItems);
    }


    public void saveToCartPojo(String cartJsonString) {


        getDataManager().setCartDetails(cartJsonString);

        if (getDataManager().getCartDetails() == null) {
            getNavigator().emptyCart();
            emptyCart.set(true);
        }


    }


    public String getCartPojoDetails() {


        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

        if (cartRequestPojo == null) {
            emptyCart.set(true);
            return null;

        } else {
            if (cartRequestPojo.getCartitems().size() == 0) {
                getDataManager().setCartDetails(null);
                emptyCart.set(true);
            } else {
                emptyCart.set(false);
            }
        }

        return getDataManager().getCartDetails();
    }


    public void fetchRefunds() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_REFUND_LIST_URL + getDataManager().getCurrentUserId(), RefundListResponse.class, new Response.Listener<RefundListResponse>() {
                @Override
                public void onResponse(RefundListResponse response) {
                    if (response != null) {

                        if (response.getResult().size() == 0) {

                            refunds.set(false);

                        } else {
                            refunds.set(true);
                            refundListItemsLiveData.setValue(response.getResult());


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


    public void saveRefundandCalculate(int rcid) {

        getDataManager().saveRefundId(rcid);
        fetchRepos();


    }


    public void fetchRepos() {


        promocode.set(getDataManager().getCouponCode());

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        if (getCartPojoDetails() != null) {


            Gson sGson = new GsonBuilder().create();
            CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

            cartRequestPojo.setUserid(getDataManager().getCurrentUserId());

            cartRequestPojo.setLat(getDataManager().getCurrentLat());
            cartRequestPojo.setLon(getDataManager().getCurrentLng());


            makeitId = cartRequestPojo.getMakeitUserid();


            if (getDataManager().getRefundId() != 0) {
                cartRequestPojo.setRcid(getDataManager().getRefundId());
            }

            if (getDataManager().getCouponId() != 0) {
                cartRequestPojo.setCid(getDataManager().getCouponId());
            }


            Gson gson = new Gson();
            String carts = gson.toJson(cartRequestPojo);

            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CART_DETAILS_URL, new JSONObject(carts), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response != null) {

                            Gson gson = new Gson();
                            CartPageResponse cartPageResponse = gson.fromJson(response.toString(), CartPageResponse.class);

                            //    if (cartPageResponse.getStatus()) {

                            statusMessage.set(cartPageResponse.getMessage());
                            available.set(cartPageResponse.getStatus());

                            if (!cartPageResponse.getStatus()) {

                                Toast.makeText(MvvmApp.getInstance(), cartPageResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            if (cartPageResponse.getResult() != null) {

                                if (cartPageResponse.getResult().get(0).getItem().size() == 0) {

                                    getNavigator().emptyCart();

                                    getDataManager().setCartDetails(null);

                                    emptyCart.set(true);


                                } else {
                                    dishItemsLiveData.setValue(cartPageResponse.getResult().get(0).getItem());

                                    if (cartPageResponse.getResult().get(0).getFunnelStatus() != null)
                                        funnelStatus = cartPageResponse.getResult().get(0).getFunnelStatus();


                                    emptyCart.set(false);

                                    if (cartPageResponse.getResult().get(0).getMakeitbrandname().isEmpty()) {

                                        makeit_brand_name.set(cartPageResponse.getResult().get(0).getMakeitusername());
                                    } else {

                                        makeit_brand_name.set(cartPageResponse.getResult().get(0).getMakeitbrandname());

                                    }

                                    cartBillLiveData.setValue(cartPageResponse.getResult().get(0).getCartDetails());


                                    getDataManager().totalOrders((cartPageResponse.getResult().get(0).getOrdercount()));


                                    makeit_image.set(cartPageResponse.getResult().get(0).getMakeitimg());
                                    //  makeit_category.set(response.getResult().get(0).getCategory());


                                    if (cartPageResponse.getResult().get(0).getIsFav().equals("1")) {
                                        isFavourite.set(true);
                                    } else {
                                        isFavourite.set(false);
                                    }


                                    totalAmount = cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotal();


                                    total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getProductOrginalPrice()));

                                    grand_total.set(String.valueOf(totalAmount));

                                    grandTotalTitle.set(cartPageResponse.getResult().get(0).getAmountdetails().getGrandTotalTitle());
                                    refundBalance.set(cartPageResponse.getResult().get(0).getAmountdetails().getRefundBalance());


                                    gst.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGstcharge()));
                                    delivery_charge.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge()));


                                    localityname.set(cartPageResponse.getResult().get(0).getLocalityname());


                                    makeitId = cartPageResponse.getResult().get(0).getMakeituserid();


                                    if (cartPageResponse.getResult().get(0).getFavid() != null)
                                        favId = cartPageResponse.getResult().get(0).getFavid();


                                    if (cartPageResponse.getResult().get(0).getAmountdetails().getRefundcouponstatus()) {
                                        refundSelected.set(true);
                                        refundFare.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getRefundCouponAdjustment()));

                                    } else {
                                        refundSelected.set(false);
                                    }
                                    if (null != cartPageResponse.getResult().get(0).getAmountdetails().getCouponstatus() && cartPageResponse.getResult().get(0).getAmountdetails().getCouponstatus()) {
                                        couponSelected.set(true);
                                        couponFare.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getCouponDiscountAmount()));

                                    } else {
                                        couponSelected.set(false);

                                        if (getDataManager().getCouponId() != 0) {
                                            getDataManager().saveCouponId(0);
                                            getDataManager().saveCouponCode(null);
                                            fetchRepos();
                                        }

                                    }


                                    cuisines.set("by " + cartPageResponse.getResult().get(0).getMakeitusername() + ", from ");
                                    region.set(cartPageResponse.getResult().get(0).getRegionname());

                                }


                                setAddressTitle();

                                serviceable.set(cartPageResponse.getResult().get(0).isAvaliablekitchen());

                                try {
                                    if (getNavigator() != null)
                                        getNavigator().cartLoaded();
                                } catch (Exception re) {
                                    re.printStackTrace();
                                }
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emptyCart.set(true);
                        if (getNavigator() != null)
                            getNavigator().cartLoaded();
                    }
                }) {
                    /**
                     * Passing some request headers
                     */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                    }
                };

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);
            } catch (JSONException j) {
                if (getNavigator() != null)
                    getNavigator().cartLoaded();
                emptyCart.set(true);
                j.printStackTrace();
            } catch (NullPointerException n) {
                if (getNavigator() != null)
                    getNavigator().cartLoaded();
                emptyCart.set(true);
                n.printStackTrace();
            } catch (Exception ee) {
                if (getNavigator() != null)
                    getNavigator().cartLoaded();
                emptyCart.set(true);
                ee.printStackTrace();
            }

        } else {
            emptyCart.set(true);
        }
    }

    public void paymentModeCheck() {


        new Analytics().sendClickData(AppConstants.SCREEN_CART_PAGE, AppConstants.CLICK_PROCEED_TO_PAY);


        if (funnelStatus == 0) {


            if (available.get()) {

                if (serviceable.get()) {


                    if (getDataManager().getAddressId() == 0) {

                        getNavigator().showToast("Please complete the address");

                    } else {
                        if (getDataManager().getTotalOrders() == 0) {

                            if (totalAmount == 0) {
                                if (getDataManager().getRefundId() != 0) {

                                    if (refundBalance.get() > 0) {
                                        getNavigator().refundAlert();
                                    } else {
                                        getNavigator().paymentGateway(grand_total.get());
                                    }

                                } else {
                                    cashMode();
                                }
                            } else {
                                if (getDataManager().getRefundId() != 0) {

                                    if (refundBalance.get() > 0) {
                                        getNavigator().refundAlert();
                                    } else {
                                        getNavigator().paymentGateway(grand_total.get());
                                    }
                                } else {
                                    getNavigator().paymentGateway(grand_total.get());
                                }

                            }
                        } else {

                            if (getDataManager().getEmailStatus()) {
                                if (totalAmount == 0) {
                                    if (getDataManager().getRefundId() != 0) {

                                        if (refundBalance.get() > 0) {
                                            getNavigator().refundAlert();
                                        } else {
                                            getNavigator().paymentGateway(grand_total.get());
                                        }
                                    } else {
                                        cashMode();
                                    }
                                } else {
                                    if (getDataManager().getRefundId() != 0) {

                                        if (refundBalance.get() > 0) {
                                            getNavigator().refundAlert();
                                        } else {
                                            getNavigator().paymentGateway(grand_total.get());
                                        }
                                    } else {
                                        getNavigator().paymentGateway(grand_total.get());
                                    }
                                }
                            } else {
                                getNavigator().postRegistration("cart", grand_total.get());

                            }

                        }
                    }

                } else {
                    getNavigator().notServicable();
                }
            } else {
                Toast.makeText(MvvmApp.getInstance(), statusMessage.get(), Toast.LENGTH_SHORT).show();


                if (!serviceable.get()) {
                    getNavigator().notServicable();
                }


            }
        } else {

            if (getDataManager().getAddressId() == 0) {

                getNavigator().showToast("Please complete the address");

            } else {

                funnelMode();

                /*if (getDataManager().getTotalOrders() == 0) {

                    if (totalAmount == 0) {
                        if (getDataManager().getRefundId() != 0) {

                            if (refundBalance.get() > 0) {
                                getNavigator().refundAlert();
                            } else {
                                getNavigator().paymentGateway(grand_total.get());
                            }

                        } else {
                            cashMode();
                        }
                    } else {
                        if (getDataManager().getRefundId() != 0) {

                            if (refundBalance.get() > 0) {
                                getNavigator().refundAlert();
                            } else {
                                getNavigator().paymentGateway(grand_total.get());
                            }
                        } else {
                            getNavigator().paymentGateway(grand_total.get());
                        }

                    }
                } else {

                    if (getDataManager().getEmailStatus()) {
                        if (totalAmount == 0) {
                            if (getDataManager().getRefundId() != 0) {

                                if (refundBalance.get() > 0) {
                                    getNavigator().refundAlert();
                                } else {
                                    getNavigator().paymentGateway(grand_total.get());
                                }
                            } else {
                                cashMode();
                            }
                        } else {
                            if (getDataManager().getRefundId() != 0) {

                                if (refundBalance.get() > 0) {
                                    getNavigator().refundAlert();
                                } else {
                                    getNavigator().paymentGateway(grand_total.get());
                                }
                            } else {
                                getNavigator().paymentGateway(grand_total.get());
                            }
                        }
                    } else {
                        getNavigator().postRegistration("cart", grand_total.get());

                    }

                }*/
            }

        }
    }


    public void paymentRadioGroup(RadioGroup radioGroup, int buttonId) {

        RadioButton rb = radioGroup.findViewById(buttonId);
        if (null != rb) {
            sPaymentMode = rb.getText().toString();
            buttonText.set(sPaymentMode);
            getNavigator().paymentMode(sPaymentMode);


        }

    }


    public void cashMode() {
        if (getDataManager().getEmailStatus()) {
            try {

                if (getDataManager().getAddressId() != 0) {


                    if (!MvvmApp.getInstance().onCheckNetWork()) return;

                    List<CartRequestPojo.Cartitem> cartitems = new ArrayList<>();
                    List<PlaceOrderRequestPojo.Orderitem> orderitems = new ArrayList<>();

                    PlaceOrderRequestPojo placeOrderRequestPojo = new PlaceOrderRequestPojo();

                    PlaceOrderRequestPojo.Orderitem orderitem;


                    Gson sGson = new GsonBuilder().create();
                    CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

                    cartitems.addAll(cartRequestPojo.getCartitems());


                    for (int i = 0; i < cartitems.size(); i++) {

                        orderitem = new PlaceOrderRequestPojo.Orderitem();
                        orderitem.setProductid(cartitems.get(i).getProductid());
                        orderitem.setQuantity(cartitems.get(i).getQuantity());
                        orderitems.add(orderitem);

                    }


                    placeOrderRequestPojo.setOrderitems(orderitems);

                    placeOrderRequestPojo.setMakeitUserId(cartRequestPojo.getMakeitUserid());

                    PlaceOrderRequestPojo placeOrderRequestPojo1 = new PlaceOrderRequestPojo(getDataManager().getCurrentUserId(), cartRequestPojo.getMakeitUserid(), 0, getDataManager().getAddressId(), getDataManager().getRefundId(), getDataManager().getCouponId(), orderitems);


                    Gson gson = new Gson();
                    String json = gson.toJson(placeOrderRequestPojo1);

                    Log.e("sfsdfd", json);

                    setIsLoading(true);


                    JsonObjectRequest jsonObjectRequest = null;
                    try {
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CREATE_ORDER_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {


                                    if (response.getBoolean("status")) {

                                        getDataManager().currentOrderId(response.getInt("orderid"));
                                        getDataManager().setCartDetails(null);
                                        getNavigator().orderCompleted();
                                        getDataManager().saveRefundId(0);

                                    } else {

                                        getNavigator().showToast(response.getString("message"));

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {


                                //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                            }
                        }) {
                            /**
                             * Passing some request headers
                             */
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                            }
                        };
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    assert jsonObjectRequest != null;
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

                } else {
                    //  getNavigator().showToast("Please select the address...");
                }
            } catch (Exception ee) {

                ee.printStackTrace();

            }
        }

    }


    public void funnelMode() {

        try {

            if (getDataManager().getAddressId() != 0) {

                if (!MvvmApp.getInstance().onCheckNetWork()) return;

                List<CartRequestPojo.Cartitem> cartitems = new ArrayList<>();
                List<PlaceOrderRequestPojo.Orderitem> orderitems = new ArrayList<>();

                PlaceOrderRequestPojo placeOrderRequestPojo = new PlaceOrderRequestPojo();

                PlaceOrderRequestPojo.Orderitem orderitem;


                Gson sGson = new GsonBuilder().create();
                CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

                cartitems.addAll(cartRequestPojo.getCartitems());


                for (int i = 0; i < cartitems.size(); i++) {

                    orderitem = new PlaceOrderRequestPojo.Orderitem();
                    orderitem.setProductid(cartitems.get(i).getProductid());
                    orderitem.setQuantity(cartitems.get(i).getQuantity());
                    orderitems.add(orderitem);

                }


                placeOrderRequestPojo.setOrderitems(orderitems);

                placeOrderRequestPojo.setMakeitUserId(cartRequestPojo.getMakeitUserid());

                PlaceOrderRequestPojo placeOrderRequestPojo1 = new PlaceOrderRequestPojo(getDataManager().getCurrentUserId(), cartRequestPojo.getMakeitUserid(), 0, getDataManager().getAddressId(), getDataManager().getRefundId(), getDataManager().getCouponId(), orderitems);


                Gson gson = new Gson();
                String json = gson.toJson(placeOrderRequestPojo1);


                setIsLoading(true);


                JsonObjectRequest jsonObjectRequest = null;
                try {
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_FUNNEL_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                if (response.getBoolean("status")) {

                                       /* getDataManager().currentOrderId(response.getInt("orderid"));
                                        getDataManager().setCartDetails(null);
                                        getNavigator().orderCompleted();
                                        getDataManager().saveRefundId(0);*/
                                    getDataManager().setFunnelStatus(true);
                                    getDataManager().setCartDetails(null);
                                    getNavigator().funnelAlert();


                                } else {

                                    getNavigator().showToast(response.getString("message"));

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                        }
                    }) {
                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                        }
                    };
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                assert jsonObjectRequest != null;
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

            } else {
                //  getNavigator().showToast("Please select the address...");
            }
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }

}
