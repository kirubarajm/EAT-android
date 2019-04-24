package com.tovo.eat.ui.filter;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;


public class FilterItemViewModel {


    public final ObservableField<String> filterTitle = new ObservableField<>();

    public final ObservableBoolean isClicked = new ObservableBoolean();


    public final FilterItemViewModelListener mListener;
    private final FilterItems filterItems;

    Integer type, id;

    public FilterItemViewModel(FilterItemViewModelListener mListener, FilterItems filterItems, Integer type) {


        this.mListener = mListener;
        this.filterItems = filterItems;
        this.type = type;

        filterTitle.set(filterItems.getTitle());


    }


    public void onItemClick() {
        mListener.onItemClick(type, id);
        if (isClicked.get()){
            isClicked.set(false);
        }else {
            isClicked.set(true);
        }
    }

    public interface FilterItemViewModelListener {

        void onItemClick(Integer type, Integer id);


    }

}
