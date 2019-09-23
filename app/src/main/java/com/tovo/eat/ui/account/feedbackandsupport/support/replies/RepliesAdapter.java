package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemRepliesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class RepliesAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<RepliesResponse.Result> orderLists;
    RepliesAdapterListener mRepliesAdapterListener;

    public RepliesAdapter(List<RepliesResponse.Result> orderLists) {
        this.orderLists = orderLists;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemRepliesBinding listItemOrdersBinding = ListItemRepliesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new RepliesViewHolder(listItemOrdersBinding);
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
    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemEmptyBinding mBinding;
        EmptyItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptyItemViewModel("No replies found");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class RepliesViewHolder extends BaseViewHolder implements RepliesItemViewModel.RepliesItemViewModelListener {

        private ListItemRepliesBinding mBinding;

        private RepliesItemViewModel ordersItemViewModel;

        public RepliesViewHolder(ListItemRepliesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (orderLists.isEmpty()) return;
            final RepliesResponse.Result order = orderLists.get(position);
            ordersItemViewModel = new RepliesItemViewModel(order, this);
            mBinding.setRepliesListItemViewModel(ordersItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(RepliesResponse.Result replies) {

            mRepliesAdapterListener.chatList(replies);
        }
    }

    public void clearItems() {
        orderLists.clear();
    }

    public void addOrderItems(List<RepliesResponse.Result> blogList) {
        orderLists.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(RepliesAdapterListener listener) {
        this.mRepliesAdapterListener = listener;
    }

    public interface RepliesAdapterListener {
        void chatList(RepliesResponse.Result orders);
    }
}
