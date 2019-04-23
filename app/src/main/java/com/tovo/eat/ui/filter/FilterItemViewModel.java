package com.tovo.eat.ui.filter;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class FilterItemViewModel {


    public final ObservableField<String> addressTitle = new ObservableField<>();

    public final ObservableBoolean isWork = new ObservableBoolean();


    public final FilterItemViewModelListener mListener;
    private final FilterItems filterItems;

    Integer type, id;

    public FilterItemViewModel(FilterItemViewModelListener mListener, FilterItems filterItems, Integer type) {


        this.mListener = mListener;
        this.filterItems = filterItems;
        this.type = type;

        //    addressTitle.set(addressList.getAddressTitle());


    }


    public void onItemClick() {
        //  mListener.onItemClick(addressList.getAid(),addressList.getAddressTitle(),addressList.getAddress(),addressList.getFlatno(),addressList.getLocality(),addressList.getPincode(),addressList.getLat(),addressList.getLon(),addressList.getLandmark(),addressList.getAddressType());
        mListener.onItemClick(type, id);
    }

    public interface FilterItemViewModelListener {

        void onItemClick(Integer type, Integer id);


    }

}
