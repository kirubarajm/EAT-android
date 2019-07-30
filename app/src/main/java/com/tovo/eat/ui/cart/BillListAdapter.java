package com.tovo.eat.ui.cart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemCartBillBinding;
import com.tovo.eat.databinding.ListItemCouponBinding;
import com.tovo.eat.ui.base.BaseViewHolder;

import java.util.List;

public class BillListAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private List<CartPageResponse.Cartdetail> item_list;


    public BillListAdapter(List<CartPageResponse.Cartdetail> item_list) {
        this.item_list = item_list;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemCartBillBinding blogViewBinding = ListItemCartBillBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<CartPageResponse.Cartdetail> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }




    public class LiveProductsViewHolder extends BaseViewHolder {

        ListItemCartBillBinding mListItemLiveProductsBinding;
        BillItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemCartBillBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CartPageResponse.Cartdetail blog = item_list.get(position);
            mLiveProductsItemViewModel = new BillItemViewModel( blog);
            mListItemLiveProductsBinding.setBillItemViewModel(mLiveProductsItemViewModel);



            mListItemLiveProductsBinding.executePendingBindings();
        }

    }
}
