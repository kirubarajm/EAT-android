package com.tovo.eat.ui.cart.refund;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemAddressBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemRefundBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class RefundListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<RefundListResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;

    private static int sSelected = -1;

    public RefundListAdapter(List<RefundListResponse.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager=dataManager;
        sSelected = -1;
    }




    public class EmptyViewHolder extends BaseViewHolder  {

        private final ListItemEmptyBinding mBinding;


        EmptyItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptyItemViewModel("");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }


    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void selectedItemClear() {
        sSelected=-1;
        notifyDataSetChanged();
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemRefundBinding blogViewBinding = ListItemRefundBinding.inflate(LayoutInflater.from(parent.getContext()),
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
            return 1;
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

    public void addItems(List<RefundListResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(RefundListResponse.Result result,int selected);

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements RefundListItemViewModel.RefundListItemViewModelListener {

        ListItemRefundBinding mListItemLiveProductsBinding;
        RefundListItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemRefundBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final RefundListResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new RefundListItemViewModel(this, blog);
            mListItemLiveProductsBinding.setRefundListItemViewModel(mLiveProductsItemViewModel);


            if (sSelected == position) {
                mListItemLiveProductsBinding.op.setChecked(true);
            } else {
                mListItemLiveProductsBinding.op.setChecked(false);
            }



            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(RefundListResponse.Result result) {


            if (sSelected==getAdapterPosition()){
                sSelected=-1;
            }else {
                sSelected = getAdapterPosition();
            }

            mLiveProductsAdapterListener.onItemClickData(result,sSelected);
            notifyDataSetChanged();
        }

    }
}
