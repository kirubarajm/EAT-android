package com.tovo.eat.ui.home.homemenu;

public class RegionCardMoreItemViewModel {
    public final RegionMoreItemViewModelListener mListener;
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
