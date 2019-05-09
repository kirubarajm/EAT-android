package com.tovo.eat.ui.account.favorites;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.CartRequestPojo;

public class FavoritesTabActivityViewModel extends BaseViewModel<FavoritesTabActivityNavigator> {



    public ObservableField<String> kitchenName = new ObservableField<>();
    public ObservableField<String> cartItems = new ObservableField<>();
    public ObservableField<String> cartPrice = new ObservableField<>();
    public ObservableField<String> items = new ObservableField<>();

    public ObservableBoolean cart = new ObservableBoolean();
    public ObservableBoolean isFavourite = new ObservableBoolean();




    public FavoritesTabActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void goBack(){
        getNavigator().goBack();

    }



    public void favClicked(boolean status){

        getDataManager().setIsFav(status);
    }



    public void viewCart() {
        getNavigator().viewCart();
    }

    public void totalCart() {

        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

        cart.set(false);

        if (cartRequestPojo == null)
            cartRequestPojo = new CartRequestPojo();

        int count = 0;
        int price = 0;

        if (cartRequestPojo.getCartitems() != null) {

            if (cartRequestPojo.getCartitems().size() == 0) {

                cart.set(false);

            } else {

                for (int i = 0; i < cartRequestPojo.getCartitems().size(); i++) {
                    count = count + cartRequestPojo.getCartitems().get(i).getQuantity();
                    price = price + ((cartRequestPojo.getCartitems().get(i).getPrice()) * cartRequestPojo.getCartitems().get(i).getQuantity());
                }

                if (count <= 0) {
                    cart.set(false);
                } else {
                    if (count == 1) {

                        cartItems.set(String.valueOf(count) + " Item");
                        cart.set(true);

                        cartPrice.set(String.valueOf(price));

                        items.set("Item");
                    } else {

                        cartItems.set(String.valueOf(count) + " Items");
                        cart.set(true);
                        cartPrice.set(String.valueOf(price));
                        items.set("Items");
                    }

                }
            }
        }
    }

}
