package com.tovo.eat.ui.home.homemenu.kitchen;

import android.databinding.ObservableField;

public class EmptyItemViewModel {


    public final ObservableField<String> message = new ObservableField<>();

    public EmptyItemViewModel(String message) {
        this.message.set(message);
    }


}
