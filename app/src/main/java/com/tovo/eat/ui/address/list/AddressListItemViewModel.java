package com.tovo.eat.ui.address.list;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.utilities.AppConstants;


public class AddressListItemViewModel {

    public final ObservableField<String> addressTitle = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableBoolean isHome = new ObservableBoolean();
    public final ObservableBoolean isWork = new ObservableBoolean();
    public final addressListItemViewModelListener mListener;
    private final AddressListResponse.Result addressList;


    public AddressListItemViewModel(addressListItemViewModelListener mListener, AddressListResponse.Result addressList) {

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

    public void deleteAddress() {

        mListener.deleteAddress(addressList);
    }


    public void editAddress() {
        mListener.editAddress(addressList);
    }


    public void onItemClick() {
        mListener.onItemClick(addressList);

    }

    public interface addressListItemViewModelListener {
        void onItemClick(AddressListResponse.Result result);

        void editAddress(AddressListResponse.Result result);

        void deleteAddress(AddressListResponse.Result addressList);

        void homeAddressAdded();

        void officeAddressAdded();


    }

}
