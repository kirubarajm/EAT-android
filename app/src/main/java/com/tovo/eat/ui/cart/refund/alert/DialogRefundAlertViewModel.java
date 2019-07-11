package com.tovo.eat.ui.cart.refund.alert;

import com.google.gson.Gson;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.ArrayList;
import java.util.List;

public class DialogRefundAlertViewModel extends BaseViewModel<DialogRefundAlertCallBack> {


    public DialogRefundAlertViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void dialogConfirm() {
        getNavigator().confirmClick();
    }

    public void dialogCancel() {
        getNavigator().cancelClick();
    }




}
