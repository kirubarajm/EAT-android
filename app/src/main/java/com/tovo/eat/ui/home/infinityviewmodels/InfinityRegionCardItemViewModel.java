package com.tovo.eat.ui.home.infinityviewmodels;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class InfinityRegionCardItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subTitle = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final InfinityRegionItemViewModelListener mListener;

    KitchenResponse.Region region;


    public InfinityRegionCardItemViewModel(InfinityRegionItemViewModelListener mListener, KitchenResponse.Region region) {
        this.mListener = mListener;
        this.region = region;
        imageUrl.set(region.getCollectionImage());
        content.set(region.getSliderContent());

    }

    public void onItemClick() {
        if (region.isClickable())
            mListener.onItemClick(region);
    }

    public interface InfinityRegionItemViewModelListener {
        void onItemClick(KitchenResponse.Region region);
    }

}
