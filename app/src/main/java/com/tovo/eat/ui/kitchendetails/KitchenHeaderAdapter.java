package com.tovo.eat.ui.kitchendetails;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemDishImageBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemKitchenHeaderBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.utilities.CustomTypefaceSpan;
import com.tovo.eat.utilities.MvvmApp;

import java.util.ArrayList;
import java.util.List;

public class KitchenHeaderAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public boolean serviceablekitchen = true;
    private List<KitchenDetailsResponse.KitchenPage> item_list;

    public KitchenHeaderAdapter(List<KitchenDetailsResponse.KitchenPage> result) {
        this.item_list=new ArrayList<>();
        this.item_list = result;
      
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ListItemKitchenHeaderBinding blogViewBinding = ListItemKitchenHeaderBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<KitchenDetailsResponse.KitchenPage> productlists) {
        item_list.addAll(productlists);
        notifyDataSetChanged();
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

    public class LiveProductsViewHolder extends BaseViewHolder{
        ListItemKitchenHeaderBinding mListItemLiveProductsBinding;
        KitchenImageItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemKitchenHeaderBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;

        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenDetailsResponse.KitchenPage blog = item_list.get(position);

            mLiveProductsItemViewModel = new KitchenImageItemViewModel(blog);
            mListItemLiveProductsBinding.setKitchenImageItemViewModel(mLiveProductsItemViewModel);

            mListItemLiveProductsBinding.executePendingBindings();


            int colorCode= Color.parseColor(blog.getHeaderColorCode());

            mListItemLiveProductsBinding.content.setTextColor(colorCode);




        }

    }

}
