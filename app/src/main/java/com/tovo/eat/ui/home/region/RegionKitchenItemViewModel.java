package com.tovo.eat.ui.home.region;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class RegionKitchenItemViewModel {


    public final ObservableField<String> ratings = new ObservableField<>();

    public final ObservableField<String> kitchen_name = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> offer = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> specialists = new ObservableField<>();


    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean isRated = new ObservableBoolean();
    public final ObservableBoolean isEta = new ObservableBoolean();

    public final RegionItemViewModelListener mListener;
    public final RegionsResponse.Kitchenlist mRegionList;


    public RegionKitchenItemViewModel(RegionItemViewModelListener mListener, RegionsResponse.Kitchenlist mRegionList) {
        this.mListener = mListener;
        this.mRegionList = mRegionList;

        this.kitchen_name.set(mRegionList.getMakeitbrandname());

        userName.set(mRegionList.getMakeitusername());

       // specialists.set(mRegionList.getSpecialists());

        if (mRegionList.getEta()==null){
            isEta.set(false);
        }else {
            this.eta.set(mRegionList.getEta());
            isEta.set(true);

        }


        if (mRegionList.getRating() != 0) {
            isRated.set(true);
            this.ratings.set(String.valueOf(mRegionList.getRating()));
        } else {
            isRated.set(false);
        }

        this.offer.set(String.valueOf(mRegionList.getCostfortwo()));

    }


    public void onItemClick() {
        mListener.onItemClick(mRegionList.getMakeituserid());
        if (isFavourite.get()){
            isFavourite.set(false);
        }else {
            isFavourite.set(true);
        }


    }

    public interface RegionItemViewModelListener {

        void onItemClick(Integer makeitId);


    }

}
