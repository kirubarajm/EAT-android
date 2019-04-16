package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import android.support.v7.widget.LinearLayoutManager;


import com.tovo.eat.data.DataManager;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatActivityModule {
    @Provides
    ChatActivityViewModel provideChatViewModel(DataManager dataManager) {
        return new ChatActivityViewModel(dataManager);
    }

    @Provides
    ChatAdapter provideChatAdapter() {
        return new ChatAdapter(new ArrayList<>());
    }


    @Provides
    LinearLayoutManager provideLinearLayoutManager(ChatActivity fragment) {
        return new LinearLayoutManager(fragment);
    }
}
