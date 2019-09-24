package com.tovo.eat.ui.account.orderhistory.historylist;

public class OrdersHistoryEmptyItemViewModel {


    private final OrdersHistoryEmptyItemViewModelListener mListener;

    public OrdersHistoryEmptyItemViewModel(OrdersHistoryEmptyItemViewModelListener listener) {
        this.mListener = listener;
    }

    public interface OrdersHistoryEmptyItemViewModelListener {

        void onRetryClick();
    }



}
