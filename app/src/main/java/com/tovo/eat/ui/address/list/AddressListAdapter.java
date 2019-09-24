package com.tovo.eat.ui.address.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemAddressBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<AddressListResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public AddressListAdapter(List<AddressListResponse.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemAddressBinding blogViewBinding = ListItemAddressBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<AddressListResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(AddressListResponse.Result blogUrl);

        void editAddressClick(AddressListResponse.Result blogUrl);

        void deleteAddress(AddressListResponse.Result addressList);
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

    public class LiveProductsViewHolder extends BaseViewHolder implements AddressListItemViewModel.addressListItemViewModelListener {

        ListItemAddressBinding mListItemLiveProductsBinding;
        AddressListItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemAddressBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final AddressListResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new AddressListItemViewModel(this, blog);
            mListItemLiveProductsBinding.setAddressListItemViewModel(mLiveProductsItemViewModel);

            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(AddressListResponse.Result result) {


            mLiveProductsAdapterListener.onItemClickData(result);

        }

        @Override
        public void editAddress(AddressListResponse.Result result) {
            mLiveProductsAdapterListener.editAddressClick(result);
        }

        @Override
        public void deleteAddress(AddressListResponse.Result addressList) {


            mLiveProductsAdapterListener.deleteAddress(addressList);


        }

        @Override
        public void homeAddressAdded() {
            dataManager.setHomeAddressAdded(true);
        }

        @Override
        public void officeAddressAdded() {
            dataManager.setOfficeAddressAdded(true);
        }
    }
}
