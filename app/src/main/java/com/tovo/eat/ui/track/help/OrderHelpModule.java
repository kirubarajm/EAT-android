package com.tovo.eat.ui.track.help;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.utilities.chat.IssuesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class OrderHelpModule {

    @Provides
    OrderHelpViewModel provideOrdersCancelViewModel(DataManager dataManager) {
        return new OrderHelpViewModel(dataManager);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(OrderHelpActivity activity) {
        return new LinearLayoutManager(activity);
    }
    @Provides
    IssuesAdapter provideIssuesAdapter() {
        return new IssuesAdapter(new ArrayList<>());
    }
}
