package com.tovo.eat.ui.account.feedbackandsupport.helpcenter;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.utilities.chat.IssuesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class HelpModule {

    @Provides
    HelpViewModel provideOrdersCancelViewModel(DataManager dataManager) {
        return new HelpViewModel(dataManager);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(HelpActivity activity) {
        return new LinearLayoutManager(activity);
    }
    @Provides
    IssuesAdapter provideIssuesAdapter() {
        return new IssuesAdapter(new ArrayList<>());
    }
}
