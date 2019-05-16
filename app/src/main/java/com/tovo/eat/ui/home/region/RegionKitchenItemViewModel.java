package com.tovo.eat.ui.home.region;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class RegionKitchenItemViewModel {

    public final ObservableField<String> area_name = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();
    public final ObservableField<String> totalKitchens = new ObservableField<>();
    public final ObservableField<String> approx = new ObservableField<>();


    public final ObservableBoolean clicked = new ObservableBoolean();


    public final RegionItemViewModelListener mListener;
    public final RegionsResponse.Kitchen mRegionList;


    public RegionKitchenItemViewModel(RegionItemViewModelListener mListener, RegionsResponse.Kitchen mRegionList) {
        this.mListener = mListener;
        this.mRegionList = mRegionList;

        area_name.set(mRegionList.getBrandname());
        region.set(mRegionList.getSpecialists());
        totalKitchens.set(mRegionList.getCostfortwo());


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
