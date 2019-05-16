package com.tovo.eat.ui.home.region;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.List;


public class RegionItemViewModel {

    public final ObservableField<String> area_name = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> totalKitchens = new ObservableField<>();
    public final ObservableField<String> approx = new ObservableField<>();


    public final ObservableBoolean clicked = new ObservableBoolean();


    public final RegionItemViewModelListener mListener;
    public final RegionsResponse.Result mRegionList;




    public ObservableList<RegionsResponse.Kitchen> regionKitchenItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<RegionsResponse.Kitchen>> regionKitchenItemsLiveData;




    public RegionItemViewModel(RegionItemViewModelListener mListener, RegionsResponse.Result mRegionList) {
        this.mListener = mListener;
        this.mRegionList = mRegionList;

        area_name.set(mRegionList.getArea());
        region.set(mRegionList.getRegion());
        imageUrl.set(mRegionList.getImage());
        totalKitchens.set((mRegionList.getKitchenNos())+" Homes specialize in "+mRegionList.getArea());
        approx.set(mRegionList.getAprox());





        regionKitchenItemViewModels.addAll(mRegionList.getKitchens());








    }


    public void onItemClick() {
      //  mListener.onItemClick(mRegionList.getRegionId());

        if (clicked.get()){
            clicked.set(false);
        }else {
            clicked.set(true);
        }




    }

    public interface RegionItemViewModelListener {

        void onItemClick(Integer id);


    }

}
