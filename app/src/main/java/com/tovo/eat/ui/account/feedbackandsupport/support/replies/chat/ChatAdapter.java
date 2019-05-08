package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemChatBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<ChatResponse.Result> orderLists;
    ChatAdapterListener mOrdersAdapterListener;

    public ChatAdapter(List<ChatResponse.Result> orderLists) {
        this.orderLists = orderLists;
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemChatBinding listItemOrdersBinding = ListItemChatBinding.inflate(LayoutInflater.from(parent.getContext()),
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



    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemEmptyBinding mBinding;


        EmptyItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptyItemViewModel("No chats found");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }



    public class OrdersViewHolder extends BaseViewHolder implements ChatItemViewModel.OrdersItemViewModelListener {

        private ListItemChatBinding mBinding;

        private ChatItemViewModel ordersItemViewModel;

        public OrdersViewHolder(ListItemChatBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public OrdersViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            if (orderLists.isEmpty()) return;
            final ChatResponse.Result order = orderLists.get(position);
            ordersItemViewModel = new ChatItemViewModel(order, this);
            mBinding.setChatItemViewModel(ordersItemViewModel);
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(ChatResponse.Result orders) {
            //Log.e("error",String.valueOf(order_status));
            /*if (orders.orderstatus == 6) {
                mOrdersAdapterListener.chatList(orders);
            }else{
                mOrdersAdapterListener.ordersDetailed(orders);
            }*/
        }
    }

    public void clearItems() {
        orderLists.clear();
    }

    public void addChatItems(List<ChatResponse.Result> blogList) {
        orderLists.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(ChatAdapterListener listener) {
        this.mOrdersAdapterListener = listener;
    }

    public interface ChatAdapterListener {
        void ordersHistory(ChatResponse.Result orders);
        void ordersDetailed(ChatResponse.Result orders);
    }


}
