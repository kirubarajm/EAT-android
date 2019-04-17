package com.tovo.eat.ui.home.homemenu.dish;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemDishesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;

import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    private List<DishResponse.Result> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    private DataManager dataManager;


    public DishAdapter(List<DishResponse.Result> item_list) {
        this.item_list = item_list;
    }

    public DishAdapter(List<DishResponse.Result> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        switch (i) {
            case VIEW_TYPE_NORMAL:
                ListItemDishesBinding blogViewBinding = ListItemDishesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ListItemDishesBinding blogViewBinding1 = ListItemDishesBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new LiveProductsViewHolder(blogViewBinding1);
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

    public void addItems(List<DishResponse.Result> blogList) {
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    public interface LiveProductsAdapterListener {

        void onItemClickData(DishResponse.Result blogUrl);


        void sendCart();


        void dishRefresh();
        void productNotAvailable();


        void  addDishFavourite(Integer dishId, String fav);
        void  removeDishFavourite(Integer favId);



    }

    public class LiveProductsViewHolder extends BaseViewHolder implements DishItemViewModel.DishItemViewModelListener {
        ListItemDishesBinding mListItemLiveProductsBinding;
        DishItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemDishesBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final DishResponse.Result blog = item_list.get(position);
            mLiveProductsItemViewModel = new DishItemViewModel(this, blog);
            mListItemLiveProductsBinding.setDishItemViewModel(mLiveProductsItemViewModel);

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
        public void refresh() {
            mLiveProductsAdapterListener.dishRefresh();
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
        public Integer getEatId() {
            return 1;
        }
    }

}
