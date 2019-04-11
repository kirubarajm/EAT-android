package com.tovo.eat.ui.home.homemenu.dish;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.ArrayList;
import java.util.List;


public class DishItemViewModel {

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

    public final ObservableBoolean isAddClicked = new ObservableBoolean();
    public final DishItemViewModelListener mListener;
    private final DishResponse.Result dishList;
    List<CartRequestPojo.Cartitem> results = new ArrayList<>();
    CartRequestPojo cartRequestPojo = new CartRequestPojo();
    CartRequestPojo.Cartitem cartRequestPojoCartitem = new CartRequestPojo.Cartitem();


    public DishItemViewModel(DishItemViewModelListener mListener, DishResponse.Result dishList) {


        this.mListener = mListener;
        this.dishList = dishList;
        //  this.date.set(mSalesList.getDate());
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);

        if (cartRequestPojo == null) {
            this.makeit_username.set(dishList.getMakeitUsername());
            this.producttype.set(dishList.getProducttype());
            this.image.set(dishList.getImage());
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


                    if (cartRequestPojo.getMakeitUserid() != null) {

                        if (cartRequestPojo.getMakeitUserid().equals(dishList.getMakeitUserid())) {
                            if (results.get(i).getProductid().equals(dishList.getProductid())) {
                                isAddClicked.set(true);
                                this.makeit_username.set(dishList.getMakeitUsername());
                                this.producttype.set(dishList.getProducttype());
                                this.image.set(dishList.getImage());
                                this.product_name.set(dishList.getProductName());
                                this.product_id.set(dishList.getProductid());
                                this.makeit_userid.set(dishList.getMakeitUserid());
                                this.sprice.set(String.valueOf(dishList.getPrice()));
                                this.price.set((dishList.getPrice()));
                                quantity.set(results.get(i).getQuantity());
                                sQuantity.set(String.valueOf(quantity.get()));

                            } else {
                                this.makeit_username.set(dishList.getMakeitUsername());
                                this.producttype.set(dishList.getProducttype());
                                this.image.set(dishList.getImage());
                                this.product_name.set(dishList.getProductName());
                                this.product_id.set(dishList.getProductid());
                                this.makeit_userid.set(dishList.getMakeitUserid());
                                this.sprice.set(String.valueOf(dishList.getPrice()));
                                this.price.set((dishList.getPrice()));
                            }
                        } else {
                            this.makeit_username.set(dishList.getMakeitUsername());
                            this.producttype.set(dishList.getProducttype());
                            this.image.set(dishList.getImage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(dishList.getMakeitUserid());
                            this.sprice.set(String.valueOf(dishList.getPrice()));
                            this.price.set((dishList.getPrice()));
                        }

                    }else {
                        this.makeit_username.set(dishList.getMakeitUsername());
                        this.producttype.set(dishList.getProducttype());
                        this.image.set(dishList.getImage());
                        this.product_name.set(dishList.getProductName());
                        this.product_id.set(dishList.getProductid());
                        this.makeit_userid.set(dishList.getMakeitUserid());
                        this.sprice.set(String.valueOf(dishList.getPrice()));
                        this.price.set((dishList.getPrice()));

                    }
                }
            }

        }

        if (quantity.get() != null)
            if (quantity.get() == 0) {
                isAddClicked.set(false);

            }

    }

    public void addClicked() throws NullPointerException {

        quantity.set(quantity.get() + 1);

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

        if (cartRequestPojo.getMakeitUserid() != null)  {

            if (cartRequestPojo.getCartitems() != null) {


                int totalSize = cartRequestPojo.getCartitems().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (dishList.getMakeitUserid().equals(cartRequestPojo.getMakeitUserid())) {

                            if (dishList.getProductid().equals(results.get(i).getProductid())) {
                                cartRequestPojoCartitem.setProductid(dishList.getProductid());
                                cartRequestPojoCartitem.setQuantity(quantity.get());
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

        if (quantity.get() == 0) {
            isAddClicked.set(false);

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
            cartRequestPojoCartitem.setQuantity(quantity.get());
            results.clear();
            results.add(cartRequestPojoCartitem);

        } else {

            if (cartRequestPojo.getCartitems() != null) {


                int totalSize = results.size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {

                        if (dishList.getMakeitUserid().equals(cartRequestPojo.getMakeitUserid())) {

                                cartRequestPojoCartitem.setProductid(dishList.getProductid());
                                cartRequestPojoCartitem.setQuantity(quantity.get());
                                results.add(cartRequestPojoCartitem);


                        } else {

                            cartRequestPojo.setCartitems(null);
                            results.clear();

                            cartRequestPojo.setMakeitUserid(dishList.getMakeitUserid());
                            cartRequestPojoCartitem.setProductid(dishList.getProductid());
                            cartRequestPojoCartitem.setQuantity(quantity.get());
                           // totalSize = 1;
                            results.add(cartRequestPojoCartitem);
                            break;

                        }
                    }

                } else {

                    cartRequestPojoCartitem.setProductid(dishList.getProductid());
                    cartRequestPojoCartitem.setQuantity(quantity.get());
                    results.add(cartRequestPojoCartitem);

                }
            } else {
                cartRequestPojoCartitem.setProductid(dishList.getProductid());
                cartRequestPojoCartitem.setQuantity(quantity.get());
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


    }

}
