package com.tovo.eat.ui.kitchendetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemProductsBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenItemViewModel;

import java.util.List;

public class KitchenProductsAdapter extends RecyclerView.Adapter<BaseViewHolder> implements ProductsNoImageAdapter.LiveProductsAdapterListener, ProductsImageAdapter.LiveProductsAdapterListener {

   /* private static final int VIEW_TYPE_WITH_IMAGE = 0;
    private static final int VIEW_TYPE_WITHOUT_IMAGE = 1;*/

    public boolean serviceablekitchen = true;
    Context context;
    DataManager dataManager;
    private List<KitchenDetailsResponse.Product> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;

    public KitchenProductsAdapter(List<KitchenDetailsResponse.Product> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        ListItemProductsBinding productsBinding = ListItemProductsBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new LiveProductsViewHolder(productsBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public void clearItems() {
        item_list.clear();
    }

    public void addItems(List<KitchenDetailsResponse.Product> blogList, Context context) {
        this.context = context;
        item_list.addAll(blogList);
        notifyDataSetChanged();
    }

    public void setListener(LiveProductsAdapterListener listener) {
        this.mLiveProductsAdapterListener = listener;
    }

    @Override
    public void dishRefresh() {
        mLiveProductsAdapterListener.dishRefresh();
    }

    @Override
    public void productNotAvailable(int quantity, String productname) {
        mLiveProductsAdapterListener.productNotAvailable(quantity, productname);
    }

    @Override
    public void showToast(String msg) {
        mLiveProductsAdapterListener.showToast(msg);
    }

    @Override
    public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {
        mLiveProductsAdapterListener.otherKitchenDish(makeitId, productId, quantity, price);
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

    public class LiveProductsViewHolder extends BaseViewHolder {
        ListItemProductsBinding mListItemLiveProductsBinding;
        KitchenItemViewModel mLiveProductsItemViewModel;

        public LiveProductsViewHolder(ListItemProductsBinding binding) {
            super(binding.getRoot());
            this.mListItemLiveProductsBinding = binding;
        }

        @Override
        public void onBind(int position) {
            if (item_list.isEmpty()) return;
            final KitchenDetailsResponse.Product blog = item_list.get(position);
            //   mLiveProductsItemViewModel = new KitchenItemViewModel(this, blog);
            //   mListItemLiveProductsBinding.setKitchenItemViewModel(mLiveProductsItemViewModel);
            mListItemLiveProductsBinding.executePendingBindings();



            if (blog.getProductList()!=null&&blog.getProductList().size()>0) {

                mListItemLiveProductsBinding.title.setText(blog.getTitle());
                mListItemLiveProductsBinding.title.setVisibility(View.VISIBLE);
                mListItemLiveProductsBinding.separator.setVisibility(View.VISIBLE);


                if (blog.getType() == 1) {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mListItemLiveProductsBinding.recyclerProducts.getContext(), 2);
                    //mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    ProductsImageAdapter productsImageAdapter = new ProductsImageAdapter(item_list.get(position).getProductList(), dataManager);
                    mListItemLiveProductsBinding.recyclerProducts.setLayoutManager(gridLayoutManager);
                    mListItemLiveProductsBinding.recyclerProducts.setAdapter(productsImageAdapter);
                    productsImageAdapter.setListener(KitchenProductsAdapter.this);

                } else {
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(mListItemLiveProductsBinding.recyclerProducts.getContext(), LinearLayoutManager.VERTICAL, false);
                    mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    ProductsNoImageAdapter productsImageAdapter = new ProductsNoImageAdapter(item_list.get(position).getProductList(), dataManager);
                    mListItemLiveProductsBinding.recyclerProducts.setLayoutManager(mLayoutManager);
                    mListItemLiveProductsBinding.recyclerProducts.setAdapter(productsImageAdapter);
                    productsImageAdapter.setListener(KitchenProductsAdapter.this);

                }
            }else {
                mListItemLiveProductsBinding.title.setVisibility(View.GONE);
                mListItemLiveProductsBinding.separator.setVisibility(View.GONE);
            }

        }


    }


}
