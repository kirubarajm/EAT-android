package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.region.RegionsResponse;


public class RegionCardItemViewModel {

    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> tagline = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final RegionItemViewModelListener mListener;
    public final RegionsResponse.Result mRegionList;


    int position;


    public RegionCardItemViewModel(RegionItemViewModelListener mListener, RegionsResponse.Result mRegionList, int position) {
        this.mListener = mListener;
        this.mRegionList = mRegionList;
        this.position = position;

        regionName.set(mRegionList.getRegionname());
        tagline.set(mRegionList.getTagline());
        title.set(mRegionList.getSliderTitle());
        content.set(mRegionList.getSliderContent());
        imageUrl.set(mRegionList.getRegionImage());

    }


    public void onItemClick() {
        mListener.onItemClick(mRegionList.getRegionid(), position);
    }


    public void showMore() {
        mListener.showMore(mRegionList.getRegionid());


    }

    public interface RegionItemViewModelListener {
        void onItemClick(Integer id, int position);

        void showMore(Integer id);

    }

}
