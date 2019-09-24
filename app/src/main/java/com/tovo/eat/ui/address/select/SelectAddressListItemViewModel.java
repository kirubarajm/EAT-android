package com.tovo.eat.ui.address.select;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.utilities.AppConstants;


public class SelectAddressListItemViewModel {


    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableBoolean isHome = new ObservableBoolean();
    public final ObservableBoolean isWork = new ObservableBoolean();
    public final addressListItemViewModelListener mListener;
    private final SelectAddressListResponse.Result addressList;


    public SelectAddressListItemViewModel(addressListItemViewModelListener mListener, SelectAddressListResponse.Result addressList) {

        this.mListener = mListener;
        this.addressList = addressList;

        if (addressList.getAddressType().equalsIgnoreCase(AppConstants.ADDRESS_TYPE_HOME)) {
            isHome.set(true);
            isWork.set(false);
            mListener.homeAddressAdded();
        } else if (addressList.getAddressType().equalsIgnoreCase(AppConstants.ADDRESS_TYPE_WORK)) {
            isHome.set(false);
            isWork.set(true);
            mListener.officeAddressAdded();
        } else if (addressList.getAddressType().equalsIgnoreCase(AppConstants.ADDRESS_TYPE_OTHER)) {
            isHome.set(false);
            isWork.set(false);
        } else {
            isHome.set(false);
            isWork.set(false);
        }
        addressTitle.set(addressList.getAddressTitle());
        address.set(addressList.getAddress());
    }

    public void editAddress() {
        mListener.editAddress(addressList);
    }

    public void onItemClick() {
        mListener.onItemClick(addressList);
    }

    public interface addressListItemViewModelListener {
        void onItemClick(SelectAddressListResponse.Result result);

        void editAddress(SelectAddressListResponse.Result result);

        void homeAddressAdded();

        void officeAddressAdded();

    }
}
