package com.tovo.eat.ui.account.orderhistory.historylist;
import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderHistoryActivityModule {

    @Provides
    OrderHistoryActivityViewModel provideOrdersHistoryViewModel(DataManager dataManager) {
        return new OrderHistoryActivityViewModel(dataManager);
    }

    @Provides
    OrdersHistoryActivityAdapter provideOrdersHistoryListAdapter() {
        return new OrdersHistoryActivityAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderHistoryActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
