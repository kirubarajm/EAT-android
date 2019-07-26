package com.tovo.eat.ui.alerts.ordercanceled;

import android.databinding.ObservableField;

import com.android.volley.Response;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class OrderCanceledViewModel extends BaseViewModel<OrderCanceledNavigator> {

    public final ObservableField<String> order = new ObservableField<>();
    public final ObservableField<String> message = new ObservableField<>();
    Response.ErrorListener errorListener;


    public OrderCanceledViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void dismiss() {
        getNavigator().submit();

    }

}
