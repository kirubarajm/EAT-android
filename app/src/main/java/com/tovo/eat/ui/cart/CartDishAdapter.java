package com.tovo.eat.ui.cart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemCartDishesBinding;
import com.tovo.eat.databinding.ListItemDishesBinding;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.utilities.CartRequestPojo;

import java.util.List;

public class CartDishAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<CartPageResponse.Item> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public CartDishAdapter(List<CartPageResponse.Item> item_list) {
        this.item_list = item_list;
    }

    public CartDishAdapter(List<CartPageResponse.Item> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
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

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemCartDishesBinding blogViewBinding = ListItemCartDishesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
              case VIEW_TYPE_EMPTY:
                  ListItemEmptyBinding blogViewBinding1 = ListItemEmptyBinding.inflate(LayoutInflater.from(parent.getContext()),
                          parent, false);
                  return new EmptyViewHolder(blogViewBinding1);
            default:
                return null;
                /*ListItemEmptyBinding blogViewBinding1 = ListItemEmptyBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(blogViewBinding1);*/
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

    public void addItems(List<CartPageResponse.Item> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(CartDishResponse.Result blogUrl);

        void sendCart();


        void saveToCart(String cart);

        String getCartData();
        void productNotAvailable();


        void reloadCart();
    }

    public class LiveProductsViewHolder extends BaseViewHolder implements CartDishItemViewModel.DishItemViewModelListener {

        ListItemCartDishesBinding mListItemLiveProductsBinding;
        CartDishItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemCartDishesBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final CartPageResponse.Item blog = item_list.get(position);
            mLiveProductsItemViewModel = new CartDishItemViewModel(this, blog);
            mListItemLiveProductsBinding.setCartDishItemViewModel(mLiveProductsItemViewModel);

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

        //  return dataManager.getCartDetails();
          return mLiveProductsAdapterListener.getCartData();

        }

        @Override
        public void subQuantity() {

        }

        @Override
        public void enableAdd() {

        }

        @Override
        public void saveCart(String jsonCartDetails) {

          //  dataManager.setCartDetails(jsonCartDetails);

            mLiveProductsAdapterListener.saveToCart(jsonCartDetails);

        }

        @Override
        public void checkAllCart() {
            mLiveProductsAdapterListener.sendCart();
        }

        @Override
        public void productNotAvailable() {
            mLiveProductsAdapterListener.productNotAvailable();
        }

        @Override
        public void reload() {
         mLiveProductsAdapterListener.reloadCart();
        }


    }

}
