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

    
      "makeit_userid": 3,
              "makeit_username": "sureshaaaa124",
              "product_name": "paya",
              "productid": 6,
              "image": "https://eattovo.s3.amazonaws.com/upload/sales/makeit/fish.jpg",
              "price": 5,
              "producttype": "1",
              "distance": 2.39197522265615
    
    
    
    

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
    List<CartRequestPojo.Result> results = new ArrayList<>();
    CartRequestPojo cartRequestPojo = new CartRequestPojo();
    CartRequestPojo.Result cartRequestPojoResult = new CartRequestPojo.Result();


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
            this.sprice.set(String.valueOf(dishList.getProductPrice()));
            this.price.set((dishList.getProductPrice()));
        } else {

            if (cartRequestPojo.getResult() != null) {
                results.clear();
                results.addAll(cartRequestPojo.getResult());

                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).getMakeitUserid().equals(dishList.getMakeitUserid())) {
                        if (results.get(i).getProductid().equals(dishList.getProductid())) {
                            isAddClicked.set(true);
                            this.makeit_username.set(dishList.getMakeitUsername());
                            this.producttype.set(dishList.getProducttype());
                            this.image.set(dishList.getImage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(dishList.getMakeitUserid());
                            this.sprice.set(String.valueOf(dishList.getProductPrice()));
                            this.price.set((dishList.getProductPrice()));
                            quantity.set(results.get(i).getProductQuantity());
                            sQuantity.set(String.valueOf(quantity.get()));

                        } else {
                            this.makeit_username.set(dishList.getMakeitUsername());
                            this.producttype.set(dishList.getProducttype());
                            this.image.set(dishList.getImage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(dishList.getMakeitUserid());
                            this.sprice.set(String.valueOf(dishList.getProductPrice()));
                            this.price.set((dishList.getProductPrice()));
                        }
                    } else {
                        this.makeit_username.set(dishList.getMakeitUsername());
                        this.producttype.set(dishList.getProducttype());
                        this.image.set(dishList.getImage());
                        this.product_name.set(dishList.getProductName());
                        this.product_id.set(dishList.getProductid());
                        this.makeit_userid.set(dishList.getMakeitUserid());
                        this.sprice.set(String.valueOf(dishList.getProductPrice()));
                        this.price.set((dishList.getProductPrice()));
                    }


                }
            }

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


        if (cartRequestPojo.getResult() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getResult());
        }


        if (cartRequestPojo.getMakeitUserid() == null) {
            cartRequestPojo.setKitchenId(makeit_userid.get());
            cartRequestPojo.setKitchenName(makeit_username.get());
            cartRequestPojo.setKitchenType(producttype.get());
            cartRequestPojo.setEatId(makeit_userid.get());

            cartRequestPojo.setResult(null);
            results.clear();

            cartRequestPojoResult.setProductId(product_id.get());
            cartRequestPojoResult.setProductName(product_name.get());
            cartRequestPojoResult.setProductImage(image.get());
            cartRequestPojoResult.setKitchenName(makeit_username.get());
            cartRequestPojoResult.setProductPrice(price.get());
            cartRequestPojoResult.setKitchenId(makeit_userid.get());
            cartRequestPojoResult.setProductQuantity(quantity.get());
            results.add(cartRequestPojoResult);

        } else {

            if (cartRequestPojo.getResult() != null) {


                int totalSize = cartRequestPojo.getResult().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (makeit_userid.get().equals(results.get(i).getMakeitUserid())) {

                            if (product_id.get().equals(results.get(i).getProductid())) {
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.set(i, cartRequestPojoResult);

                            }
                        } else {
                            cartRequestPojo.setResult(null);
                            results.clear();

                            cartRequestPojo.setKitchenId(makeit_userid.get());
                            cartRequestPojo.setKitchenName(makeit_username.get());
                            cartRequestPojo.setKitchenType(producttype.get());
                            cartRequestPojo.setEatId(makeit_userid.get());

                            cartRequestPojoResult.setProductId(product_id.get());
                            cartRequestPojoResult.setProductName(product_name.get());
                            cartRequestPojoResult.setProductImage(image.get());
                            cartRequestPojoResult.setKitchenName(makeit_username.get());
                            cartRequestPojoResult.setProductPrice(price.get());
                            cartRequestPojoResult.setKitchenId(makeit_userid.get());
                            cartRequestPojoResult.setProductQuantity(quantity.get());
                            results.add(cartRequestPojoResult);


                        }
                    }

                } else {

                    cartRequestPojoResult.setProductId(product_id.get());
                    cartRequestPojoResult.setProductName(product_name.get());
                    cartRequestPojoResult.setProductImage(image.get());
                    cartRequestPojoResult.setKitchenName(makeit_username.get());
                    cartRequestPojoResult.setProductPrice(price.get());
                    cartRequestPojoResult.setKitchenId(makeit_userid.get());
                    cartRequestPojoResult.setProductQuantity(quantity.get());
                    results.add(cartRequestPojoResult);

                }
            } else {
                cartRequestPojoResult.setProductId(product_id.get());
                cartRequestPojoResult.setProductName(product_name.get());
                cartRequestPojoResult.setProductImage(image.get());
                cartRequestPojoResult.setKitchenName(makeit_username.get());
                cartRequestPojoResult.setProductPrice(price.get());
                cartRequestPojoResult.setKitchenId(makeit_userid.get());
                cartRequestPojoResult.setProductQuantity(quantity.get());
                results.add(cartRequestPojoResult);


            }


        }

        cartRequestPojo.setResult(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

        Log.e("cart", json);


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


        if (cartRequestPojo.getResult() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getResult());
        }

        if (cartRequestPojo.getMakeitUserid() == null) {
            cartRequestPojo.setKitchenId(makeit_userid.get());
            cartRequestPojo.setKitchenName(makeit_username.get());
            cartRequestPojo.setKitchenType(producttype.get());
            cartRequestPojo.setEatId(makeit_userid.get());

            cartRequestPojo.setResult(null);
            results.clear();

            cartRequestPojoResult.setProductId(product_id.get());
            cartRequestPojoResult.setProductName(product_name.get());
            cartRequestPojoResult.setProductImage(image.get());
            cartRequestPojoResult.setKitchenName(makeit_username.get());
            cartRequestPojoResult.setProductPrice(price.get());
            cartRequestPojoResult.setKitchenId(makeit_userid.get());
            cartRequestPojoResult.setProductQuantity(quantity.get());
            results.add(cartRequestPojoResult);


        } else {

            if (cartRequestPojo.getResult() != null) {


                int totalSize = cartRequestPojo.getResult().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (makeit_userid.get().equals(results.get(i).getMakeitUserid())) {

                            if (product_id.get().equals(results.get(i).getProductid())) {
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.set(i, cartRequestPojoResult);
                            }
                        } else {


                            cartRequestPojo.setResult(null);
                            results.clear();

                            cartRequestPojo.setKitchenId(makeit_userid.get());
                            cartRequestPojo.setKitchenName(makeit_username.get());
                            cartRequestPojo.setKitchenType(producttype.get());
                            cartRequestPojo.setEatId(makeit_userid.get());

                            cartRequestPojoResult.setProductId(product_id.get());
                            cartRequestPojoResult.setProductName(product_name.get());
                            cartRequestPojoResult.setProductImage(image.get());
                            cartRequestPojoResult.setKitchenName(makeit_username.get());
                            cartRequestPojoResult.setProductPrice(price.get());
                            cartRequestPojoResult.setKitchenId(makeit_userid.get());
                            cartRequestPojoResult.setProductQuantity(quantity.get());
                            results.add(cartRequestPojoResult);


                        }
                    }

                } else {

                    cartRequestPojoResult.setProductId(product_id.get());
                    cartRequestPojoResult.setProductName(product_name.get());
                    cartRequestPojoResult.setProductImage(image.get());
                    cartRequestPojoResult.setKitchenName(makeit_username.get());
                    cartRequestPojoResult.setProductPrice(price.get());
                    cartRequestPojoResult.setKitchenId(makeit_userid.get());
                    cartRequestPojoResult.setProductQuantity(quantity.get());
                    results.add(cartRequestPojoResult);

                }
            } else {
                cartRequestPojoResult.setProductId(product_id.get());
                cartRequestPojoResult.setProductName(product_name.get());
                cartRequestPojoResult.setProductImage(image.get());
                cartRequestPojoResult.setKitchenName(makeit_username.get());
                cartRequestPojoResult.setProductPrice(price.get());
                cartRequestPojoResult.setKitchenId(makeit_userid.get());
                cartRequestPojoResult.setProductQuantity(quantity.get());
                results.add(cartRequestPojoResult);


            }


        }

        cartRequestPojo.setResult(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

        Log.e("cart", json);

        if (quantity.get() == 0) {
            isAddClicked.set(false);

        }

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

        if (cartRequestPojo.getResult() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getResult());
        }

        if (cartRequestPojo.getMakeitUserid() == null) {
            cartRequestPojo.setKitchenId(makeit_userid.get());
            cartRequestPojo.setKitchenName(makeit_username.get());
            cartRequestPojo.setKitchenType(producttype.get());
            cartRequestPojo.setEatId(makeit_userid.get());

            cartRequestPojo.setResult(null);

            cartRequestPojoResult.setProductId(product_id.get());
            cartRequestPojoResult.setProductName(product_name.get());
            cartRequestPojoResult.setProductImage(image.get());
            cartRequestPojoResult.setKitchenName(makeit_username.get());
            cartRequestPojoResult.setProductPrice(price.get());
            cartRequestPojoResult.setKitchenId(makeit_userid.get());
            cartRequestPojoResult.setProductQuantity(quantity.get());
            results.clear();
            results.add(cartRequestPojoResult);

        } else {

            if (cartRequestPojo.getResult() != null) {


                int totalSize = results.size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (makeit_userid.get().equals(results.get(i).getMakeitUserid())) {

                            if (product_id.get().equals(results.get(i).getProductid())) {
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.set(i, cartRequestPojoResult);

                            } else {

                                cartRequestPojoResult.setProductId(product_id.get());
                                cartRequestPojoResult.setProductName(product_name.get());
                                cartRequestPojoResult.setProductImage(image.get());
                                cartRequestPojoResult.setKitchenName(makeit_username.get());
                                cartRequestPojoResult.setProductPrice(price.get());
                                cartRequestPojoResult.setKitchenId(makeit_userid.get());
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.add(cartRequestPojoResult);

                            }
                        } else {

                            cartRequestPojo.setKitchenId(makeit_userid.get());
                            cartRequestPojo.setKitchenName(makeit_username.get());
                            cartRequestPojo.setKitchenType(producttype.get());
                            cartRequestPojo.setEatId(makeit_userid.get());

                            cartRequestPojo.setResult(null);

                            cartRequestPojoResult.setProductId(product_id.get());
                            cartRequestPojoResult.setProductName(product_name.get());
                            cartRequestPojoResult.setProductImage(image.get());
                            cartRequestPojoResult.setKitchenName(makeit_username.get());
                            cartRequestPojoResult.setProductPrice(price.get());
                            cartRequestPojoResult.setKitchenId(makeit_userid.get());
                            cartRequestPojoResult.setProductQuantity(quantity.get());
                            results.clear();
                            totalSize = 1;
                            results.add(cartRequestPojoResult);


                        }
                    }

                } else {

                    cartRequestPojoResult.setProductId(product_id.get());
                    cartRequestPojoResult.setProductName(product_name.get());
                    cartRequestPojoResult.setProductImage(image.get());
                    cartRequestPojoResult.setKitchenName(makeit_username.get());
                    cartRequestPojoResult.setProductPrice(price.get());
                    cartRequestPojoResult.setKitchenId(makeit_userid.get());
                    cartRequestPojoResult.setProductQuantity(quantity.get());
                    results.add(cartRequestPojoResult);

                }
            } else {
                cartRequestPojoResult.setProductId(product_id.get());
                cartRequestPojoResult.setProductName(product_name.get());
                cartRequestPojoResult.setProductImage(image.get());
                cartRequestPojoResult.setKitchenName(makeit_username.get());
                cartRequestPojoResult.setProductPrice(price.get());
                cartRequestPojoResult.setKitchenId(makeit_userid.get());
                cartRequestPojoResult.setProductQuantity(quantity.get());
                results.add(cartRequestPojoResult);


            }


        }

        cartRequestPojo.setResult(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        mListener.saveCart(json);

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

    }

}
