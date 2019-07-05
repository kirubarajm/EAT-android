package com.tovo.eat.utilities;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

public class MainSliderAdapter extends SliderAdapter {

    KitchenDishResponse response;

    public MainSliderAdapter(KitchenDishResponse response) {
        this.response=response;
    }

    @Override
    public int getItemCount() {
        return response.getResult().get(0).getKitcheninfoimage().size();
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {

        viewHolder.bindImageSlide(String.valueOf(response.getResult().get(0).getKitcheninfoimage().get(position)));

    }
}