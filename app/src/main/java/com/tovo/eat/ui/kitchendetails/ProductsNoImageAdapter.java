package com.tovo.eat.ui.kitchendetails;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemDishNoimageBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.utilities.CustomTypefaceSpan;
import com.tovo.eat.utilities.MvvmApp;

import java.util.ArrayList;
import java.util.List;

public class ProductsNoImageAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public boolean serviceablekitchen = true;
    private List<KitchenDetailsResponse.ProductList> item_list = new ArrayList<>();
    private LiveProductsAdapterListener mLiveProductsAdapterListener;
    DataManager dataManager;

    public ProductsNoImageAdapter(List<KitchenDetailsResponse.ProductList> result, DataManager dataManager) {
        this.item_list = new ArrayList<>();
        this.item_list = result;
        this.dataManager=dataManager;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemDishNoimageBinding blogViewBinding = ListItemDishNoimageBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LiveProductsViewHolder(blogViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
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

    public void addItems(List<KitchenDetailsResponse.ProductList> productlists) {
        item_list.addAll(productlists);
        notifyDataSetChanged();
    }


    public void serviceable(boolean status) {
        this.serviceablekitchen = status;

    }


    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void dishRefresh();
        void productNotAvailable(int quantity, String productname);
        void showToast(String msg);
        void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price);
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

    public class LiveProductsViewHolder extends BaseViewHolder implements ProductNoImageItemViewModel.DishItemViewModelListener {
        ListItemDishNoimageBinding mListItemLiveProductsBinding;
        ProductNoImageItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemDishNoimageBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;

        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenDetailsResponse.ProductList blog = item_list.get(position);

            mLiveProductsItemViewModel = new ProductNoImageItemViewModel(this, blog,dataManager);
            mListItemLiveProductsBinding.setProductNoImageItemViewModel(mLiveProductsItemViewModel);

            mListItemLiveProductsBinding.executePendingBindings();

           /* Typeface font = Typeface.createFromAsset(MvvmApp.getInstance().getAssets(), "Poppins-Medium.otf");
            Typeface font2 = Typeface.createFromAsset(MvvmApp.getInstance().getAssets(), "icomoon.ttf");
            String vegIcon = MvvmApp.getInstance().getResources().getString(R.string.icon_veg);
            SpannableStringBuilder SS = new SpannableStringBuilder(blog.getProductName() + " " + vegIcon);
            SS.setSpan(new CustomTypefaceSpan("", font), 0, blog.getProductName().length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            SS.setSpan(new CustomTypefaceSpan("", font2), blog.getProductName().length() + 1, blog.getProductName().length() + 2, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

            SS.setSpan(new RelativeSizeSpan(0.6f), blog.getProductName().length() + 1, blog.getProductName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            if (blog.getVegtype().equals("0")) {
                SS.setSpan(new ForegroundColorSpan(MvvmApp.getInstance().getResources().getColor(R.color.green)), blog.getProductName().length() + 1, blog.getProductName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                SS.setSpan(new ForegroundColorSpan(MvvmApp.getInstance().getResources().getColor(R.color.red)), blog.getProductName().length() + 1, blog.getProductName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mListItemLiveProductsBinding.name.setText(SS);*/




        }

        @Override
        public void productNotAvailable(int quantity, String productname) {
            mLiveProductsAdapterListener.productNotAvailable(quantity, productname);
        }

        @Override
        public void refresh() {
            mLiveProductsAdapterListener.dishRefresh();
        }

        @Override
        public void showToast(String msg) {
            mLiveProductsAdapterListener.showToast(msg);
        }

        @Override
        public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {
            mLiveProductsAdapterListener.otherKitchenDish(makeitId, productId, quantity, price);
        }

    }

}
