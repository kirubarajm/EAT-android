package com.tovo.eat.ui.home.dialog;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class DialogSelectAddressViewModel extends BaseViewModel<DialogSelectAddressCallBack> {


    public DialogSelectAddressViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void dialogConfirm() {
        getNavigator().confirmClick();
    }


}
