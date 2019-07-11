package com.tovo.eat.ui.cart.refund.alert;


import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchenModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogRefundAlertProvider {

    @ContributesAndroidInjector(modules = DialogRefundAlertModule.class)
    abstract DialogRefundAlert provideDialogRefundAlertFactory();

}
