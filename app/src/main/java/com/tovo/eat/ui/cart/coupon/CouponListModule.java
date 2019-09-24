package com.tovo.eat.ui.cart.coupon;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class CouponListModule {

    DataManager dataManager;

    @Provides
    CouponListViewModel provideRefundListViewModel(DataManager dataManager) {
        this.dataManager = dataManager;
        return new CouponListViewModel(dataManager);
    }

    @Provides
    CouponListAdapter provideRefundListAdapter() {
        return new CouponListAdapter(new ArrayList<>(), dataManager);
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(CouponListActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
