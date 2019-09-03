package com.tovo.eat.ui.kitchendetails;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.ui.home.homemenu.dish.DishFavRequest;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.ArrayList;
import java.util.List;


public class FavTodaysMenuItemViewModel {

    public final ObservableField<String> makeit_username = new ObservableField<>();
    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();
    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<Integer> price = new ObservableField<>();

    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<Integer> quantity = new ObservableField<>();
    public final ObservableField<Integer> product_id = new ObservableField<>();
    public final ObservableField<Integer> makeit_userid = new ObservableField<>();


    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean isVeg = new ObservableBoolean();
    public final ObservableBoolean isFavouriteMenu = new ObservableBoolean();
    public final ObservableBoolean serviceableKitchen = new ObservableBoolean();

    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final DishItemViewModelListener mListener;
    private final KitchenDishResponse.Productlist dishList;

    List<CartRequestPojo.Cartitem> results = new ArrayList<>();
    CartRequestPojo cartRequestPojo = new CartRequestPojo();
    CartRequestPojo.Cartitem cartRequestPojoCartitem = new CartRequestPojo.Cartitem();




    Integer favID;


    public FavTodaysMenuItemViewModel(DishItemViewModelListener mListener, KitchenDishResponse.Productlist dishList,boolean sericeable) {

        this.mListener = mListener;
        this.dishList = dishList;

        this.serviceableKitchen.set(sericeable);

        //  this.date.set(mSalesList.getDate());
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);

        if(dishList.getVegtype().equals("1")){
            this.producttype.set(dishList.getCuisinename());
            this.isVeg.set(false);


        }else {
            this.producttype.set(dishList.getCuisinename());
            this.isVeg.set(true);
        }


        if (dishList.getProductimage()!=null){
            isFavouriteMenu.set(true);
        }



        if (cartRequestPojo == null) {

            this.image.set(dishList.getProductimage());
            this.product_name.set(dishList.getProductName());
            this.product_id.set(dishList.getProductid());
            this.makeit_userid.set(dishList.getMakeitUserid());
            this.sprice.set(String.valueOf(dishList.getPrice()));
            this.price.set((dishList.getPrice()));

        } else {

            if (cartRequestPojo.getCartitems() != null) {
                results.clear();
                results.addAll(cartRequestPojo.getCartitems());

                for (int i = 0; i < results.size(); i++) {
                    if (cartRequestPojo.getMakeitUserid().equals(dishList.getMakeitUserid())) {
                        if (results.get(i).getProductid().equals(dishList.getProductid())) {
                            isAddClicked.set(true);
                            this.image.set(dishList.getProductimage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(dishList.getMakeitUserid());
                            this.sprice.set(String.valueOf(dishList.getPrice()));
                            this.price.set((dishList.getPrice()));
                            quantity.set(results.get(i).getQuantity());
                            sQuantity.set(String.valueOf(quantity.get()));

                        } else {
                            this.image.set(dishList.getProductimage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(dishList.getMakeitUserid());
                            this.sprice.set(String.valueOf(dishList.getPrice()));
                            this.price.set((dishList.getPrice()));
                        }
                    } else {
                        this.image.set(dishList.getProductimage());
                        this.product_name.set(dishList.getProductName());
                        this.product_id.set(dishList.getProductid());
                        this.makeit_userid.set(dishList.getMakeitUserid());
                        this.sprice.set(String.valueOf(dishList.getPrice()));
                        this.price.set((dishList.getPrice()));
                    }

                }
            } else {

                this.image.set(dishList.getProductimage());
                this.product_name.set(dishList.getProductName());
                this.product_id.set(dishList.getProductid());
                this.makeit_userid.set(dishList.getMakeitUserid());
                this.sprice.set(String.valueOf(dishList.getPrice()));
                this.price.set((dishList.getPrice()));
            }
        }

        if (quantity.get() != null)
            if (quantity.get() == 0) {
                isAddClicked.set(false);
            }

        if (dishList.getIsfav().equals("0")) {
            this.isFavourite.set(false);
        } else {
            this.isFavourite.set(true);
        }


    }

    public void addClicked() throws NullPointerException {


        int checkQuantity = quantity.get();


        if (checkQuantity + 1 <= dishList.getQuantity()) {
            quantity.set(quantity.get() + 1);

        } else {
            mListener.productNotAvailable(dishList.getQuantity(),dishList.getProductName());
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
        mListener.refresh();

    }


    public void subClicked() {

        mListener.subQuantity();

        quantity.set(quantity.get() - 1);

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


        if (quantity.get() == 0) {
            isAddClicked.set(false);


        }


        if (results.size() == 0) {

            mListener.saveCart(null);

        } else {

            cartRequestPojo.setCartitems(results);
            Gson gson = new Gson();
            String json = gson.toJson(cartRequestPojo);
            mListener.saveCart(json);
        }


        mListener.refresh();
    }

    public void enableAdd() {
        mListener.enableAdd();
        isAddClicked.set(true);
        quantity.set(1);
        sQuantity.set(String.valueOf(quantity.get()));


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
            cartRequestPojoCartitem.setPrice(dishList.getPrice());
            cartRequestPojoCartitem.setQuantity(quantity.get());
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

        mListener.refresh();

        Log.e("cart", json);


    }


    public void fav() {
        mListener.onItemClick(dishList);

        if (isFavourite.get()) {
            removeFavourite();
            isFavourite.set(false);
        } else {
            addFavourite(dishList.getProductid());
            isFavourite.set(true);
        }

       /*
        if (dishList.getIsfav().equalsIgnoreCase("0")) {
            mListener.addFavourites(dishList.getProductid(), dishList.getIsfav());
            isFavourite.set(true);
        } else if (dishList.getIsfav().equalsIgnoreCase("1")) {
            if (dishList.getFavid() != null)
                mListener.removeFavourites(dishList.getFavid());
            isFavourite.set(false);
        } else {
            isFavourite.set(false);
        }*/
        //   mListener.addFavourites(originalResult.getMakeituserid(), dishList.getProductid(), dishList.getIsfav());
    }


    public void removeFavourite() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL + favID, CommonResponse.class, null, new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        mListener.showToast(response.getMessage());

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            },AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    public void addFavourite(Integer kitchenId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new DishFavRequest(String.valueOf(mListener.getEatId()), String.valueOf(kitchenId)), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        favID = response.getFavid();
                        mListener.showToast(response.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            },AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void onItemClick() {
        mListener.onItemClick(dishList);

        //     mListener.addQuantity();

        //  mListener.onItemClick(isJobCompleted,Integer.parseInt(sales_emp_id.toString()) , Integer.parseInt(makeit_userid.toString()), date.toString(), name.toString(), email.toString(), phoneno.toString(), brandname.toString(),address.toString(),lat.toString(),lng.toString(),localityid.toString());

    }

    public interface DishItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick(KitchenDishResponse.Productlist blogUrl);

        String addQuantity();

        void subQuantity();

        void enableAdd();

        void saveCart(String jsonCartDetails);

        void checkAllCart();

        void addFavourites(Integer dishId, String fav);

        void removeFavourites(Integer favId);


        void productNotAvailable(int quantity,String productname);

        void refresh();

        Integer getEatId();

        void showToast(String msg);


        void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price);
    }

}
