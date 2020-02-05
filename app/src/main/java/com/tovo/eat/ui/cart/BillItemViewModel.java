package com.tovo.eat.ui.cart;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class BillItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> charges = new ObservableField<>();
    public final ObservableField<String> note = new ObservableField<>();
    public final ObservableBoolean showInfo = new ObservableBoolean();
    public final ObservableBoolean lowCost = new ObservableBoolean();
    public final ObservableBoolean aboveCost = new ObservableBoolean();


    public final BilldetailsInfoViewModelListener mListener;
    private final CartPageResponse.Cartdetail cartdetail;

    public BillItemViewModel(CartPageResponse.Cartdetail cartdetail, BilldetailsInfoViewModelListener mListener) {
        this.cartdetail = cartdetail;
        this.mListener = mListener;

        title.set(cartdetail.getTitle());
        //charges.set(String.valueOf(cartdetail.getCharges()));
        aboveCost.set(cartdetail.getDefaultCostStatus());
        if (cartdetail.getDefaultCostStatus()){
            charges.set(String.valueOf(cartdetail.getDefaultCost()));
        }else {
            charges.set(String.valueOf(cartdetail.getCharges()));
        }

        /*if (cartdetail.getCharges()!=null&&cartdetail.getCharges()>0){
            charges.set(String.valueOf(cartdetail.getCharges()));
            aboveCost.set(cartdetail.getDefaultCostStatus());
        }else {
            charges.set(String.valueOf(cartdetail.getDefaultCost()));
            aboveCost.set(true);
        }*/



        showInfo.set(cartdetail.getInfostatus()==null?false:cartdetail.getInfostatus());
       // showInfo.set(true);

        note.set(cartdetail.getLowCostNote());
        lowCost.set(cartdetail.getLowCostStatus()==null?false:cartdetail.getLowCostStatus());

    }


    public void infoClick() {
        mListener.onItemClick(cartdetail);
    }

    public interface BilldetailsInfoViewModelListener {
        void onItemClick(CartPageResponse.Cartdetail cartdetail);
    }

}
