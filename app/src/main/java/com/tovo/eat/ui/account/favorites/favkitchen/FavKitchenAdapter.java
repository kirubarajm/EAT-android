package com.tovo.eat.ui.account.favorites.favkitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.databinding.ListItemCollectionsBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemHomeOffersBinding;
import com.tovo.eat.databinding.ListItemKitchensBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.home.homemenu.OffersAdapter;
import com.tovo.eat.ui.home.homemenu.collection.CollectionAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenItemViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;

import java.util.List;

public class FavKitchenAdapter extends RecyclerView.Adapter<BaseViewHolder> implements CollectionAdapter.LiveProductsAdapterListener, OffersAdapter.LiveProductsAdapterListener {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private static final int VIEW_TYPE_COLLECTION = 2;
    private static final int VIEW_TYPE_COUPON = 3;
    Context context;
    private List<KitchenResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    public FavKitchenAdapter(List<KitchenResponse.Result> item_list) {
        this.item_list = item_list;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemKitchensBinding blogViewBinding = ListItemKitchensBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);

            case VIEW_TYPE_COLLECTION:
                ListItemCollectionsBinding collectionBinding = ListItemCollectionsBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new CollectionViewHolder(collectionBinding);

            case VIEW_TYPE_COUPON:
                ListItemHomeOffersBinding offersBinding = ListItemHomeOffersBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new OffernViewHolder(offersBinding);

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

            if (item_list.get(position).getCollection() != null && item_list.get(position).getCollection().size() > 0) {

                return VIEW_TYPE_COLLECTION;
            } else if (item_list.get(position).getCoupon() != null && item_list.get(position).getCoupon().size() > 0) {

                return VIEW_TYPE_COUPON;

            } else {
                return VIEW_TYPE_NORMAL;
            }
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<KitchenResponse.Result> blogList, Context context) {
        this.context = context;
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public void removeAt(int position) {
        item_list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, item_list.size());
    }


    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {
        mLiveProductsAdapterListener.collectionItemClick(collection);
    }

    @Override
    public void offerItemClick(KitchenResponse.Coupon offers) {
        mLiveProductsAdapterListener.offersItemClick(offers);
    }

    public interface LiveProductsAdapterListener {

        void collectionItemClick(KitchenResponse.Collection collection);

        void offersItemClick(KitchenResponse.Coupon offers);

        void onItemClickData(Long kitchenId);

        void animateView(View view);

        void empty();

        void removeDishFavourite(Integer favId);

        void addFav(Long id, String fav);
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
            mLiveProductsItemViewModel = new KitchenItemViewModel(this, blog);
            mListItemLiveProductsBinding.setKitchenItemViewModel(mLiveProductsItemViewModel);

            mLiveProductsAdapterListener.animateView(mListItemLiveProductsBinding.fav);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }


        @Override
        public void onItemClick(Long id) {
            mLiveProductsAdapterListener.onItemClickData(id);
        }

        @Override
        public void addFavourites(Long id, String fav) {
            mLiveProductsAdapterListener.addFav(id, fav);
        }

        @Override
        public void removeFavourites(Integer favId) {
            removeAt(getAdapterPosition());
            if (item_list.size() == 0) {
                mLiveProductsAdapterListener.empty();
            }
        }
    }

    public class CollectionViewHolder extends BaseViewHolder {
        ListItemCollectionsBinding mListItemLiveProductsBinding;

        public CollectionViewHolder(ListItemCollectionsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position).getCollection().isEmpty()) return;
            mListItemLiveProductsBinding.executePendingBindings();
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerCollection.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            CollectionAdapter collectionAdapter = new CollectionAdapter(item_list.get(position).getCollection());
            mListItemLiveProductsBinding.recyclerCollection.setLayoutManager(mLayoutManager);
            mListItemLiveProductsBinding.recyclerCollection.setAdapter(collectionAdapter);
            collectionAdapter.setListener(FavKitchenAdapter.this);
        }
    }
    public class OffernViewHolder extends BaseViewHolder {
        ListItemHomeOffersBinding mListItemLiveProductsBinding;

        public OffernViewHolder(ListItemHomeOffersBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.get(position).getCoupon().isEmpty()) return;
            mListItemLiveProductsBinding.executePendingBindings();
            LinearLayoutManager offerLayputManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerCollection.getContext(), LinearLayoutManager.HORIZONTAL, false);
            offerLayputManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            OffersAdapter offersAdapter = new OffersAdapter(item_list.get(position).getCoupon());
            mListItemLiveProductsBinding.recyclerCollection.setLayoutManager(offerLayputManager);
            mListItemLiveProductsBinding.recyclerCollection.setAdapter(offersAdapter);
            offersAdapter.setListener(FavKitchenAdapter.this);
        }

    }


}
