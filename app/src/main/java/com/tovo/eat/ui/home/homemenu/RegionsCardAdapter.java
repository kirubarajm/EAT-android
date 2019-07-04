package com.tovo.eat.ui.home.homemenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemRegionCardBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.region.RegionsResponse;

import java.util.List;

public class RegionsCardAdapter extends RecyclerView.Adapter<BaseViewHolder>  {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    ListItemRegionCardBinding mProductsBinding;
    private List<RegionsResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    public RegionsCardAdapter(List<RegionsResponse.Result> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemRegionCardBinding blogViewBinding = ListItemRegionCardBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<RegionsResponse.Result> blogList, Context context) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(Integer kitchenId, int position);

        void showMore(Integer regionId);

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

    public class LiveProductsViewHolder extends BaseViewHolder implements RegionCardItemViewModel.RegionItemViewModelListener {
        ListItemRegionCardBinding mListItemLiveProductsBinding;
        RegionCardItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemRegionCardBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
            mProductsBinding = binding;


        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final RegionsResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new RegionCardItemViewModel(this, blog, position);
            mListItemLiveProductsBinding.setRegionItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();


          /*  if (position ==getAdapterPosition()) {

                mListItemLiveProductsBinding.title.setVisibility(View.VISIBLE);

            } else {
                mListItemLiveProductsBinding.title.setVisibility(View.INVISIBLE);
            }*/


        }


        @Override
        public void onItemClick(Integer id, int position) {
            mLiveProductsAdapterListener.onItemClickData(id, position);


        }

        @Override
        public void showMore(Integer id) {
            mLiveProductsAdapterListener.showMore(id);
        }


    }

    public void viewRegion(){



    }


}
