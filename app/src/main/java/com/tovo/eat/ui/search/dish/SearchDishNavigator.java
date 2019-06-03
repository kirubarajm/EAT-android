package com.tovo.eat.ui.search.dish;

public interface SearchDishNavigator {

    void handleError(Throwable throwable);

    void goBack();

    void listLoaded();
void viewCart();
}
