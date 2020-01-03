package com.tovo.eat.ui.home.homemenu.collection;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class FilterCollectionCardItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subTitle = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();

    public final ObservableBoolean status = new ObservableBoolean();
    public final CollectionItemViewModelListener mListener;

    KitchenResponse.Collection collection;


    public FilterCollectionCardItemViewModel(CollectionItemViewModelListener mListener, KitchenResponse.Collection collection) {
        this.mListener = mListener;
        this.collection = collection;

        String[] separated = collection.getName().split(" ");
        //title.set( separated[0]+"\n"+ separated[1]);

        StringBuilder sTitle= new StringBuilder();

        for (int i=0;i<separated.length;i++){
            sTitle.append(separated[i]);
            if (i!=separated.length-1){
                sTitle.append("\n");
            }

        }
        title.set(sTitle.toString());

       // title.set(collection.getHeading());
        content.set(collection.getName());
        imageUrl.set(collection.getIcon());
        status.set(collection.getCollectionstatus());

    }

    public void onItemClick() {
        mListener.onItemClick(collection);
    }

    public interface CollectionItemViewModelListener {
        void onItemClick(KitchenResponse.Collection collection);
    }

}
