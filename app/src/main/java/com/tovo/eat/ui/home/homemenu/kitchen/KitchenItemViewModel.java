package com.tovo.eat.ui.home.homemenu.kitchen;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class KitchenItemViewModel {


    //   public final ObservableField<Integer> sales_emp_id = new ObservableField<Integer>();

    public final ObservableField<String> ratings = new ObservableField<String>();

    public final ObservableField<String> kitchen_name = new ObservableField<>();

    public final ObservableField<String> kitchen_type = new ObservableField<>();
    public final ObservableField<String> favourite = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchen_image = new ObservableField<>();
    public final ObservableField<String> offer = new ObservableField<>();

    public final ObservableBoolean isFavourite = new ObservableBoolean();


    public final KitchenItemViewModelListener mListener;
    public final KitchenResponse.Result mKitchenList;


    public KitchenItemViewModel(KitchenItemViewModelListener mListener, KitchenResponse.Result mKitchenList) {
        this.mListener = mListener;
        this.mKitchenList = mKitchenList;
        //  this.date.set(mSalesList.getDate());

        //    this.ratings.set(String.valueOf(mKitchenList.getRatings()));

        //  this.kitchen_type.set(mKitchenList.getKitchenType());
        this.kitchen_image.set(mKitchenList.getMakeitimg());
        this.eta.set(mKitchenList.getEta());
        //  this.favourite.set(mKitchenList.getFavourite());
        //  this.offer.set(mKitchenList.getOffer());


        if (mKitchenList.getMakeitbrandname() == null) {
            this.kitchen_name.set(mKitchenList.getMakeitusername());
        } else {
            this.kitchen_name.set(mKitchenList.getMakeitbrandname());
        }


        if (mKitchenList.getFavid() != null) {
            if (mKitchenList.getFavid().equalsIgnoreCase("0")) {
                this.isFavourite.set(false);
            } else {
                this.isFavourite.set(true);
            }
        }else {
            this.isFavourite.set(false);
        }
    }

    public void fav(){
        mListener.addFavourites(mKitchenList.getMakeituserid(), mKitchenList.getFavid());

    }



    public void onItemClick() {
        mListener.onItemClick(mKitchenList.getMakeituserid());

        //  mListener.onItemClick(isJobCompleted,Integer.parseInt(sales_emp_id.toString()) , Integer.parseInt(makeit_userid.toString()), date.toString(), name.toString(), email.toString(), phoneno.toString(), brandname.toString(),address.toString(),lat.toString(),lng.toString(),localityid.toString());

    }

    public interface KitchenItemViewModelListener {
        // void onItemClick(boolean completed_status, Object salesEmpId, int makeitUserId, String date, String name, String email, String phNum, String brandName, String address, String lat, String lng);

        void onItemClick(Integer id);

        void addFavourites(Integer id,String fav);

    }

}
