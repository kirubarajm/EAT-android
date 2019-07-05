package com.tovo.eat.ui.home.homemenu;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.region.RegionsResponse;


public class RegionCardMoreItemViewModel {



    public final RegionMoreItemViewModelListener mListener;


    int position;


    public RegionCardMoreItemViewModel(RegionMoreItemViewModelListener mListener) {
        this.mListener = mListener;

    }


    public void onItemClick() {
        mListener.viewMoreRegions();
    }



    public interface RegionMoreItemViewModelListener {
        void viewMoreRegions();


    }

}
