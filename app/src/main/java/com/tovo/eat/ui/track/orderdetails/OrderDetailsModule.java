package com.tovo.eat.ui.track.orderdetails;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryActivityAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.cart.BillListAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderDetailsModule {

    @Provides
    OrderDetailsViewModel provideOrdersHistoryviewViewModel(DataManager dataManager) {
        return new OrderDetailsViewModel(dataManager);
    }


    @Provides
    OrdersHistoryActivityItemAdapter provideOrdersHistoryViewItemListAdapter() {
        return new OrdersHistoryActivityItemAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderDetailsActivity activity) {
        return new LinearLayoutManager(activity);
    }
    @Provides
    BillListAdapter provideHistoryDBillListAdapter() {
        return new BillListAdapter(new ArrayList<>());
    }
}
