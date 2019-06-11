package com.tovo.eat.ui.search.dish;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;


public class SearchDishItemViewModel {

    public final ObservableField<String> ratings = new ObservableField<>();

    public final ObservableField<String> kitchen_name = new ObservableField<>();

    public final ObservableField<String> kitchen_type = new ObservableField<>();
    public final ObservableField<String> favourite = new ObservableField<>();
    public final ObservableField<String> eta = new ObservableField<>();
    public final ObservableField<String> kitchen_image = new ObservableField<>();
    public final ObservableField<String> offer = new ObservableField<>();
    public final ObservableField<String> region = new ObservableField<>();
    public final ObservableField<String> cuisines = new ObservableField<>();


    public final ObservableBoolean isFavourite = new ObservableBoolean();
    public final ObservableBoolean isRated = new ObservableBoolean();
    public final ObservableBoolean isEta = new ObservableBoolean();


    public final RegionItemViewModelListener mListener;
    public final KitchenDishResponse.Result mKitchenList;


    public ObservableList<KitchenDishResponse.Productlist> KitchenDishItemViewModels = new ObservableArrayList<>();


    public SearchDishItemViewModel(RegionItemViewModelListener mListener, KitchenDishResponse.Result mKitchenList) {
        this.mListener = mListener;
        this.mKitchenList = mKitchenList;
        KitchenDishItemViewModels.addAll(mKitchenList.getProductlist());


        this.kitchen_image.set(mKitchenList.getMakeitimg());


        if (mKitchenList.getEta() == null) {
            isEta.set(false);
        } else {
            this.eta.set(mKitchenList.getEta());
            isEta.set(true);

        }


        //  this.favourite.set(mKitchenList.getFavourite());
        //  this.offer.set(mKitchenList.getOffer());

        this.region.set(mKitchenList.getRegionname());

        if (mKitchenList.getRating() != null) {
            isRated.set(true);
            this.ratings.set(String.valueOf(mKitchenList.getRating()));
        } else {
            isRated.set(false);
        }

/*
        StringBuilder itemsBuilder = new StringBuilder();

        if (mKitchenList.getCuisines() != null) {

            for (int i = 0; i < mKitchenList.getCuisines().size(); i++) {

                itemsBuilder.append(mKitchenList.getCuisines().get(i).getCuisinename());

                if (mKitchenList.getCuisines().size() - 1 == i) {

                } else {
                    itemsBuilder.append(" | ");

                }
            }
        }

        String items = itemsBuilder.toString();
        cuisines.set(items);*/


        cuisines.set("by " + mKitchenList.getMakeitusername() + " | " + mKitchenList.getRegionname());


        this.offer.set(String.valueOf(mKitchenList.getCostfortwo()));
        this.kitchen_name.set(mKitchenList.getMakeitbrandname());

       /* if (mKitchenList.getMakeitbrandname() != null)
            if (mKitchenList.getMakeitbrandname().isEmpty()) {
                this.kitchen_name.set(mKitchenList.getMakeitusername());
            } else {
                this.kitchen_name.set(mKitchenList.getMakeitbrandname());
            }*/

        if (mKitchenList.getIsfav() != null) {
            if (mKitchenList.getIsfav().equals("0")) {
                this.isFavourite.set(false);
            } else {
                this.isFavourite.set(true);
            }
        } else {
            this.isFavourite.set(false);
        }


    }

    public void fav() {

/*
        if (mKitchenList.getIsfav().equals("0")) {
            isFavourite.set(true);
            mListener.addFavourites(mKitchenList.getMakeituserid(), mKitchenList.getIsfav());
        } else if (mKitchenList.getIsfav().equals("1")) {

            if (mKitchenList.getFavid() != null) {
                isFavourite.set(false);
                mListener.removeFavourites(mKitchenList.getFavid());
            }

        } else {
            isFavourite.set(false);
        }*/


    }

    public void onItemClick() {
        mListener.onItemClick(mKitchenList.getMakeituserid());
    }


    public void showMore() {
        mListener.showMore(mKitchenList.getMakeituserid());


    }


    public interface RegionItemViewModelListener {

        void onItemClick(Integer id);

        void showMore(Integer id);

    }

}
