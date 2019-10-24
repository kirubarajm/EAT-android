package com.tovo.eat.ui.cart.xfactoralert;


import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

import dagger.Module;

@Module
public class XFactorViewModel extends BaseViewModel<XFactorNavigator> {
    public final ObservableField<String> message = new ObservableField<>();
    public XFactorViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void goHome(){
        getNavigator().goHome();
    }
}
