package com.tovo.eat.ui.kitchendetails;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

import java.util.List;

public interface KitchenDetailsNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void dishListLoaded(KitchenDishResponse response);

    void viewCart();

    void changeImageArray(KitchenDishResponse.Kitchenmenuimage kitchenmenuimages);


    void goBack();
void loadError();
    void animChanges(boolean status);

    void update(List<KitchenDishResponse.Kitchenmenuimage> kitchenmenuimageArrayList);



}
