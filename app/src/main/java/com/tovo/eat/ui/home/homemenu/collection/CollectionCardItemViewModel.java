package com.tovo.eat.ui.home.homemenu.collection;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class CollectionCardItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subTitle = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ObservableBoolean status = new ObservableBoolean();


    public final CollectionItemViewModelListener mListener;

    KitchenResponse.Collection collection;


    public CollectionCardItemViewModel(CollectionItemViewModelListener mListener, KitchenResponse.Collection collection) {
        this.mListener = mListener;
        this.collection = collection;
        title.set(collection.getHeading());
        content.set(collection.getSubheading());
        imageUrl.set(collection.getImgUrl());
    //    status.set(collection.getCollectionstatus());

    }

    public void onItemClick() {
        if (collection.isClickable())
        mListener.onItemClick(collection);
    }

    public interface CollectionItemViewModelListener {
        void onItemClick(KitchenResponse.Collection collection);
    }

}
