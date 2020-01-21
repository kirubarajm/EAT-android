package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemOrdersHistoryItemBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.utilities.CustomTypefaceSpan;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class OrdersHistoryActivityItemAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<OrdersHistoryActivityResponse.Result.Item> orderLists;
    OrdersHistoryAdapterListener mOrdersAdapterListener;

    public OrdersHistoryActivityItemAdapter(List<OrdersHistoryActivityResponse.Result.Item> orderLists) {
        this.orderLists = orderLists;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemOrdersHistoryItemBinding listItemOrdersBinding = ListItemOrdersHistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addOrderItems(List<OrdersHistoryActivityResponse.Result.Item> orderList) {
        orderLists.addAll(orderList);
        notifyDataSetChanged();
    }

    public void setListener(OrdersHistoryAdapterListener listener) {
        this.mOrdersAdapterListener = listener;
    }

    public interface OrdersHistoryAdapterListener {
        void listItem(OrdersHistoryActivityResponse.Result mOrderList);
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
            emptyItemViewModel = new EmptyItemViewModel("No results found for your selection");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class OrdersViewHolder extends BaseViewHolder implements OrdersHistoryActivityListItemViewModel.OrdersItemViewModelListener {

        private ListItemOrdersHistoryItemBinding mBinding;

        private OrdersHistoryActivityListItemViewModel ordersItemViewModel;

        public OrdersViewHolder(ListItemOrdersHistoryItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public OrdersViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            if (orderLists.isEmpty()) return;
            final OrdersHistoryActivityResponse.Result.Item order = orderLists.get(position);
            ordersItemViewModel = new OrdersHistoryActivityListItemViewModel(order);
            mBinding.setOrdersProductListItemViewModel(ordersItemViewModel);
            mBinding.executePendingBindings();

            Typeface font = Typeface.createFromAsset(MvvmApp.getInstance(). getAssets(), "Poppins-Medium.otf");
            Typeface font2 = Typeface.createFromAsset(MvvmApp.getInstance().getAssets(), "icomoon.ttf");
            String vegIcon=MvvmApp.getInstance().getResources().getString(R.string.icon_veg);
            SpannableStringBuilder SS = new SpannableStringBuilder(order.getProductName() +" "+ vegIcon+" ");
            SS.setSpan(new CustomTypefaceSpan("", font), 0, order.getProductName().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            SS.setSpan(new CustomTypefaceSpan("", font2), order.getProductName().length()+ 1, order.getProductName().length()+ 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            SS.setSpan(new RelativeSizeSpan(0.6f), order.getProductName().length()+ 1, order.getProductName().length()+ 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (order.getVegtype().equals("0")){
                SS.setSpan(new ForegroundColorSpan(MvvmApp.getInstance().getResources().getColor(R.color.green)), order.getProductName().length()+ 1,  order.getProductName().length()+ 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }else {
                SS.setSpan(new ForegroundColorSpan(MvvmApp.getInstance().getResources().getColor(R.color.red)), order.getProductName().length()+ 1,  order.getProductName().length()+ 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mBinding.name.setText(SS);
            
        }

        @Override
        public void onItemClick(OrdersHistoryActivityResponse.Result.Item mOrderList) {
            Log.e("error", String.valueOf(mOrderList));
           /* if (mOrderList.getOrderstatus().equals("6"))
            {
                mOrdersAdapterListener.listItem(mOrderList);
            }else {
                mOrdersAdapterListener.listItem(mOrderList);
            }*/
        }

    }

}
