package com.tovo.eat.ui.home.kitchendish;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.ArrayList;
import java.util.List;


public class KitchenDishItemViewModel {

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
    private final KitchenDishResponse.Productlist dishList;
    private final KitchenDishResponse.Result originalResult;


    List<CartRequestPojo.Result> results = new ArrayList<>();
    CartRequestPojo cartRequestPojo = new CartRequestPojo();
    CartRequestPojo.Result cartRequestPojoResult = new CartRequestPojo.Result();


    public KitchenDishItemViewModel(DishItemViewModelListener mListener, KitchenDishResponse.Productlist dishList, KitchenDishResponse.Result response) {

        this.originalResult = response;
        this.mListener = mListener;
        this.dishList = dishList;
        //  this.date.set(mSalesList.getDate());
        Gson sGson = new GsonBuilder().create();
        cartRequestPojo = sGson.fromJson(mListener.addQuantity(), CartRequestPojo.class);

        if (cartRequestPojo == null) {
            this.makeit_username.set(response.getMakeitusername());
            this.producttype.set("response null");
            this.image.set(dishList.getProductimage());
            this.product_name.set(dishList.getProductName());
            this.product_id.set(dishList.getProductid());
            this.makeit_userid.set(response.getMakeituserid());
            this.sprice.set(String.valueOf(dishList.getPrice()));
            this.price.set((dishList.getPrice()));
        } else {

            if (cartRequestPojo.getResult() != null) {
                results.clear();
                results.addAll(cartRequestPojo.getResult());

                for (int i = 0; i < results.size(); i++) {
                    if (results.get(i).getMakeit_userid().equals(response.getMakeituserid())) {
                        if (results.get(i).getProductid().equals(dishList.getProductid())) {
                            isAddClicked.set(true);
                            this.makeit_username.set(response.getMakeitusername());
                            //  this.producttype.set(dishList.getProducttype());
                            this.image.set(dishList.getProductimage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(response.getMakeituserid());
                            this.sprice.set(String.valueOf(dishList.getPrice()));
                            this.price.set((dishList.getPrice()));
                            quantity.set(results.get(i).getProductQuantity());
                            sQuantity.set(String.valueOf(quantity.get()));

                        } else {
                            this.makeit_username.set(response.getMakeitusername());
                            //  this.producttype.set(dishList.getProducttype());
                            this.image.set(dishList.getProductimage());
                            this.product_name.set(dishList.getProductName());
                            this.product_id.set(dishList.getProductid());
                            this.makeit_userid.set(response.getMakeituserid());
                            this.sprice.set(String.valueOf(dishList.getPrice()));
                            this.price.set((dishList.getPrice()));
                        }
                    } else {
                        this.makeit_username.set(response.getMakeitusername());
                        // this.producttype.set(dishList.getProducttype());
                        this.image.set(dishList.getProductimage());
                        this.product_name.set(dishList.getProductName());
                        this.product_id.set(dishList.getProductid());
                        this.makeit_userid.set(response.getMakeituserid());
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

        if (cartRequestPojo.getResult() != null) {
            results.clear();
            results.addAll(cartRequestPojo.getResult());
        }


        if (cartRequestPojo.getMakeit_userid() == null) {
            cartRequestPojo.setMakeit_userid(makeit_userid.get());
            cartRequestPojo.setMakeit_username(makeit_username.get());
            cartRequestPojo.setKitchenType(producttype.get());
            cartRequestPojo.setKitchenImage(originalResult.getMakeitimg());
            cartRequestPojo.setEatId(makeit_userid.get());

            cartRequestPojo.setResult(null);
            results.clear();

            cartRequestPojoResult.setProductid(product_id.get());
            cartRequestPojoResult.setProduct_name(product_name.get());
            cartRequestPojoResult.setImage(image.get());
            cartRequestPojoResult.setMakeit_username(makeit_username.get());
            cartRequestPojoResult.setPrice(price.get());
            cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
            cartRequestPojoResult.setProductQuantity(quantity.get());
            results.add(cartRequestPojoResult);

        } else {

            if (cartRequestPojo.getResult() != null) {


                int totalSize = cartRequestPojo.getResult().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (makeit_userid.get().equals(results.get(i).getMakeit_userid())) {

                            if (product_id.get().equals(results.get(i).getProductid())) {
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.set(i, cartRequestPojoResult);

                            }
                        } else {
                            cartRequestPojo.setResult(null);
                            results.clear();

                            cartRequestPojo.setMakeit_userid(makeit_userid.get());
                            cartRequestPojo.setMakeit_username(makeit_username.get());
                            cartRequestPojo.setKitchenType(producttype.get());
                            cartRequestPojo.setKitchenImage(originalResult.getMakeitimg());
                            cartRequestPojo.setEatId(makeit_userid.get());

                            cartRequestPojoResult.setProductid(product_id.get());
                            cartRequestPojoResult.setProduct_name(product_name.get());
                            cartRequestPojoResult.setImage(image.get());
                            cartRequestPojoResult.setMakeit_username(makeit_username.get());
                            cartRequestPojoResult.setPrice(price.get());
                            cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                            cartRequestPojoResult.setProductQuantity(quantity.get());
                            results.add(cartRequestPojoResult);


                        }
                    }

                } else {

                    cartRequestPojoResult.setProductid(product_id.get());
                    cartRequestPojoResult.setProduct_name(product_name.get());
                    cartRequestPojoResult.setImage(image.get());
                    cartRequestPojoResult.setMakeit_username(makeit_username.get());
                    cartRequestPojoResult.setPrice(price.get());
                    cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                    cartRequestPojoResult.setProductQuantity(quantity.get());
                    results.add(cartRequestPojoResult);

                }
            } else {
                cartRequestPojoResult.setProductid(product_id.get());
                cartRequestPojoResult.setProduct_name(product_name.get());
                cartRequestPojoResult.setImage(image.get());
                cartRequestPojoResult.setMakeit_username(makeit_username.get());
                cartRequestPojoResult.setPrice(price.get());
                cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
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

        if (cartRequestPojo.getMakeit_userid() == null) {
            cartRequestPojo.setMakeit_userid(makeit_userid.get());
            cartRequestPojo.setMakeit_username(makeit_username.get());
            cartRequestPojo.setKitchenType(producttype.get());
            cartRequestPojo.setKitchenImage(originalResult.getMakeitimg());
            cartRequestPojo.setEatId(makeit_userid.get());

            cartRequestPojo.setResult(null);
            results.clear();

            cartRequestPojoResult.setProductid(product_id.get());
            cartRequestPojoResult.setProduct_name(product_name.get());
            cartRequestPojoResult.setImage(image.get());
            cartRequestPojoResult.setMakeit_username(makeit_username.get());
            cartRequestPojoResult.setPrice(price.get());
            cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
            cartRequestPojoResult.setProductQuantity(quantity.get());
            results.add(cartRequestPojoResult);


        } else {

            if (cartRequestPojo.getResult() != null) {


                int totalSize = cartRequestPojo.getResult().size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (makeit_userid.get().equals(results.get(i).getMakeit_userid())) {

                            if (product_id.get().equals(results.get(i).getProductid())) {
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.set(i, cartRequestPojoResult);
                            }
                        } else {


                            cartRequestPojo.setResult(null);
                            results.clear();

                            cartRequestPojo.setMakeit_userid(makeit_userid.get());
                            cartRequestPojo.setMakeit_username(makeit_username.get());
                            cartRequestPojo.setKitchenType(producttype.get());
                            cartRequestPojo.setKitchenImage(originalResult.getMakeitimg());
                            cartRequestPojo.setEatId(makeit_userid.get());

                            cartRequestPojoResult.setProductid(product_id.get());
                            cartRequestPojoResult.setProduct_name(product_name.get());
                            cartRequestPojoResult.setImage(image.get());
                            cartRequestPojoResult.setMakeit_username(makeit_username.get());
                            cartRequestPojoResult.setPrice(price.get());
                            cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                            cartRequestPojoResult.setProductQuantity(quantity.get());
                            results.add(cartRequestPojoResult);


                        }
                    }

                } else {

                    cartRequestPojoResult.setProductid(product_id.get());
                    cartRequestPojoResult.setProduct_name(product_name.get());
                    cartRequestPojoResult.setImage(image.get());
                    cartRequestPojoResult.setMakeit_username(makeit_username.get());
                    cartRequestPojoResult.setPrice(price.get());
                    cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                    cartRequestPojoResult.setProductQuantity(quantity.get());
                    results.add(cartRequestPojoResult);

                }
            } else {
                cartRequestPojoResult.setProductid(product_id.get());
                cartRequestPojoResult.setProduct_name(product_name.get());
                cartRequestPojoResult.setImage(image.get());
                cartRequestPojoResult.setMakeit_username(makeit_username.get());
                cartRequestPojoResult.setPrice(price.get());
                cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
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

        if (cartRequestPojo.getMakeit_userid() == null) {
            cartRequestPojo.setMakeit_userid(makeit_userid.get());
            cartRequestPojo.setMakeit_username(makeit_username.get());
            cartRequestPojo.setKitchenImage(originalResult.getMakeitimg());
            cartRequestPojo.setKitchenType(producttype.get());
            cartRequestPojo.setEatId(makeit_userid.get());

            cartRequestPojo.setResult(null);

            cartRequestPojoResult.setProductid(product_id.get());
            cartRequestPojoResult.setProduct_name(product_name.get());
            cartRequestPojoResult.setImage(image.get());
            cartRequestPojoResult.setMakeit_username(makeit_username.get());
            cartRequestPojoResult.setPrice(price.get());
            cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
            cartRequestPojoResult.setProductQuantity(quantity.get());
            results.clear();
            results.add(cartRequestPojoResult);

        } else {

            if (cartRequestPojo.getResult() != null) {


                int totalSize = results.size();
                if (totalSize != 0) {

                    for (int i = 0; i < totalSize; i++) {


                        if (makeit_userid.get().equals(results.get(i).getMakeit_userid())) {

                            if (product_id.get().equals(results.get(i).getProductid())) {
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.set(i, cartRequestPojoResult);

                            } else {

                                cartRequestPojoResult.setProductid(product_id.get());
                                cartRequestPojoResult.setProduct_name(product_name.get());
                                cartRequestPojoResult.setImage(image.get());
                                cartRequestPojoResult.setMakeit_username(makeit_username.get());
                                cartRequestPojoResult.setPrice(price.get());
                                cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                                cartRequestPojoResult.setProductQuantity(quantity.get());
                                results.add(cartRequestPojoResult);

                            }
                        } else {

                            cartRequestPojo.setMakeit_userid(makeit_userid.get());
                            cartRequestPojo.setMakeit_username(makeit_username.get());
                            cartRequestPojo.setKitchenType(producttype.get());
                            cartRequestPojo.setKitchenImage(originalResult.getMakeitimg());
                            cartRequestPojo.setEatId(makeit_userid.get());

                            cartRequestPojo.setResult(null);

                            cartRequestPojoResult.setProductid(product_id.get());
                            cartRequestPojoResult.setProduct_name(product_name.get());
                            cartRequestPojoResult.setImage(image.get());
                            cartRequestPojoResult.setMakeit_username(makeit_username.get());
                            cartRequestPojoResult.setPrice(price.get());
                            cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                            cartRequestPojoResult.setProductQuantity(quantity.get());
                            results.clear();
                            totalSize = 1;
                            results.add(cartRequestPojoResult);


                        }
                    }

                } else {

                    cartRequestPojoResult.setProductid(product_id.get());
                    cartRequestPojoResult.setProduct_name(product_name.get());
                    cartRequestPojoResult.setImage(image.get());
                    cartRequestPojoResult.setMakeit_username(makeit_username.get());
                    cartRequestPojoResult.setPrice(price.get());
                    cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
                    cartRequestPojoResult.setProductQuantity(quantity.get());
                    results.add(cartRequestPojoResult);

                }
            } else {
                cartRequestPojoResult.setProductid(product_id.get());
                cartRequestPojoResult.setProduct_name(product_name.get());
                cartRequestPojoResult.setImage(image.get());
                cartRequestPojoResult.setMakeit_username(makeit_username.get());
                cartRequestPojoResult.setPrice(price.get());
                cartRequestPojoResult.setMakeit_userid(makeit_userid.get());
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
