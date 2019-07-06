package com.tovo.eat.ui.account.favorites.tab;

public interface FavoritesTabActivityNavigator {

    void handleError(Throwable throwable);
    void goBack();

    void viewCart();
}
