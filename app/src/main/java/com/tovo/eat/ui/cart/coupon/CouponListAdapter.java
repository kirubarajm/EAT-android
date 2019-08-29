package com.tovo.eat.ui.cart.coupon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemCouponBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.ArrayList;
import java.util.List;

public class CouponListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static int sSelected = -1;
    private List<CouponListResponse.Result> item_list = new ArrayList<>();
    private List<CouponListResponse.Result> temp_item_list = new ArrayList<>();
    private LiveProductsAdapterListener mLiveProductsAdapterListener;
    private DataManager dataManager;

    public CouponListAdapter(List<CouponListResponse.Result> item_list, DataManager dataManager) {

        for (int i = 0; i < item_list.size(); i++) {
            if (item_list.get(i).isCouponStatus()) {
                // item_list.remove(i);
                temp_item_list.add(item_list.get(i));
            }

            this.item_list = temp_item_list;
        }
        this.dataManager = dataManager;
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemCouponBinding blogViewBinding = ListItemCouponBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ListItemEmptyBinding blogViewBinding1 = ListItemEmptyBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(blogViewBinding1);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        if (item_list != null && item_list.size() > 0) {
            return item_list.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (item_list != null && !item_list.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<CouponListResponse.Result> blogList) {
        // item_list.addAll(blogList);
        for (int i = 0; i < blogList.size(); i++) {
            if (blogList.get(i).isCouponStatus()) {
                // item_list.remove(i);
                temp_item_list.add(blogList.get(i));
            }

            this.item_list = temp_item_list;
        }

        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(CouponListResponse.Result result, int selected);

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
            emptyItemViewModel = new EmptyItemViewModel("You have no address \n Please add your address");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements CouponListItemViewModel.RefundListItemViewModelListener {

        ListItemCouponBinding mListItemLiveProductsBinding;
        CouponListItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemCouponBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CouponListResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new CouponListItemViewModel(this, blog);
            mListItemLiveProductsBinding.setCouponListItemViewModel(mLiveProductsItemViewModel);




/*
            if (sSelected == position) {
                mListItemLiveProductsBinding.op.setChecked(true);
            } else {
                mListItemLiveProductsBinding.op.setChecked(false);
            }*/


            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(CouponListResponse.Result result) {


            if (sSelected == getAdapterPosition()) {
                sSelected = -1;
            } else {
                sSelected = getAdapterPosition();

            }

            mLiveProductsAdapterListener.onItemClickData(result, sSelected);
            notifyDataSetChanged();
        }

    }
}
