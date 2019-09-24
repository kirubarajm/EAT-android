package com.tovo.eat.ui.home.homemenu.collection;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class CollectionCardItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subTitle = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final CollectionItemViewModelListener mListener;

    KitchenResponse.Collection collection;


    public CollectionCardItemViewModel(CollectionItemViewModelListener mListener, KitchenResponse.Collection collection) {
        this.mListener = mListener;
        this.collection = collection;
        title.set(collection.getHeading());
        content.set(collection.getName());
        imageUrl.set(collection.getImgUrl());

    }

    public void onItemClick() {
        mListener.onItemClick(collection);
    }

    public interface CollectionItemViewModelListener {
        void onItemClick(KitchenResponse.Collection collection);
    }

}
