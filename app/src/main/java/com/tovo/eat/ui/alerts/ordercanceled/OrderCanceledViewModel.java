package com.tovo.eat.ui.alerts.ordercanceled;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class OrderCanceledViewModel extends BaseViewModel<OrderCanceledNavigator> {

    public final ObservableField<String> order = new ObservableField<>();
    public final ObservableField<String> message = new ObservableField<>();
    public final ObservableBoolean codPayment = new ObservableBoolean();

    public OrderCanceledViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void dismiss() {
        getNavigator().submit();
    }

}
