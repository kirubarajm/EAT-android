package com.tovo.eat.ui.account.chatsupport;

import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.utilities.chat.IssuesAdapter;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class HistoryHelpModule {

    @Provides
    HistoryHelpViewModel provideOrdersCancelViewModel(DataManager dataManager) {
        return new HistoryHelpViewModel(dataManager);
    }
    @Provides
    LinearLayoutManager provideLinearLayoutManager(HistoryHelpActivity activity) {
        return new LinearLayoutManager(activity);
    }
    @Provides
    IssuesAdapter provideIssuesAdapter() {
        return new IssuesAdapter(new ArrayList<>());
    }
}
