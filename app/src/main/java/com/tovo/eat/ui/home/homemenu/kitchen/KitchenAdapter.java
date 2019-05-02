package com.tovo.eat.ui.home.homemenu.kitchen;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemKitchensBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;

import java.util.List;

public class KitchenAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<KitchenResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;


    public KitchenAdapter(List<KitchenResponse.Result> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemKitchensBinding blogViewBinding = ListItemKitchensBinding.inflate(LayoutInflater.from(parent.getContext()),
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
    public class EmptyViewHolder extends BaseViewHolder  {

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

    public class LiveProductsViewHolder extends BaseViewHolder implements KitchenItemViewModel.KitchenItemViewModelListener {
        ListItemKitchensBinding mListItemLiveProductsBinding;
       KitchenItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemKitchensBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new KitchenItemViewModel(this,blog);
            mListItemLiveProductsBinding.setKitchenItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(Integer id) {

            mLiveProductsAdapterListener.onItemClickData(id);


        }

        @Override
        public void addFavourites(Integer id,String fav) {
            mLiveProductsAdapterListener.addFav(id,fav);
        }

        @Override
        public void removeFavourites(Integer favId) {

            mLiveProductsAdapterListener.removeDishFavourite(favId);
        }
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<KitchenResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(Integer kitchenId);


        void  removeDishFavourite(Integer favId);

        void addFav(Integer id,String fav);
    }

}
