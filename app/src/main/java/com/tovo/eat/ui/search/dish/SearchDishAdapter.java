package com.tovo.eat.ui.search.dish;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemSerachDishBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class SearchDishAdapter extends RecyclerView.Adapter<BaseViewHolder> implements SearchKitchenDishAdapter.LiveProductsAdapterListener {


    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    DataManager dataManager;
    private List<KitchenDishResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    public SearchDishAdapter(List<KitchenDishResponse.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    public LiveProductsAdapterListener getListener() {
        return mLiveProductsAdapterListener;
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemSerachDishBinding blogViewBinding = ListItemSerachDishBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<KitchenDishResponse.Result> blogList, Context context) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    @Override
    public void onItemClickData(KitchenDishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {
        mLiveProductsAdapterListener.sendCart();
    }

    @Override
    public void dishRefresh() {

    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {

    }

    @Override
    public void productNotAvailable() {
        mLiveProductsAdapterListener.productNotAvailable();
    }

    @Override
    public void removeDishFavourite(Integer favId) {

    }

    @Override
    public void showToast(String msg) {

        mLiveProductsAdapterListener.showToast(msg);

    }

    @Override
    public void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price) {
        mLiveProductsAdapterListener.otherKitchenDish(makeitId, productId, quantity, price);
    }


    public interface LiveProductsAdapterListener {

        void onItemClickData(Integer kitchenId);

        void showMore(Integer kitchenId);

        void onItemClickData(DishResponse.Result blogUrl);

        void sendCart();

        void dishRefresh();

        void productNotAvailable();

        void addDishFavourite(Integer dishId, String fav);

        void removeDishFavourite(Integer favId);

        void showToast(String msg);

        void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price);


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

    public class LiveProductsViewHolder extends BaseViewHolder implements SearchDishItemViewModel.RegionItemViewModelListener {
        ListItemSerachDishBinding mListItemLiveProductsBinding;
        SearchDishItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemSerachDishBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenDishResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new SearchDishItemViewModel(this, blog);
            mListItemLiveProductsBinding.setSearchDishItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();


            if (!blog.isServiceableStatus()) {

                mListItemLiveProductsBinding.kitchenTile.setAlpha(1);
                mListItemLiveProductsBinding.kitchenTile.setBackgroundColor(MvvmApp.getInstance().getResources().getColor(R.color.gray));
                mListItemLiveProductsBinding.kitchenName.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));
                mListItemLiveProductsBinding.region.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));

                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                mListItemLiveProductsBinding.image.setColorFilter(filter);
                mListItemLiveProductsBinding.service1.setVisibility(View.VISIBLE);
                //  mListItemLiveProductsBinding.rating.setVisibility(View.GONE);

            }


            if (blog.getProductlist() != null && blog.getProductlist().size() > 0) {


                mListItemLiveProductsBinding.recyclerviewKitchens.setVisibility(View.VISIBLE);
                mListItemLiveProductsBinding.viewMenu.setVisibility(View.VISIBLE);

                LinearLayoutManager mLayoutManager
                        = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerviewKitchens.getContext(), LinearLayoutManager.VERTICAL, false);

                mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                SearchKitchenDishAdapter regionKitchenAdapter = new SearchKitchenDishAdapter(item_list.get(position).getProductlist(), item_list.get(position), dataManager);
                mListItemLiveProductsBinding.recyclerviewKitchens.setLayoutManager(mLayoutManager);
                mListItemLiveProductsBinding.recyclerviewKitchens.setAdapter(regionKitchenAdapter);
                regionKitchenAdapter.serviceable(blog.isServiceableStatus());

                regionKitchenAdapter.setListener(SearchDishAdapter.this);
            } else {
                mListItemLiveProductsBinding.recyclerviewKitchens.setVisibility(View.GONE);
                mListItemLiveProductsBinding.viewMenu.setVisibility(View.GONE);
            }


        }

        @Override
        public void onItemClick(Integer id) {
            mLiveProductsAdapterListener.onItemClickData(id);
        }

        @Override
        public void showMore(Integer id) {
            mLiveProductsAdapterListener.showMore(id);
        }


    }

}
