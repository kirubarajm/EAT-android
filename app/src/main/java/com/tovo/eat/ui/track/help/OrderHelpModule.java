package com.tovo.eat.ui.track.help;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderHelpModule {

    @Provides
    OrderHelpViewModel provideOrdersHistoryviewViewModel(DataManager dataManager) {
        return new OrderHelpViewModel(dataManager);
    }


    @Provides
    OrdersHistoryActivityItemAdapter provideOrdersHistoryViewItemListAdapter() {
        return new OrdersHistoryActivityItemAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderHelpActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
