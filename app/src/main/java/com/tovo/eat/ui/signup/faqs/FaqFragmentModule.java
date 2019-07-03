package com.tovo.eat.ui.signup.faqs;

import android.support.v7.widget.LinearLayoutManager;


import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class FaqFragmentModule {

    @Provides
    FaqFragmentViewModel provideFaqViewModel(DataManager dataManager)
    {
        return new FaqFragmentViewModel(dataManager);
    }

    @Provides
    FaqsAdapter provideFaqsAdapter() {
        return new FaqsAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(FaqActivity activity) {
        return new LinearLayoutManager(activity);
    }
}
