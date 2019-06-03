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
    public final ObservableBoolean haveMore = new ObservableBoolean();


    public final RegionItemViewModelListener mListener;
    public final RegionsResponse.Result mRegionList;




    public ObservableList<RegionsResponse.Kitchenlist> regionKitchenItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<RegionsResponse.Kitchenlist>> regionKitchenItemsLiveData;




    public RegionItemViewModel(RegionItemViewModelListener mListener, RegionsResponse.Result mRegionList) {
        this.mListener = mListener;
        this.mRegionList = mRegionList;

      //  area_name.set(mRegionList.getArea());
        region.set(mRegionList.getRegionname());
        imageUrl.set(mRegionList.getRegionImage() );


        area_name.set(mRegionList.getRegionname()+" Special");


       // totalKitchens.set((mRegionList.getKitchenNos())+" Homes specialize in "+mRegionList.getArea());

         if (mRegionList.getKitchencount()==1) {

             totalKitchens.set(+mRegionList.getKitchencount() + " Kitchen featured in " + mRegionList.getRegionname());
         }else {
             totalKitchens.set(+mRegionList.getKitchencount() + " Kitchens featured in " + mRegionList.getRegionname());
         }
      //  approx.set(mRegionList.getAprox());



        if (mRegionList.getKitchencount()>3){
            haveMore.set(true);
        }else {
            haveMore.set(false);
        }



        regionKitchenItemViewModels.addAll(mRegionList.getKitchenlist());








    }


    public void onItemClick() {
      //  mListener.onItemClick(mRegionList.getRegionId());

        if (clicked.get()){
            clicked.set(false);
        }else {
            clicked.set(true);
        }




    }


    public void showMore() {
         mListener.showMore(mRegionList.getRegionid());


    }


    public interface RegionItemViewModelListener {

        void onItemClick(Integer id);
        void showMore(Integer id);

    }

}
