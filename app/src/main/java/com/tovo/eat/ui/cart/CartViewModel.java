package com.tovo.eat.ui.cart;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartViewModel extends BaseViewModel<CartNavigator> {


    public final ObservableField<String> makeit_username = new ObservableField<>();
    public final ObservableField<String> makeit_image = new ObservableField<>();
    public final ObservableField<String> makeit_category = new ObservableField<>();
    public final ObservableField<String> total = new ObservableField<>();
    public final ObservableField<String> grand_total = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> delivery_charge = new ObservableField<>();
    public final ObservableField<String> makeit_brand_name = new ObservableField<>();
    public final ObservableField<String> buttonText = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();


    public final ObservableBoolean payment = new ObservableBoolean();
    public ObservableList<CartPageResponse.Item> cartDishItemViewModels = new ObservableArrayList<>();
    private String sPaymentMode = "";
    private MutableLiveData<List<CartPageResponse.Item>> dishItemsLiveData;


    public CartViewModel(DataManager dataManager) {
        super(dataManager);
        dishItemsLiveData = new MutableLiveData<>();

        fetchRepos();

    }


    public void selectAddress() {
        getNavigator().selectAddress();
    }


    public void setAddressTitle() {

        address.set(getDataManager().getCurrentAddressTitle());

    }


    public ObservableList<CartPageResponse.Item> getCartDishItemViewModels() {
        return cartDishItemViewModels;
    }

    public void setCartDishItemViewModels(ObservableList<CartPageResponse.Item> cartDishItemViewModels) {
        this.cartDishItemViewModels = cartDishItemViewModels;
    }

    public MutableLiveData<List<CartPageResponse.Item>> getDishItemsLiveData() {
        return dishItemsLiveData;
    }

    public void setDishItemsLiveData(MutableLiveData<List<CartPageResponse.Item>> dishItemsLiveData) {
        this.dishItemsLiveData = dishItemsLiveData;
    }

    public void addDishItemsToList(List<CartPageResponse.Item> ordersItems) {
        cartDishItemViewModels.clear();
        cartDishItemViewModels.addAll(ordersItems);

    }


    public void saveToCartPojo(String cartJsonString) {


        getDataManager().setCartDetails(cartJsonString);
    }


    public String getCartPojoDetails() {

        return getDataManager().getCartDetails();
    }

    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CART_DETAILS_URL, new JSONObject(getDataManager().getCartDetails()), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    Gson gson = new Gson();
                    CartPageResponse cartPageResponse = gson.fromJson(response.toString(), CartPageResponse.class);

                    dishItemsLiveData.setValue(cartPageResponse.getResult().get(0).getItem());


                    if (cartPageResponse.getResult().get(0).getMakeitbrandname() == null) {

                        makeit_brand_name.set(cartPageResponse.getResult().get(0).getMakeitusername());
                    } else {

                        makeit_brand_name.set(cartPageResponse.getResult().get(0).getMakeitbrandname());

                    }

                    makeit_image.set(cartPageResponse.getResult().get(0).getMakeitimg());
                    //  makeit_category.set(response.getResult().get(0).getCategory());

                    total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getTotalamount()));
                    grand_total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotal()));
                    gst.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGstcharge()));
                    delivery_charge.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge()));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };
            MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (JSONException j) {

            j.printStackTrace();
        }


    }

    public void paymentModeCheck() {

        if (getNavigator().paymentStatus(sPaymentMode)) {

            cashMode();

        } else {

            onlineMode();

        }

    }


    public void cashMode() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );


        List<CartRequestPojo.Cartitem> cartitems = new ArrayList<>();
        List<PlaceOrderRequestPojo.Orderitem> orderitems = new ArrayList<>();

        PlaceOrderRequestPojo placeOrderRequestPojo=new PlaceOrderRequestPojo();

        PlaceOrderRequestPojo.Orderitem orderitem=new PlaceOrderRequestPojo.Orderitem();


        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

        cartitems.addAll(cartRequestPojo.getCartitems());

        for (int i = 0; i < cartitems.size(); i++) {

            orderitem.setProductid(cartitems.get(i).getProductid());
            orderitem.setQuantity(cartitems.get(i).getQuantity());
            orderitems.add(orderitem);
        }


        placeOrderRequestPojo.setOrderitems(orderitems);

        placeOrderRequestPojo.setMakeitUserId(cartRequestPojo.getMakeitUserid());

        PlaceOrderRequestPojo placeOrderRequestPojo1=   new PlaceOrderRequestPojo(1,1,0,cartRequestPojo.getMakeitUserid(),0,1,orderitems );


        Gson gson = new Gson();
        String json = gson.toJson(placeOrderRequestPojo1);

        Log.e("sfsdfd",json);

        setIsLoading(true);


        JsonObjectRequest jsonObjectRequest= null;
        try {
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CREATE_ORDER_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        getDataManager().currentOrderId(response.getInt("orderid"));


                        getDataManager().setCartDetails("");


                        getNavigator().orderCompleted();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");

                    return headers;
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);


    }


    public void onlineMode() {


    }


    public void paymentRadioGroup(RadioGroup radioGroup, int buttonId) {


        RadioButton rb = radioGroup.findViewById(buttonId);
        if (null != rb) {
            //  Toast.makeText(DirectionTestTestActivity.this,String.valueOf(rb.getT) , Toast.LENGTH_SHORT).show();
            sPaymentMode = rb.getText().toString();
            buttonText.set(sPaymentMode);
            getNavigator().paymentMode(sPaymentMode);


        }

    }

}
