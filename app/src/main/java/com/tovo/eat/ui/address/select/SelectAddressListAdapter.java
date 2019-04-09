package com.tovo.eat.ui.address.select;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemAddressBinding;
import com.tovo.eat.databinding.ListItemAddressSelectBinding;
import com.tovo.eat.ui.base.BaseViewHolder;

import java.util.List;

public class SelectAddressListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<SelectAddressListResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public SelectAddressListAdapter(List<SelectAddressListResponse.Result> item_list) {
        this.item_list = item_list;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemAddressSelectBinding blogViewBinding = ListItemAddressSelectBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ListItemAddressSelectBinding blogViewBinding1 = ListItemAddressSelectBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding1);
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

    public void addItems(List<SelectAddressListResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(SelectAddressListResponse.Result blogUrl);

        void editAddressClick(SelectAddressListResponse.Result blogUrl);


    }

    public class LiveProductsViewHolder extends BaseViewHolder implements SelectAddressListItemViewModel.addressListItemViewModelListener {

        ListItemAddressSelectBinding mListItemLiveProductsBinding;
        SelectAddressListItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemAddressSelectBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final SelectAddressListResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new SelectAddressListItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSelectAddressListItemViewModel(mLiveProductsItemViewModel);

            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(SelectAddressListResponse.Result result) {


            mLiveProductsAdapterListener.onItemClickData(result);

        }

        @Override
        public void editAddress(SelectAddressListResponse.Result result) {
            mLiveProductsAdapterListener.editAddressClick(result);
        }
    }
}
