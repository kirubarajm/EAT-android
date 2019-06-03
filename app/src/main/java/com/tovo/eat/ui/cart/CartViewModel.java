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
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
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
    public final ObservableField<String> current_address = new ObservableField<>();
    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<String> localityname = new ObservableField<>();

    public final ObservableField<String> cuisines = new ObservableField<>();


    public final ObservableBoolean payment = new ObservableBoolean();
    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean emptyCart = new ObservableBoolean();


    public ObservableList<CartPageResponse.Item> cartDishItemViewModels = new ObservableArrayList<>();
    int favId;
    int makeitId;
    String isFav;
    private String sPaymentMode = "";
    private MutableLiveData<List<CartPageResponse.Item>> dishItemsLiveData;


    public CartViewModel(DataManager dataManager) {
        super(dataManager);
        dishItemsLiveData = new MutableLiveData<>();


        if (getCartPojoDetails() != null)
            fetchRepos();

        //  getDataManager().setisPasswordStatus(false);

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
            });

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

                        favId = response.getFavid();


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

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


        if (getDataManager().getCartDetails() == null) {
            getNavigator().emptyCart();
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
            }else {
                emptyCart.set(false);
            }
        }

        return getDataManager().getCartDetails();
    }

    public void fetchRepos() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;


        //  String ss = getDataManager().getCartDetails();

        if (getCartPojoDetails() != null) {


            Gson sGson = new GsonBuilder().create();
            CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

            cartRequestPojo.setUserid(getDataManager().getCurrentUserId());

            Gson gson = new Gson();
            String carts = gson.toJson(cartRequestPojo);

            try {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CART_DETAILS_URL, new JSONObject(carts), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Gson gson = new Gson();
                        CartPageResponse cartPageResponse = gson.fromJson(response.toString(), CartPageResponse.class);

                        //    if (cartPageResponse.getStatus()) {

                        if (cartPageResponse.getResult().get(0).getItem().size() == 0) {

                            getNavigator().emptyCart();

                            getDataManager().setCartDetails(null);

                            emptyCart.set(true);


                        } else {
                            dishItemsLiveData.setValue(cartPageResponse.getResult().get(0).getItem());

                            emptyCart.set(false);

                            if (cartPageResponse.getResult().get(0).getMakeitbrandname().isEmpty()) {

                                makeit_brand_name.set(cartPageResponse.getResult().get(0).getMakeitusername());
                            } else {

                                makeit_brand_name.set(cartPageResponse.getResult().get(0).getMakeitbrandname());

                            }


                            getDataManager().totalOrders(Integer.valueOf(cartPageResponse.getResult().get(0).getOrdercount()));


                            makeit_image.set(cartPageResponse.getResult().get(0).getMakeitimg());
                            //  makeit_category.set(response.getResult().get(0).getCategory());

                            total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getTotalamount()));
                            grand_total.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGrandtotal()));
                            gst.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getGstcharge()));
                            delivery_charge.set(String.valueOf(cartPageResponse.getResult().get(0).getAmountdetails().getDeliveryCharge()));


                            localityname.set(cartPageResponse.getResult().get(0).getLocalityname());


                            makeitId = cartPageResponse.getResult().get(0).getMakeituserid();


                            if (cartPageResponse.getResult().get(0).getFavid() != null)
                                favId = cartPageResponse.getResult().get(0).getFavid();


                            StringBuilder itemsBuilder = new StringBuilder();
                            for (int i = 0; i < cartPageResponse.getResult().get(0).getCuisines().size(); i++) {

                                itemsBuilder.append(cartPageResponse.getResult().get(0).getCuisines().get(i).getCuisinename());

                                if (cartPageResponse.getResult().get(0).getCuisines().size() - 1 == i) {

                                } else {

                                    itemsBuilder.append(" | ");

                                }

                            }
                            String items = itemsBuilder.toString();
                            cuisines.set(items);


                        }


                       /* } else {

                            getNavigator().showToast(cartPageResponse.getMessage());

                        }*/


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        emptyCart.set(true);

                    }
                }) {

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
                emptyCart.set(true);
                j.printStackTrace();
            } catch (NullPointerException n) {
                emptyCart.set(true);
                n.printStackTrace();
            } catch (Exception ee) {
                emptyCart.set(true);
                ee.printStackTrace();

            }

        }
    }

    public void paymentModeCheck() {

        if (getDataManager().getAddressId() == 0) {

            getNavigator().selectAddress();

        } else {


            if (getDataManager().getTotalOrders() == 0) {
                getNavigator().paymentGateway(grand_total.get());

            } else {

                if (getDataManager().getisPasswordStatus()) {

                    getNavigator().paymentGateway(grand_total.get());

                } else {
                    getNavigator().postRegistration();
                }


            }
        }


/*
        if (getDataManager().getisPasswordStatus()) {

            if (getNavigator().paymentStatus(sPaymentMode)) {

                cashMode();


            } else {

                onlineMode();

            }
        } else {

            getNavigator().postRegistration();

        }*/


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
