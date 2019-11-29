package com.tovo.eat.ui.home.homemenu.collection;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemCollectionCardBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemFilterCollectionsBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.homemenu.story.StoriesCardAdapter;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;

import java.util.List;

public class FilterCollectionAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<KitchenResponse.Collection> item_list;
    private FilterCollectionAdapterListener mFilterCollectionAdapterListener;


    public FilterCollectionAdapter(List<KitchenResponse.Collection> item_list) {
        for (int i = 0; i < item_list.size(); i++) {

            if (!item_list.get(i).isCollectionStatus()) {
                item_list.remove(i);
            }

        }
        this.item_list = item_list;


    }


    public void addItems(List<KitchenResponse.Collection> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }
    public void setListener(FilterCollectionAdapterListener listener) {
        this.mFilterCollectionAdapterListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemFilterCollectionsBinding blogViewBinding = ListItemFilterCollectionsBinding.inflate(LayoutInflater.from(parent.getContext()),
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


    public interface FilterCollectionAdapterListener {

        void filterCollectionItemClick(KitchenResponse.Collection collection);

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

    public class LiveProductsViewHolder extends BaseViewHolder implements CollectionCardItemViewModel.CollectionItemViewModelListener {
        ListItemFilterCollectionsBinding mListItemLiveProductsBinding;
        CollectionCardItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemFilterCollectionsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenResponse.Collection blog = item_list.get(position);

            mLiveProductsItemViewModel = new CollectionCardItemViewModel(this, blog);
            mListItemLiveProductsBinding.setCollectionCardItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(KitchenResponse.Collection collection) {
            mFilterCollectionAdapterListener.filterCollectionItemClick(collection);
        }
    }

}
