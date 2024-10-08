package com.tovo.eat.ui.search.dish;

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
import com.tovo.eat.databinding.ListItemEmptyBinding;
import com.tovo.eat.databinding.ListItemKitchenDishesBinding;
import com.tovo.eat.ui.base.BaseViewHolder;
import com.tovo.eat.ui.home.homemenu.kitchen.EmptyItemViewModel;
import com.tovo.eat.ui.home.kitchendish.KitchenDishItemViewModel;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.utilities.CustomTypefaceSpan;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class SearchKitchenDishAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 1;
    private static final int VIEW_TYPE_EMPTY = 0;
    public boolean serviceablekitchen = true;
    KitchenDishResponse.Result response;
    private List<KitchenDishResponse.Productlist> item_list;
    private LiveProductsAdapterListener mLiveProductsAdapterListener;
    private DataManager dataManager;
    String strScreenName="";

    public SearchKitchenDishAdapter(List<KitchenDishResponse.Productlist> item_list, KitchenDishResponse.Result response, DataManager dataManager) {
        this.item_list = item_list;
        this.response = response;
        this.dataManager = dataManager;
    }


    public SearchKitchenDishAdapter(List<KitchenDishResponse.Productlist> item_list, DataManager dataManager) {
        this.item_list = item_list;
        this.dataManager = dataManager;
    }

    public void serviceable(boolean status) {
        this.serviceablekitchen = status;

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

            /*if (item_list.size()>2){
                return 3;
            }else {
                return item_list.size();
            }*/

        } else {
            return 0;
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

    public void setScreenName(String strScreenName) {
       this.strScreenName = strScreenName;
    }


    public void setListener(LiveProductsAdapterListener listener,String screenName) {
        this.mLiveProductsAdapterListener = listener;
        this.strScreenName = screenName;
    }


    public interface LiveProductsAdapterListener {

        void onItemClickData(KitchenDishResponse.Result blogUrl);

        void sendCart();

        void dishRefresh();

        void addDishFavourite(Integer dishId, String fav);

        void productNotAvailable();

        void removeDishFavourite(Integer favId);

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

            mLiveProductsItemViewModel = new KitchenDishItemViewModel(this, blog, response, serviceablekitchen,strScreenName);
            mListItemLiveProductsBinding.setKitchenDishItemViewModel(mLiveProductsItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mListItemLiveProductsBinding.executePendingBindings();


            Typeface font = Typeface.createFromAsset(MvvmApp.getInstance().getAssets(), "Poppins-Medium.otf");
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
            mListItemLiveProductsBinding.name.setText(SS);


            if (!serviceablekitchen) {

               /* mListItemLiveProductsBinding.addDish.setVisibility(View.GONE);



                mListItemLiveProductsBinding.content.setAlpha(1);
                mListItemLiveProductsBinding.content.setBackgroundColor(MvvmApp.getInstance().getResources().getColor(R.color.gray));
                mListItemLiveProductsBinding.inr.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));
                mListItemLiveProductsBinding.amount.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));
                mListItemLiveProductsBinding.name.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));


                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);*/
                //  mListItemLiveProductsBinding.image.setColorFilter(filter);


            } else if (blog.getNextAvailable()) {

               /* mListItemLiveProductsBinding.addDish.setVisibility(View.GONE);

                mListItemLiveProductsBinding.content.setAlpha(1);
                mListItemLiveProductsBinding.content.setBackgroundColor(MvvmApp.getInstance().getResources().getColor(R.color.gray));
                mListItemLiveProductsBinding.inr.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));
                mListItemLiveProductsBinding.amount.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));
                mListItemLiveProductsBinding.name.setTextColor(MvvmApp.getInstance().getResources().getColor(R.color.medium_gray));


                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);

                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);*/
            } else {
                mListItemLiveProductsBinding.addDish.setVisibility(View.VISIBLE);
            }


        }


        @Override
        public void onItemClick(KitchenDishResponse.Result blogUrl) {

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
            mLiveProductsAdapterListener.addDishFavourite(dishId, fav);
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
        public Long getEatId() {
            return dataManager.getCurrentUserId();
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
