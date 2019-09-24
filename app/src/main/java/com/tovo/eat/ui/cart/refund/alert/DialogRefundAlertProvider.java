package com.tovo.eat.ui.cart.refund.alert;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DialogRefundAlertProvider {

    @ContributesAndroidInjector(modules = DialogRefundAlertModule.class)
    abstract DialogRefundAlert provideDialogRefundAlertFactory();

}
