package com.tovo.eat.ui.signup.fagsandsupport;

import android.databinding.ObservableBoolean;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class FaqsAndSupportViewModel extends BaseViewModel<FaqsAndSupportNavigator> {
    public ObservableBoolean contact = new ObservableBoolean();

    public FaqsAndSupportViewModel(DataManager dataManager) {
        super(dataManager);
        contact.set(false);
    }


    public void faq() {
        getNavigator().feedBackClick();
    }

    public void supportClick() {
        //   getNavigator().supportClick();

        if (contact.get()) {
            contact.set(false);
        } else {
            contact.set(true);
        }

    }


    public void goBack() {
        getNavigator().goBack();

    }

    public void callCustomer() {
        getNavigator().callCusstomerCare();

    }

}
