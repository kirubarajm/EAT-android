package com.tovo.eat.ui.home.homemenu.story;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemStoriesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;

import java.util.List;

public class StoriesCardAdapter extends RecyclerView.Adapter<BaseViewHolder> {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<StoriesResponse.Result> item_list;
    private StoriesAdapterListener mStoriesAdapterListener;


    public StoriesCardAdapter(List<StoriesResponse.Result> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemStoriesBinding blogViewBinding = ListItemStoriesBinding.inflate(LayoutInflater.from(parent.getContext()),
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

        return item_list.size();

        //return Integer.MAX_VALUE;


        /*if (item_list != null && item_list.size() > 0) {
            return item_list.size();
        } else {
            return 1;
        }*/
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

    public void addItems(List<StoriesResponse.Result> blogList, Context context) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(StoriesAdapterListener listener) {
        this.mStoriesAdapterListener = listener;
    }

    public interface StoriesAdapterListener {

        void onItemClickData(StoriesResponse.Result result, int pos);

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

    public class LiveProductsViewHolder extends BaseViewHolder implements StoriesItemViewModel.StoriesItemViewModelListener {
        ListItemStoriesBinding mListItemLiveProductsBinding;
        StoriesItemViewModel mStoriesItemViewModel;

        int pos;

        public LiveProductsViewHolder(ListItemStoriesBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;

        }

        @Override
        public void onBind(int position) {
            pos = position;
            if (item_list.isEmpty()) return;
            final StoriesResponse.Result blog = item_list.get(position);
            mStoriesItemViewModel = new StoriesItemViewModel(this, blog);
            mListItemLiveProductsBinding.setStoriesItemViewModel(mStoriesItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(StoriesResponse.Result blog) {
            mStoriesAdapterListener.onItemClickData(blog, pos);
        }
    }

}
