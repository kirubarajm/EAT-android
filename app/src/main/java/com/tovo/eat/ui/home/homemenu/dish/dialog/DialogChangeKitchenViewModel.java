package com.tovo.eat.ui.home.homemenu.dish.dialog;

import com.google.gson.Gson;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.ArrayList;
import java.util.List;

public class DialogChangeKitchenViewModel extends BaseViewModel<DialogChangeKitchenCallBack> {


    public DialogChangeKitchenViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void dialogConfirm() {
        getNavigator().confirmClick();
    }

    public void dialogCancel() {
        getNavigator().cancelClick();
    }


    public void addToCart(Long makeitId, Integer productId, Integer quantity, Integer price) {

        List<CartRequestPojo.Cartitem> results = new ArrayList<>();
        CartRequestPojo cartRequestPojo = new CartRequestPojo();
        CartRequestPojo.Cartitem cartRequestPojoCartitem = new CartRequestPojo.Cartitem();

        cartRequestPojo.setMakeitUserid(makeitId);
        cartRequestPojoCartitem.setProductid(productId);
        cartRequestPojoCartitem.setQuantity(quantity);
        cartRequestPojoCartitem.setPrice(price);
        results.add(cartRequestPojoCartitem);


        cartRequestPojo.setCartitems(results);
        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        getDataManager().setCartDetails(json);
    }

}
