package com.tovo.eat.ui.filter;


import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.MasterPojo;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class FilterViewModel extends BaseViewModel<FilterNavigator> {


    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<Integer> selectedOptions = new ObservableField<>();
    public ObservableList<FilterItems> filterItems = new ObservableArrayList<>();
    public ObservableBoolean isSortClicked = new ObservableBoolean();
    public ObservableBoolean isRegionalClicked = new ObservableBoolean();
    public ObservableBoolean isCuisinesClicked = new ObservableBoolean();
    List<MasterPojo.Cuisinelist> cuisinelists = new ArrayList<>();
    List<MasterPojo.Sort> sorts = new ArrayList<>();
    List<MasterPojo.Regionlist> regionlists = new ArrayList<>();
    List<FilterItems> filterItemList = new ArrayList<>();


    public FilterViewModel(DataManager dataManager) {
        super(dataManager);
        filterSortOptions();
    }


    public void sort() {
        selectedOptions.set(1);
        isSortClicked.set(true);
        isRegionalClicked.set(false);
        isCuisinesClicked.set(false);

filterItemList.clear();

        for (int i = 0; i < sorts.size(); i++) {
            filterItemList.add(new FilterItems(sorts.get(i).getSortid(), sorts.get(i).getSortname()));
        }
        filterItems.clear();
        filterItems.addAll(filterItemList);

    }

    public void regional() {
        selectedOptions.set(2);
        isSortClicked.set(false);
        isRegionalClicked.set(true);
        isCuisinesClicked.set(false);

        filterItemList.clear();
        for (int i = 0; i < regionlists.size(); i++) {
            filterItemList.add(new FilterItems(regionlists.get(i).getRegionid(), regionlists.get(i).getRegionname()));
        }
        filterItems.clear();
        filterItems.addAll(filterItemList);

    }

    public void cusines() {

        selectedOptions.set(3);

        isSortClicked.set(false);
        isRegionalClicked.set(false);
        isCuisinesClicked.set(true);

        filterItemList.clear();

        for (int i = 0; i < cuisinelists.size(); i++) {
            filterItemList.add(new FilterItems(cuisinelists.get(i).getCusineid(), cuisinelists.get(i).getCusinename()));
        }
        filterItems.clear();
        filterItems.addAll(filterItemList);


    }


    public void clearAll() {
        getNavigator().clearFilters();
        getDataManager().saveFilterSort("");
    }


    public void filterSortOptions() {

        MasterPojo masterPojo = new MasterPojo();

        Gson sGson = new GsonBuilder().create();
        masterPojo = sGson.fromJson(getDataManager().getMaster(), MasterPojo.class);

        cuisinelists = masterPojo.getResult().get(1).getCuisinelist();
        regionlists = masterPojo.getResult().get(0).getRegionlist();
        sorts = masterPojo.getResult().get(2).getSort();

        for (int i = 0; i < sorts.size(); i++) {
            filterItemList.add(new FilterItems(sorts.get(i).getSortid(), sorts.get(i).getSortname()));
        }

        filterItems.addAll(filterItemList);

        isSortClicked.set(true);

    }


}
