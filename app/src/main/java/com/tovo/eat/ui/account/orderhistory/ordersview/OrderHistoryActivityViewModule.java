package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.cart.BillListAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderHistoryActivityViewModule {

    @Provides
    OrderHistoryActivityViewModelView provideOrdersHistoryviewViewModel(DataManager dataManager) {
        return new OrderHistoryActivityViewModelView(dataManager);
    }

    @Provides
    OrdersHistoryActivityItemAdapter provideOrdersHistoryViewItemListAdapter() {
        return new OrdersHistoryActivityItemAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderHistoryActivityView activity) {
        return new LinearLayoutManager(activity);
    }

    @Provides
    BillListAdapter provideHistoryBillListAdapter() {
        return new BillListAdapter(new ArrayList<>());
    }
}
