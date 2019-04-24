package com.tovo.eat.ui.home.kitchendish;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemKitchenDishEmptyBinding;
import com.tovo.eat.databinding.ListItemKitchenDishesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;

import java.util.List;

public class KitchenDishAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    List<KitchenDishResponse.Result> response;
    private List<KitchenDishResponse.Productlist> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;
    private DataManager dataManager;


    public KitchenDishAdapter(List<KitchenDishResponse.Productlist> item_list) {
        this.item_list = item_list;
    }

    public KitchenDishAdapter(List<KitchenDishResponse.Productlist> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemKitchenDishesBinding blogViewBinding = ListItemKitchenDishesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ListItemKitchenDishEmptyBinding blogViewBinding1 = ListItemKitchenDishEmptyBinding.inflate(LayoutInflater.from(parent.getContext()),
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

    public void addItems(List<KitchenDishResponse.Productlist> blogList, List<KitchenDishResponse.Result> response) {
        item_list.addAll(blogList);

        this.response = response;

        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(KitchenDishResponse.Result blogUrl);

        void sendCart();

        void dishRefresh();

        void  addDishFavourite(Integer dishId, String fav);

        void productNotAvailable();

        void  removeDishFavourite(Integer favId);

    }


    public class EmptyViewHolder extends BaseViewHolder {

        private final ListItemKitchenDishEmptyBinding mBinding;

        public EmptyViewHolder(ListItemKitchenDishEmptyBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {

        }

    }

    public class LiveProductsViewHolder extends BaseViewHolder implements KitchenDishItemViewModel.DishItemViewModelListener {
        ListItemKitchenDishesBinding mListItemLiveProductsBinding;
        KitchenDishItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemKitchenDishesBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenDishResponse.Productlist blog = item_list.get(position);
            final KitchenDishResponse.Result result = response.get(0);

            mLiveProductsItemViewModel = new KitchenDishItemViewModel(this, blog, result);
            mListItemLiveProductsBinding.setKitchenDishItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();
        }

        @Override
        public void onItemClick() {

        }

        @Override
        public String addQuantity() {
            // mLiveProductsItemViewModel.isAddClicked.set(true);

            return dataManager.getCartDetails();

        }

        @Override
        public void subQuantity() {

        }

        @Override
        public void enableAdd() {

        }

        @Override
        public void saveCart(String jsonCartDetails) {

            dataManager.setCartDetails(jsonCartDetails);

            mLiveProductsAdapterListener.sendCart();

        }

        @Override
        public void checkAllCart() {
            mLiveProductsAdapterListener.sendCart();
        }

        @Override
        public void addFavourites(Integer dishId, String fav) {
            mLiveProductsAdapterListener.addDishFavourite(dishId,fav);
        }

        @Override
        public void removeFavourites(Integer favId) {
            mLiveProductsAdapterListener.removeDishFavourite(favId);
        }

        @Override
        public void productNotAvailable() {
            mLiveProductsAdapterListener.productNotAvailable();
        }

        @Override
        public void refresh() {
            mLiveProductsAdapterListener.dishRefresh();
        }

        @Override
        public Integer getEatId() {
            return dataManager.getCurrentUserId();
        }
    }

}
