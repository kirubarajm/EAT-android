package com.tovo.eat.ui.address.select;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


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

        if (addressList.getAddressType().equalsIgnoreCase("1")){
            isHome.set(true);
            isWork.set(false);
        }else if (addressList.getAddressType().equalsIgnoreCase("2")){
            isHome.set(false);
            isWork.set(true);
        }else if (addressList.getAddressType().equalsIgnoreCase("3")){
            isHome.set(false);
            isWork.set(false);
        }else {
            isHome.set(false);
            isWork.set(false);
        }


        addressTitle.set(addressList.getAddressTitle());
        address.set(addressList.getAddress());





    }

    public void editAddress(){
      //  mListener.editAddress(addressList.getAid(),addressList.getAddressTitle(),addressList.getAddress(),addressList.getFlatno(),addressList.getLocality(),addressList.getPincode(),addressList.getLat(),addressList.getLon(),addressList.getLandmark(),addressList.getAddressType());
        mListener.editAddress(addressList);
    }


    public void onItemClick() {
      //  mListener.onItemClick(addressList.getAid(),addressList.getAddressTitle(),addressList.getAddress(),addressList.getFlatno(),addressList.getLocality(),addressList.getPincode(),addressList.getLat(),addressList.getLon(),addressList.getLandmark(),addressList.getAddressType());
        mListener.onItemClick(addressList);

    }

    public interface addressListItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

      //  void onItemClick(Integer aid,String addressTitle,String address,String flatno,String locality,String pincode, Double lat, Double lon, String landmark,String addressType);

     //   void editAddress(Integer aid,String addressTitle,String address,String flatno,String locality,String pincode, Double lat, Double lon, String landmark,String addressType);

        void onItemClick(SelectAddressListResponse.Result result);
        void editAddress(SelectAddressListResponse.Result result);



    }

}
