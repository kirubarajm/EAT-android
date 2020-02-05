package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tovo.eat.databinding.ListItemOrderBillBinding;
import com.tovo.eat.ui.base.BaseViewHolder;

import java.util.List;

public class OrderBillListAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<OrdersHistoryActivityResponse.Cartdetail> item_list;
    private BilldetailsInfoListener mBilldetailsInfoListener;

    public OrderBillListAdapter(List<OrdersHistoryActivityResponse.Cartdetail> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemOrderBillBinding blogViewBinding = ListItemOrderBillBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LiveProductsViewHolder(blogViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {

        return item_list.size();
    }


    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<OrdersHistoryActivityResponse.Cartdetail> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(BilldetailsInfoListener listener) {
        this.mBilldetailsInfoListener = listener;
    }

    public interface BilldetailsInfoListener {

        void infoClick(OrdersHistoryActivityResponse.Cartdetail cartdetail, ImageView imageView);

    }

    public class LiveProductsViewHolder extends BaseViewHolder {

        ListItemOrderBillBinding mListItemLiveProductsBinding;
        OrderBillItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemOrderBillBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final OrdersHistoryActivityResponse.Cartdetail blog = item_list.get(position);
            mLiveProductsItemViewModel = new OrderBillItemViewModel(blog);
            mListItemLiveProductsBinding.setBillItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();


        }


    }
}
