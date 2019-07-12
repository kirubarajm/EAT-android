package com.tovo.eat.ui.kitchendetails;


import android.databinding.ObservableField;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;


public class SpecialitiesItemViewModel {

    public final ObservableField<String> image_url = new ObservableField<>();
    public final SpecialitiesItemViewModelListener mListener;
    KitchenDishResponse.Specialitem foodBadges;

    public SpecialitiesItemViewModel(SpecialitiesItemViewModelListener mListener, KitchenDishResponse.Specialitem foodBadges) {
        this.mListener = mListener;
        this.foodBadges=foodBadges;
        image_url.set(foodBadges.getImgUrl());
    }

    public void onItemClick() {
        mListener.onItemClick(foodBadges.getImgUrl());
    }

    public interface SpecialitiesItemViewModelListener {

        void onItemClick(String url);
    }

}
