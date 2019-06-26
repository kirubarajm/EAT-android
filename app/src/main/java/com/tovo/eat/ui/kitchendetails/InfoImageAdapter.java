package com.tovo.eat.ui.kitchendetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemInfoGallaryBinding;
import com.tovo.eat.databinding.ListItemKitchenDishesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class InfoImageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<KitchenDetailsResponse.Result> response;
    private List<KitchenDetailsResponse.Productlist> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;
    private DataManager dataManager;


    public InfoImageAdapter(List<KitchenDetailsResponse.Productlist> item_list) {
        this.item_list = item_list;
    }


    public InfoImageAdapter(List<KitchenDetailsResponse.Productlist> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemInfoGallaryBinding blogViewBinding = ListItemInfoGallaryBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<KitchenDetailsResponse.Productlist> blogList, List<KitchenDetailsResponse.Result> response) {
        item_list.addAll(blogList);

        this.response = response;

        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(KitchenDetailsResponse.Result blogUrl);

        void sendCart();

        void dishRefresh();

        void  addDishFavourite(Integer dishId, String fav);

        void productNotAvailable();

        void  removeDishFavourite(Integer favId);
        void showToast(String msg);
        void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price);
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
            emptyItemViewModel = new EmptyItemViewModel("No results found for your selection");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements InfoImageItemViewModel.DishItemViewModelListener {
        ListItemInfoGallaryBinding mListItemLiveProductsBinding;
        InfoImageItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemInfoGallaryBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenDetailsResponse.Productlist blog = item_list.get(position);
            final KitchenDetailsResponse.Result result = response.get(0);

            mLiveProductsItemViewModel = new InfoImageItemViewModel(this, blog, result);
            mListItemLiveProductsBinding.setInfoImageItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }

        @Override
        public void onItemClick() {

        }



    }

}
