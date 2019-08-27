package com.tovo.eat.ui.search;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemDishesBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemEmptySearchBinding;
import com.tovo.eat.databinding.ListItemSearchBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<SearchResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public SearchAdapter(List<SearchResponse.Result> item_list) {
        this.item_list = item_list;
    }

    public SearchAdapter(List<SearchResponse.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemSearchBinding blogViewBinding = ListItemSearchBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:


            default:
                ListItemEmptySearchBinding blogViewBinding1 = ListItemEmptySearchBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<SearchResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(SearchResponse.Result result);

    }


    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemEmptySearchBinding mBinding;


        EmptySearchItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptySearchBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptySearchItemViewModel("No search results found");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements SearchItemViewModel.SearchItemViewModelListener {
        ListItemSearchBinding mListItemLiveProductsBinding;
        SearchItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemSearchBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }


        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final SearchResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new SearchItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSearchItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();


        }

        @Override
        public void onItemClick(SearchResponse.Result result) {
            mLiveProductsAdapterListener.onItemClickData(result);
        }

    }

}
