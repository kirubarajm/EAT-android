package com.tovo.eat.ui.account.favorites;


public interface FavouritesNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void viewCart();


    void back();

    void dish();
    void kitchen();
    void goBack();




}
