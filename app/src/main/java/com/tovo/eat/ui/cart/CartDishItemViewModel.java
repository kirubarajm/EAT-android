package com.tovo.eat.ui.cart;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.ArrayList;
import java.util.List;


public class CartDishItemViewModel {

    public final ObservableField<String> producttype = new ObservableField<>();
    public final ObservableField<String> product_name = new ObservableField<>();
    public final ObservableField<String> veg_type = new ObservableField<>();
    public final ObservableField<String> image = new ObservableField<>();

    public final ObservableField<String> sprice = new ObservableField<>();
    public final ObservableField<String> sQuantity = new ObservableField<>();
    public final ObservableField<String> availability = new ObservableField<>();
    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final ObservableBoolean isAvailable = new ObservableBoolean();
    private final ObservableField<Integer> price = new ObservableField<>();
    private final ObservableField<Integer> quantity = new ObservableField<>();
    private final DishItemViewModelListener mListener;
    private final CartPageResponse.Item dishList;

    private final List<CartRequestPojo.Cartitem> results = new ArrayList<>();
    private final CartRequestPojo.Cartitem cartRequestPojoResult = new CartRequestPojo.Cartitem();
    private CartRequestPojo cartRequestPojo = new CartRequestPojo();


    public CartDishItemViewModel(DishItemViewModelListener mListener, CartPageResponse.Item dishList) {


        this.mListener = mListener;
        this.dishList = dishList;


        product_name.set(dishList.getProductName());
        sprice.set(String.valueOf(dishList.getPrice()));
        sQuantity.set(String.valueOf(dishList.getCartquantity()));
        quantity.set(dishList.getCartquantity());


        if (dishList.isAvailablity()) {
            isAvailable.set(true);
        } else {
            if (dishList.getQuantity() > 0) {
                isAvailable.set(false);
                availability.set(dishList.getQuantity() + " Only left");
            }

        }


        image.set(dishList.getImage());


        isAddClicked.set(true);


        if (dishList.getVegtype().equalsIgnoreCase("0")) {

            veg_type.set("Veg");
        } else {
            veg_type.set("Non-Veg");

        }
        producttype.set("Mediterranean"+" | "+veg_type.get());

    }

    public void addClicked() throws NullPointerException {


        int checkQuantity = quantity.get();


        if (checkQuantity + 1 <= dishList.getQuantity()) {
            quantity.set(quantity.get() + 1);

        } else {
            mListener.productNotAvailable();
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

        if (cartRequestPojo.getCartitems() != null) {


            int totalSize = cartRequestPojo.getCartitems().size();
            if (totalSize != 0) {

                for (int i = 0; i < totalSize; i++) {

                    if (dishList.getProductid().equals(results.get(i).getProductid())) {
                        cartRequestPojoResult.setProductid(dishList.getProductid());
                        cartRequestPojoResult.setQuantity(quantity.get());
                        cartRequestPojoResult.setPrice(dishList.getPrice());
                        results.set(i, cartRequestPojoResult);

                    } else {
                        cartRequestPojo.setCartitems(null);
                        results.clear();

                        cartRequestPojoResult.setProductid(dishList.getProductid());
                        cartRequestPojoResult.setQuantity(quantity.get());
                        cartRequestPojoResult.setPrice(dishList.getPrice());
                        results.add(cartRequestPojoResult);

                    }
                }

            }

        }

        cartRequestPojo.setCartitems(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

        Log.e("cart", json);

        mListener.reload();

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

        if (cartRequestPojo.getCartitems() != null) {


            int totalSize = cartRequestPojo.getCartitems().size();
            if (totalSize != 0) {

                for (int i = 0; i < totalSize; i++) {


                    if (dishList.getProductid().equals(results.get(i).getProductid())) {


                        if (quantity.get() == 0) {
                            results.remove(i);
                        } else {

                            cartRequestPojoResult.setProductid(dishList.getProductid());
                            cartRequestPojoResult.setQuantity(quantity.get());
                            cartRequestPojoResult.setPrice(dishList.getPrice());
                            results.set(i, cartRequestPojoResult);

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

        if (quantity.get() == 0) {
            isAddClicked.set(false);

        }

        mListener.reload();

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

        if (cartRequestPojo.getCartitems() != null) {


            int totalSize = cartRequestPojo.getCartitems().size();
            if (totalSize != 0) {

                for (int i = 0; i < totalSize; i++) {


                    if (dishList.getProductid().equals(results.get(i).getProductid())) {
                        cartRequestPojoResult.setProductid(dishList.getProductid());
                        cartRequestPojoResult.setQuantity(quantity.get());
                        cartRequestPojoResult.setPrice(dishList.getPrice());
                        results.set(i, cartRequestPojoResult);


                    } else {

                        cartRequestPojoResult.setProductid(dishList.getProductid());
                        cartRequestPojoResult.setQuantity(quantity.get());
                        cartRequestPojoResult.setPrice(dishList.getPrice());
                        results.add(cartRequestPojoResult);


                    }
                }

            }

        }

        cartRequestPojo.setCartitems(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

        Log.e("cart", json);


        mListener.reload();


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

        void productNotAvailable();

        void reload();

    }

}
