package com.tovo.eat.ui.account.orderhistory.historylist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemOrdersHistoryListBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class OrdersHistoryActivityAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<OrdersHistoryListResponse.Result> orderLists;
    OrdersHistoryAdapterListener mOrdersAdapterListener;

    public OrdersHistoryActivityAdapter(List<OrdersHistoryListResponse.Result> orderLists) {
        this.orderLists = orderLists;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemOrdersHistoryListBinding listItemOrdersBinding = ListItemOrdersHistoryListBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new OrdersViewHolder(listItemOrdersBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ListItemEmptyBinding listItemOrdersBinding1 = ListItemEmptyBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(listItemOrdersBinding1);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        if (orderLists != null && orderLists.size() > 0) {
            return orderLists.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (orderLists != null && !orderLists.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearItems() {
        orderLists.clear();
    }

    public void addOrderItems(List<OrdersHistoryListResponse.Result> blogList) {
        orderLists.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(OrdersHistoryAdapterListener listener) {
        this.mOrdersAdapterListener = listener;
    }

    public interface OrdersHistoryAdapterListener {
        void listItem(OrdersHistoryListResponse.Result mOrderList);
    }

    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemEmptyBinding mBinding;


        EmptyItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptyItemViewModel("No history found");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class OrdersViewHolder extends BaseViewHolder implements OrdersHistoryListItemModel.OrdersItemViewModelListener {

        private ListItemOrdersHistoryListBinding mBinding;

        private OrdersHistoryListItemModel ordersItemViewModel;

        public OrdersViewHolder(ListItemOrdersHistoryListBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (orderLists.isEmpty()) return;
            final OrdersHistoryListResponse.Result order = orderLists.get(position);
            ordersItemViewModel = new OrdersHistoryListItemModel(order, this);
            mBinding.setOrdersListItemViewModel(ordersItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(OrdersHistoryListResponse.Result mOrderList) {
            if (mOrderList != null) {
                Log.e("error", String.valueOf(mOrderList));
                mOrdersAdapterListener.listItem(mOrderList);
            }
        }

    }

}
