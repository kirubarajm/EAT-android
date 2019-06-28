package com.tovo.eat.ui.cart.refund;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class RefundListModule {

    DataManager dataManager;

    @Provides
    RefundListViewModel provideRefundListViewModel(DataManager dataManager) {
        this.dataManager=dataManager;
        return new RefundListViewModel(dataManager);
    }

    @Provides
    RefundListAdapter provideRefundListAdapter() {
        return new RefundListAdapter(new ArrayList<>(),dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(RefundListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
