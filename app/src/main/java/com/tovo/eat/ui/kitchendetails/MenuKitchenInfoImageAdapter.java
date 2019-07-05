package com.tovo.eat.ui.kitchendetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.tovo.eat.R;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemKitchenCommonImagesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

import java.util.List;

public class MenuKitchenInfoImageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<KitchenDishResponse.Kitchenmenuimage> item_list;
    MenuProductsAdapterListener mMenuProductsAdapterListener;

    public MenuKitchenInfoImageAdapter(List<KitchenDishResponse.Kitchenmenuimage> item_list) {
        this.item_list = item_list;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemKitchenCommonImagesBinding blogViewBinding = ListItemKitchenCommonImagesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new MenuProductsViewHolder(blogViewBinding);
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

    public class EmptyViewHolder extends BaseViewHolder {
        private final ListItemEmptyBinding mBinding;
        EmptyItemViewModel emptyItemViewModel;

        public EmptyViewHolder(ListItemEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
        @Override
        public void onBind(int position) {
            emptyItemViewModel = new EmptyItemViewModel("No menu products found");
            mBinding.setEmptyItemViewModel(emptyItemViewModel);
        }
    }

    public class MenuProductsViewHolder extends BaseViewHolder implements
            KitchenCommonItemViewModel.KitchenCommonImageItemViewModelListener {

        private ListItemKitchenCommonImagesBinding mBinding;

        private KitchenCommonItemViewModel menuProductsItemViewModel;

        public MenuProductsViewHolder(ListItemKitchenCommonImagesBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public MenuProductsViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(int position) {
            if(item_list.isEmpty()) return;
            final KitchenDishResponse.Kitchenmenuimage blog = item_list.get(position);
            menuProductsItemViewModel = new KitchenCommonItemViewModel(this,blog);
            mBinding.setKitchenCommonImagesViewModel(menuProductsItemViewModel);
            mBinding.executePendingBindings();
        }

/*
        @Override
        public void onItemClick(KitchenDishResponse.Result mBlog,String days) {
            if (mBlog.getProductid() != null) {
                try {
                    mMenuProductsAdapterListener.onItemClicked(mBlog,days);
                } catch (Exception e) {
                    Log.e("url error","");
                }
            }else {
                mMenuProductsAdapterListener.onItemClicked(mBlog,days);
            }
        }
*/


    }

    public void clearItems() {
        item_list.clear();
    }


    public void addItems(List<KitchenDishResponse.Kitchenmenuimage> blogList) {
        item_list.addAll(blogList);


        notifyDataSetChanged();
    }

    public void setListener(MenuProductsAdapterListener listener) {
        this.mMenuProductsAdapterListener = listener;
    }

    public interface MenuProductsAdapterListener {

        void onItemClicked(KitchenDishResponse.Kitchenmenuimage mBlog, String days);
    }

}
