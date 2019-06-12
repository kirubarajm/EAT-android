package com.tovo.eat.ui.search;

import android.databinding.ObservableField;

public class EmptySearchItemViewModel {


    public final ObservableField<String> message = new ObservableField<>();

    public EmptySearchItemViewModel(String message) {
        this.message.set(message);
    }


}
