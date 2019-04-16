package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.widget.LinearLayoutManager;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.utilities.ViewModelProviderFactory;
import java.util.ArrayList;
import dagger.Module;
import dagger.Provides;

@Module
public class RepliesActivityModule {

    @Provides
    RepliesActivityViewModel provideRepliesViewModel(DataManager dataManager) {
        return new RepliesActivityViewModel(dataManager);
    }

    @Provides
    ViewModelProvider.Factory RepliesViewModelProvider(RepliesActivityViewModel mRepliesActivityViewModel) {
        return new ViewModelProviderFactory<>(mRepliesActivityViewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RepliesActivity fragment) {
        return new LinearLayoutManager(fragment);
    }

    @Provides
    RepliesAdapter provideRepliesAdapter() {
        return new RepliesAdapter(new ArrayList<>());
    }
}
