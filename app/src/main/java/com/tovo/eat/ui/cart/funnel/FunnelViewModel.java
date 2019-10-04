package com.tovo.eat.ui.cart.funnel;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class FunnelViewModel extends BaseViewModel<FunnelNavigator> {

    public FunnelViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void close(){
        getNavigator().close();
    }

}
