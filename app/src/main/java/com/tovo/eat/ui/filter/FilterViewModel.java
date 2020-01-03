package com.tovo.eat.ui.filter;


import android.app.DownloadManager;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.api.remote.GsontoJsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CusineResponse;
import com.tovo.eat.utilities.MasterPojo;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

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

    List<CusineResponse.Result> cuisinelists = new ArrayList<>();
    List<MasterPojo.Sort> sorts = new ArrayList<>();
    List<MasterPojo.Regionlist> regionlists = new ArrayList<>();
    List<FilterItems> filterItemList = new ArrayList<>();


    List<FilterRequestPojo.Cusinelist> filterCuisinelists = new ArrayList<>();
    List<FilterRequestPojo.Sortlist> filterSorts = new ArrayList<>();
    List<FilterRequestPojo.Regionlist> filterRegionlists = new ArrayList<>();


    public FilterViewModel(DataManager dataManager) {
        super(dataManager);
        getCuisineList();
        filterSortOptions();
        sort();
    }


    public void addToFilter(Integer id) {


        int selectedId = selectedOptions.get();


        FilterRequestPojo filterRequestPojo=new FilterRequestPojo();


        Gson sGson = new GsonBuilder().create();
        if (getDataManager().getFilterSort()!=null)
        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);


        filterSorts.clear();
        filterCuisinelists.clear();
        filterRegionlists.clear();

        if (filterRequestPojo.getSortlist() != null)
            filterSorts.addAll(filterRequestPojo.getSortlist());
        if (filterRequestPojo.getCusinelist() != null)
            filterCuisinelists.addAll(filterRequestPojo.getCusinelist());
        if (filterRequestPojo.getRegionlist() != null)
            filterRegionlists.addAll(filterRequestPojo.getRegionlist());

        if (selectedId == 1) {
            filterSorts.clear();
            filterSorts.add(new FilterRequestPojo.Sortlist(id));
            filterRequestPojo.setSortlist(null);
            filterRequestPojo.setSortlist(filterSorts);
            filterRequestPojo.setSortid(id);

            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);
            getDataManager().setFilterSort(json);


            sort();


        } else if (selectedId == 2) {

            filterRegionlists.add(new FilterRequestPojo.Regionlist(id));
            filterRequestPojo.setRegionlist(null);
            filterRequestPojo.setRegionlist(filterRegionlists);
            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);

            getDataManager().setFilterSort(json);


        } else if (selectedId == 3) {
            filterCuisinelists.add(new FilterRequestPojo.Cusinelist(id));
            filterRequestPojo.setCusinelist(null);
            filterRequestPojo.setCusinelist(filterCuisinelists);
            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);

            getDataManager().setFilterSort(json);

        } else {


        }


    }

    public void removeFromFilter(Integer id) {


        FilterRequestPojo filterRequestPojo=new FilterRequestPojo();

        Gson sGson = new GsonBuilder().create();
        if (getDataManager().getFilterSort()!=null)
        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);


        filterSorts.clear();
        filterCuisinelists.clear();
        filterRegionlists.clear();
        if (filterRequestPojo.getSortlist() != null)
            filterSorts.addAll(filterRequestPojo.getSortlist());
        if (filterRequestPojo.getCusinelist() != null)
            filterCuisinelists.addAll(filterRequestPojo.getCusinelist());
        if (filterRequestPojo.getRegionlist() != null)
            filterRegionlists.addAll(filterRequestPojo.getRegionlist());

        if (selectedOptions.get() == 1) {

            filterRequestPojo.setSortid(0);

            for (int i = 0; i < filterSorts.size(); i++) {

                if (id.equals(filterSorts.get(i).getSort())) {
                    filterSorts.remove(i);
                }
            }
            filterRequestPojo.setSortlist(null);
            filterRequestPojo.setSortlist(filterSorts);
            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);
            getDataManager().setFilterSort(json);


        } else if (selectedOptions.get() == 2) {

            for (int i = 0; i < filterRegionlists.size(); i++) {

                if (id.equals(filterRegionlists.get(i).getRegion())) {
                    filterRegionlists.remove(i);
                }
            }
            filterRequestPojo.setRegionlist(null);
            filterRequestPojo.setRegionlist(filterRegionlists);
            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);

            getDataManager().setFilterSort(json);


        } else if (selectedOptions.get() == 3) {

            for (int i = 0; i < filterCuisinelists.size(); i++) {

                if (id.equals(filterCuisinelists.get(i).getCusine())) {
                    filterCuisinelists.remove(i);
                }
            }
            filterRequestPojo.setRegionlist(null);
            filterRequestPojo.setCusinelist(filterCuisinelists);
            Gson gson = new Gson();
            String json = gson.toJson(filterRequestPojo);

            getDataManager().setFilterSort(json);

        } else {


        }


    }


    public void apply() {
        getDataManager().saveIsFilterApplied(true);
        getNavigator().applyFilter();

    }

    public Integer getSelectedOptions() {
        return selectedOptions.get();
    }


    public void sort() {


        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_FILTER, AppConstants.CLICK_SORT);

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

        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_FILTER, AppConstants.CLICK_CUISINE);


        selectedOptions.set(3);

        isSortClicked.set(false);
        isRegionalClicked.set(false);
        isCuisinesClicked.set(true);

        filterItemList.clear();

        for (int i = 0; i < cuisinelists.size(); i++) {
            filterItemList.add(new FilterItems(cuisinelists.get(i).getCuisineid(), cuisinelists.get(i).getCuisinename()));
        }
        filterItems.clear();
        filterItems.addAll(filterItemList);


    }


    public void clearAll() {
        getDataManager().saveIsFilterApplied(false);
        getDataManager().saveFilterSort(null);
        getNavigator().clearFilters();


    }


    public void filterSortOptions() {

        MasterPojo masterPojo = new MasterPojo();

        Gson sGson = new GsonBuilder().create();
        masterPojo = sGson.fromJson(getDataManager().getMaster(), MasterPojo.class);

       // cuisinelists = masterPojo.getResult().get(1).getCuisinelist();


        if (masterPojo!=null&&masterPojo.getResult()!=null) {

            regionlists = masterPojo.getResult().get(0).getRegionlist();
            sorts = masterPojo.getResult().get(2).getSort();

            for (int i = 0; i < sorts.size(); i++) {
                filterItemList.add(new FilterItems(sorts.get(i).getSortid(), sorts.get(i).getSortname()));
            }

            if (getDataManager().getCurrentFragment() == 1) {
                filterItemList.remove(1);
            }


            filterItems.addAll(filterItemList);

            isSortClicked.set(true);
        }
    }


    public void getCuisineList(){


        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_CUISINELIST, CusineResponse.class, new Response.Listener<CusineResponse>() {
            @Override
            public void onResponse(CusineResponse response) {
                if (response != null) {

                    cuisinelists =response.getResult();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("", error.getMessage());
                setIsLoading(false);
            }
        }, AppConstants.API_VERSION_ONE);

        MvvmApp.getInstance().addToRequestQueue(gsonRequest);

    }


}
