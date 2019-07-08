package com.tovo.eat.ui.kitchendetails;


import android.databinding.ObservableField;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;



public class FoodBadgesItemViewModel {

    public final ObservableField<String> image_url = new ObservableField<>();
    public final DishItemViewModelListener mListener;

    public FoodBadgesItemViewModel(DishItemViewModelListener mListener, KitchenDishResponse.Foodbadge foodBadges) {
        this.mListener = mListener;
        image_url.set(foodBadges.getBadges());
    }

    public void onItemClick() {
        mListener.onItemClick();
    }

    public interface DishItemViewModelListener {

        void onItemClick();
    }

}
