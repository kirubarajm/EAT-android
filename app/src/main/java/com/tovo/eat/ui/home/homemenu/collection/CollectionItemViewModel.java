package com.tovo.eat.ui.home.homemenu.collection;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;

import java.util.List;


public class CollectionItemViewModel {

    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> tagline = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final ObservableList<KitchenResponse.Collection> collections = new ObservableArrayList<>();


    public CollectionItemViewModel(List<KitchenResponse.Collection> item_list) {

        collections.addAll(item_list);

    }


}
