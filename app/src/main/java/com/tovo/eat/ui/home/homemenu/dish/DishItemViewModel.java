package com.tovo.eat.ui.home.homemenu.dish;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DishItemViewModel {

    public final ObservableField<String> makeit_username = new ObservableField<>();
    public final ObservableField<String> makeitBrandName = new ObservableField<>();
    public final ObservableField<String> cusines = new ObservableField<>();
    public final ObservableField<String> locality = new ObservableField<>();

    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean noImage = new ObservableBoolean();

    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<String> productDes = new ObservableField<>();
    public final ObservableField<Integer> price = new ObservableField<>();

    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<Integer> quantity = new ObservableField<>();
    public final ObservableField<Integer> product_id = new ObservableField<>();
    public final ObservableField<Integer> makeit_userid = new ObservableField<>();

    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final DishItemViewModelListener mListener;
    private final DishResponse.Result dishList;
    public ObservableBoolean isVeg = new ObservableBoolean();
    List<CartRequestPojo.Cartitem> results = new ArrayList<>();
    CartRequestPojo cartRequestPojo = new CartRequestPojo();
    CartRequestPojo.Cartitem cartRequestPojoCartitem = new CartRequestPojo.Cartitem();
    private Integer favID;

    public DishItemViewModel(DishItemViewModelListener mListener, DishResponse.Result dishList) {

        this.mListener = mListener;
        this.dishList = dishList;
        //  this.date.set(mSalesList.getDate());

        if (dishList.getFavid() != null)
            favID = dishList.getFavid();


        if (dishList.getImage() == null) {
            noImage.set(true);
        }

        productDes.set(dishList.getProdDesc());

        if (dishList.getBrandname().isEmpty()) {
            this.makeitBrandName.set(dishList.getMakeitUsername());
        } else {
            this.makeitBrandName.set(dishList.getBrandname());
        }

        if (dishList.getIsfav().equals("1")) {
            isFavourite.set(true);
        } else {
            isFavourite.set(false);
        }

        if (dishList.getVegtype() != null)

            if (dishList.getVegtype().equals("0")) {

                isVeg.set(true);

            } else {

                isVeg.set(false);
            }


        this.locality.set(dishList.getLocalityname());

        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);

        if (cartRequestPojo == null) {
            this.makeit_username.set(dishList.getMakeitUsername());
            this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
            this.image.set(dishList.getImage());
            this.product_name.set(dishList.getProductName());
            this.product_id.set(dishList.getProductid());
            this.makeit_userid.set(dishList.getMakeitUserid());
            this.sprice.set(String.valueOf(dishList.getPrice()));
            this.price.set((dishList.getPrice()));
            this.cusines.set(dishList.getCusinename());
        } else {

            if (cartRequestPojo.getCartitems() != null) {
                results.clear();
                results.addAll(cartRequestPojo.getCartitems());


                int totalSize = results.size();
                if (totalSize != 0) {


                    for (int i = 0; i < results.size(); i++) {


                        if (cartRequestPojo.getMakeitUserid() != null) {

                            if (cartRequestPojo.getMakeitUserid().equals(dishList.getMakeitUserid())) {
                                if (results.get(i).getProductid().equals(dishList.getProductid())) {
                                    isAddClicked.set(true);
                                    this.makeit_username.set(dishList.getMakeitUsername());
                                    this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
                                    this.image.set(dishList.getImage());
                                    this.product_name.set(dishList.getProductName());
                                    this.product_id.set(dishList.getProductid());
                                    this.makeit_userid.set(dishList.getMakeitUserid());
                                    this.sprice.set(String.valueOf(dishList.getPrice()));
                                    this.price.set((dishList.getPrice()));
                                    quantity.set(results.get(i).getQuantity());
                                    sQuantity.set(String.valueOf(quantity.get()));
                                    this.cusines.set(dishList.getCusinename());

                                } else {
                                    this.makeit_username.set(dishList.getMakeitUsername());
                                    this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
                                    this.image.set(dishList.getImage());
                                    this.product_name.set(dishList.getProductName());
                                    this.product_id.set(dishList.getProductid());
                                    this.makeit_userid.set(dishList.getMakeitUserid());
                                    this.sprice.set(String.valueOf(dishList.getPrice()));
                                    this.price.set((dishList.getPrice()));
                                    this.cusines.set(dishList.getCusinename());
                                }
                            } else {
                                this.makeit_username.set(dishList.getMakeitUsername());
                                this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
                                this.image.set(dishList.getImage());
                                this.product_name.set(dishList.getProductName());
                                this.product_id.set(dishList.getProductid());
                                this.makeit_userid.set(dishList.getMakeitUserid());
                                this.sprice.set(String.valueOf(dishList.getPrice()));
                                this.price.set((dishList.getPrice()));
                                this.cusines.set(dishList.getCusinename());
                            }

                        } else {
                            this.makeit_username.set(dishList.getMakeitUsername());
                            this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
                            this.image.set(dishList.getImage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(dishList.getMakeitUserid());
                            this.sprice.set(String.valueOf(dishList.getPrice()));
                            this.price.set((dishList.getPrice()));
                            this.cusines.set(dishList.getCusinename());

                        }
                    }
                } else {
                    this.makeit_username.set(dishList.getMakeitUsername());
                    this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
                    this.image.set(dishList.getImage());
                    this.product_name.set(dishList.getProductName());
                    this.product_id.set(dishList.getProductid());
                    this.makeit_userid.set(dishList.getMakeitUserid());
                    this.sprice.set(String.valueOf(dishList.getPrice()));
                    this.price.set((dishList.getPrice()));
                    this.cusines.set(dishList.getCusinename());


                }
            } else {
                this.makeit_username.set(dishList.getMakeitUsername());
                this.producttype.set(dishList.getRegionname() + " | " + dishList.getCusinename());
                this.image.set(dishList.getImage());
                this.product_name.set(dishList.getProductName());
                this.product_id.set(dishList.getProductid());
                this.makeit_userid.set(dishList.getMakeitUserid());
                this.sprice.set(String.valueOf(dishList.getPrice()));
                this.price.set((dishList.getPrice()));
                this.cusines.set(dishList.getCusinename());
            }


        }

        if (quantity.get() != null) {
            if (quantity.get() == 0) {
                isAddClicked.set(false);

            }
        }
    }

    public void addClicked() throws NullPointerException {


        int checkQuantity = quantity.get();


        if (checkQuantity + 1 <= dishList.getQuantity()) {
            quantity.set(quantity.get() + 1);
            new Analytics(dishList.getProductid(),dishList.getProductName(),dishList.getPrice(),quantity.get(),String.valueOf(dishList.getMakeitUserid()));


            new Analytics().addtoCart(dishList.getProductid(),dishList.getProductName(),quantity.get(),dishList.getPrice());


        } else {
            Toast.makeText(MvvmApp.getInstance(), "Only " + dishList.getQuantity() + " Quantity of " + dishList.getProductName() + " Available", Toast.LENGTH_SHORT).show();

            return;
        }


        sQuantity.set(String.valueOf(quantity.get()));

        results.clear();

        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);

        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequestPojo();

        if (cartRequestPojo.getCartitems() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getCartitems());
        }

        if (cartRequestPojo.getMakeitUserid() != null) {

            if (cartRequestPojo.getCartitems() != null) {


                int totalSize = cartRequestPojo.getCartitems().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (dishList.getMakeitUserid().equals(cartRequestPojo.getMakeitUserid())) {

                            if (dishList.getProductid().equals(results.get(i).getProductid())) {

                                cartRequestPojoCartitem.setQuantity(quantity.get());
                                cartRequestPojoCartitem.setProductid(dishList.getProductid());
                                cartRequestPojoCartitem.setPrice(dishList.getPrice());
                                results.set(i, cartRequestPojoCartitem);
                            }
                        } else {


                        }
                    }

                }
            }


        }

        cartRequestPojo.setCartitems(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

        Log.e("cart", json);
      //  mListener.refresh();

    }


    public void subClicked() {

        mListener.subQuantity();

        quantity.set(quantity.get() - 1);

        sQuantity.set(String.valueOf(quantity.get()));

        results.clear();
      //  new Analytics(dishList.getProductid(),dishList.getProductName(),dishList.getPrice(),quantity.get(),String.valueOf(dishList.getMakeitUserid()));


        new Analytics().removeFromCart(dishList.getProductid(),dishList.getProductName(),quantity.get(),dishList.getPrice());

        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);

        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequestPojo();


        if (cartRequestPojo.getCartitems() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getCartitems());
        }

        if (cartRequestPojo.getMakeitUserid() != null) {

            if (cartRequestPojo.getCartitems() != null) {


                int totalSize = cartRequestPojo.getCartitems().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (dishList.getMakeitUserid().equals(cartRequestPojo.getMakeitUserid())) {

                            if (dishList.getProductid().equals(results.get(i).getProductid())) {
                                if (quantity.get() == 0) {
                                    results.remove(i);
                                    break;
                                } else {
                                    cartRequestPojoCartitem.setProductid(dishList.getProductid());
                                    cartRequestPojoCartitem.setQuantity(quantity.get());
                                    cartRequestPojoCartitem.setPrice(dishList.getPrice());
                                    results.set(i, cartRequestPojoCartitem);
                                }
                            }
                        }
                    }

                }
            }
        }

        if (results.size() == 0) {

            mListener.saveCart(null);

        } else {

            cartRequestPojo.setCartitems(results);
            Gson gson = new Gson();
            String json = gson.toJson(cartRequestPojo);
            mListener.saveCart(json);
        }


        if (quantity.get() == 0) {
            isAddClicked.set(false);

        }
     //   mListener.refresh();
    }

    public void enableAdd() {
        mListener.enableAdd();
        isAddClicked.set(true);
        quantity.set(1);
        sQuantity.set(String.valueOf(quantity.get()));
        new Analytics(dishList.getProductid(),dishList.getProductName(),dishList.getPrice(),quantity.get(),String.valueOf(dishList.getMakeitUserid()));
        new Analytics().addtoCart(dishList.getProductid(),dishList.getProductName(),quantity.get(),dishList.getPrice());
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);


        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequestPojo();

        if (cartRequestPojo.getCartitems() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getCartitems());
        }

        if (cartRequestPojo.getMakeitUserid() == null) {
            cartRequestPojo.setMakeitUserid(dishList.getMakeitUserid());

            cartRequestPojo.setCartitems(null);

            cartRequestPojoCartitem.setProductid(dishList.getProductid());
            cartRequestPojoCartitem.setQuantity(quantity.get());
            cartRequestPojoCartitem.setPrice(dishList.getPrice());
            results.clear();
            results.add(cartRequestPojoCartitem);

        } else {

            if (cartRequestPojo.getCartitems() != null) {


                if (dishList.getMakeitUserid().equals(cartRequestPojo.getMakeitUserid())) {
                    cartRequestPojoCartitem.setProductid(dishList.getProductid());
                    cartRequestPojoCartitem.setQuantity(quantity.get());
                    cartRequestPojoCartitem.setPrice(dishList.getPrice());
                    results.add(cartRequestPojoCartitem);

                } else {

                    mListener.otherKitchenDish(dishList.getMakeitUserid(), dishList.getProductid(), quantity.get(), dishList.getPrice());

                    return;
                    //  mListener.refresh();

                }

            } else {
                cartRequestPojoCartitem.setProductid(dishList.getProductid());
                cartRequestPojoCartitem.setQuantity(quantity.get());
                cartRequestPojoCartitem.setPrice(dishList.getPrice());
                results.add(cartRequestPojoCartitem);

            }
        }

        cartRequestPojo.setCartitems(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

       // mListener.refresh();

    }

    public void fav() {

        if (isFavourite.get()) {
            removeFavourite();
            new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_DISH,AppConstants.CLICK_REMOVE_FROM_FAV);
            isFavourite.set(false);
        } else {
            addFavourite(dishList.getProductid());
            new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_DISH,AppConstants.CLICK_ADD_TO_FAV);
            isFavourite.set(true);
        }


/*

        if (dishList.getIsfav().equalsIgnoreCase("0")) {
            mListener.addFavourites(dishList.getProductid(), dishList.getIsfav());
        } else if (dishList.getIsfav().equalsIgnoreCase("1")) {
            if (dishList.getFavid() != null)
                mListener.removeFavourites(dishList.getFavid());

        } else {
            isFavourite.set(false);
        }*/
    }


    public void removeFavourite() {
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL + favID, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mListener.favChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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
                    headers.put("Authorization", "Bearer " + appPreferencesHelper.getApiToken());
                    return headers;
                }
            };
            MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void addFavourite(Integer favid) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new DishFavRequest(String.valueOf(mListener.getEatId()), String.valueOf(favid)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        if (response.getFavid() != null) {
                            favID = response.getFavid();
                            mListener.favChanged();
                            mListener.showToast(response.getMessage());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void onItemClick() {
        mListener.onItemClick();

        //     mListener.addQuantity();

        //  mListener.onItemClick(isJobCompleted,Integer.parseInt(sales_emp_id.toString()) , Integer.parseInt(makeit_userid.toString()), date.toString(), name.toString(), email.toString(), phoneno.toString(), brandname.toString(),address.toString(),lat.toString(),lng.toString(),localityid.toString());

    }

    public interface DishItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick();

        String addQuantity();

        void subQuantity();

        void enableAdd();

        void saveCart(String jsonCartDetails);

        void checkAllCart();

        void refresh();

        void addFavourites(Integer dishId, String fav);

        void removeFavourites(Integer favId);


        void productNotAvailable();

        Integer getEatId();

        void showToast(String msg);


        void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price);


        void favChanged();

    }

}
