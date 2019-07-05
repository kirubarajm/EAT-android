package com.tovo.eat.ui.home.region.viewmore;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.region.RegionsResponse;


public class RegionsListItemViewModel {

    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> tagline = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final ObservableField<String> totalKitchens = new ObservableField<>();
    public final ObservableField<String> stateName = new ObservableField<>();
    public final ObservableField<String> famousfood = new ObservableField<>();
    public final ObservableField<String> identityImage = new ObservableField<>();
    public final ObservableField<String> spclFoodImage = new ObservableField<>();

    public final RegionItemViewModelListener mListener;
    public final RegionsResponse.Result mRegionList;


    int position;


    public RegionsListItemViewModel(RegionItemViewModelListener mListener, RegionsResponse.Result mRegionList) {
        this.mListener = mListener;
        this.mRegionList = mRegionList;

        regionName.set(mRegionList.getRegionname());
        tagline.set(mRegionList.getTagline());
        title.set(mRegionList.getSliderTitle());
        content.set(mRegionList.getSliderContent());
        imageUrl.set(mRegionList.getRegionImage());

        totalKitchens.set(mRegionList.getKitchencount()+" Kitchens");
        stateName.set(mRegionList.getStatename());
        famousfood.set(mRegionList.getSpecialitiesFoodContent());
        identityImage.set(mRegionList.getIdentityImg());
        spclFoodImage.set(mRegionList.getSpecialDishImg());


    }


    public void onItemClick() {
        mListener.onItemClick(mRegionList);
    }



    public interface RegionItemViewModelListener {
        void onItemClick(RegionsResponse.Result mRegionList);


    }

}
