package com.tovo.eat.ui.home.infinityadapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemCollectionKitchensBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.infinityviewmodels.InfinityCollectionKitchenItemViewModel;

import java.util.List;

public class InfinityCollectionKitchensAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<KitchenResponse.CollectionDetail> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;


    public InfinityCollectionKitchensAdapter(List<KitchenResponse.CollectionDetail> item_list) {
       /* for (int i = 0; i < item_list.size(); i++) {

            if (!item_list.get(i).getCollectionstatus()) {
                item_list.remove(i);
            }

        }*/
        this.item_list = item_list;


    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemCollectionKitchensBinding blogViewBinding = ListItemCollectionKitchensBinding.inflate(LayoutInflater.from(parent.getContext()),
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


    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }


    public interface LiveProductsAdapterListener {

        void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection);

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

    public class LiveProductsViewHolder extends BaseViewHolder implements InfinityCollectionKitchenItemViewModel.KitchenItemViewModelListener {
        ListItemCollectionKitchensBinding mListItemLiveProductsBinding;
        InfinityCollectionKitchenItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemCollectionKitchensBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenResponse.CollectionDetail blog = item_list.get(position);

            mLiveProductsItemViewModel = new InfinityCollectionKitchenItemViewModel(this, blog);
            mListItemLiveProductsBinding.setKitchenItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(KitchenResponse.CollectionDetail mKitchenList) {
            mLiveProductsAdapterListener.infinityCollectionDetailItemClick(mKitchenList);
        }

        @Override
        public void addFavourites(Long id, String fav) {

        }

        @Override
        public void removeFavourites(Integer favId) {

        }
    }

}
