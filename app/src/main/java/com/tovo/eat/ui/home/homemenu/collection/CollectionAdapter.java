package com.tovo.eat.ui.home.homemenu.collection;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tovo.eat.databinding.ListItemCollectionCardBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<KitchenResponse.Collection> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;


    public CollectionAdapter(List<KitchenResponse.Collection> item_listall) {
        for (int i = 0; i < item_listall.size(); i++) {
            if (!item_listall.get(i).getCollectionstatus()) {
                item_listall.remove(i);
                notifyDataSetChanged();
            }
        }
        this.item_list = item_listall;



    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemCollectionCardBinding blogViewBinding = ListItemCollectionCardBinding.inflate(LayoutInflater.from(parent.getContext()),
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

        void collectionItemClick(KitchenResponse.Collection collection);

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
        ListItemCollectionCardBinding mListItemLiveProductsBinding;
        CollectionCardItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemCollectionCardBinding binding) {
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


            /*DisplayMetrics displayMetrics = MvvmApp.getInstance().getResources().getDisplayMetrics();
            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;




            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Math.round(screenWidthDp/2),Math.round(screenWidthDp/2));
            mListItemLiveProductsBinding.im.setLayoutParams(params);*/



        }


        @Override
        public void onItemClick(KitchenResponse.Collection collection) {
            mLiveProductsAdapterListener.collectionItemClick(collection);
        }
    }

}
