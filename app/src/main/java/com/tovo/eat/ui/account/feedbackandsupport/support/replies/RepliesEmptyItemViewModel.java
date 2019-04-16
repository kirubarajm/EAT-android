package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

public class RepliesEmptyItemViewModel {

    private final RepliesEmptyItemViewModelListener mListener;

    public RepliesEmptyItemViewModel(RepliesEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public void onRetryClick() {
        mListener.onRetryClick();
    }

    public interface RepliesEmptyItemViewModelListener {

        void onRetryClick();
    }
}
