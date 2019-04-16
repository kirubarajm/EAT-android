package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

public class ChatEmptyItemViewModel {

    private final ChatEmptyItemViewModelListener mListener;

    public ChatEmptyItemViewModel(ChatEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface ChatEmptyItemViewModelListener {

        void onRetryClick();
    }
}
