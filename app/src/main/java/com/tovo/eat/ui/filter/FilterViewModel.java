package com.tovo.eat.ui.filter;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

import dagger.Module;

@Module
public class FilterViewModel extends BaseViewModel<FilterNavigator> {


    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<Integer> selectedOptions = new ObservableField<>();


    public ObservableList<FilterItems> filterItems = new ObservableArrayList<>();


    public FilterViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void sort() {
        selectedOptions.set(1);

    }

    public void regional() {
        selectedOptions.set(2);

    }
    public void cusines() {

        selectedOptions.set(3);
    }


    public void clearAll() {

      getNavigator().clearFilters();
    }


    public void filterSortOptions(){











    }


}
