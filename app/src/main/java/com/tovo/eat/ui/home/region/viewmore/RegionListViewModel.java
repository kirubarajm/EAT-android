package com.tovo.eat.ui.home.region.viewmore;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class RegionListViewModel extends BaseViewModel<RegionListNavigator> {


    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> totalKitchens = new ObservableField<>();
    public final ObservableField<String> stateName = new ObservableField<>();
    public final ObservableField<String> famousfood = new ObservableField<>();
    public final ObservableField<String> identityImage = new ObservableField<>();
    public final ObservableField<String> spclFoodImage = new ObservableField<>();



    public ObservableList<RegionsResponse.Result> regionResults = new ObservableArrayList<>();
    private MutableLiveData<List<RegionsResponse.Result>> regionListItemsLiveData;

    public RegionListViewModel(DataManager dataManager) {
        super(dataManager);
        regionListItemsLiveData = new MutableLiveData<>();
        fetchRepos();
    }


    public ObservableList<RegionsResponse.Result> getRegionResults() {
        return regionResults;
    }

    public void setRegionResults(ObservableList<RegionsResponse.Result> regionResults) {
        this.regionResults = regionResults;
    }

    public MutableLiveData<List<RegionsResponse.Result>> getRegionListItemsLiveData() {
        return regionListItemsLiveData;
    }

    public void setRegionListItemsLiveData(MutableLiveData<List<RegionsResponse.Result>> regionListItemsLiveData) {
        this.regionListItemsLiveData = regionListItemsLiveData;
    }

    public void addDishItemsToList(List<RegionsResponse.Result> ordersItems) {
        regionResults.clear();
        regionResults.addAll(ordersItems);

    }

    public void goBack() {

        getNavigator().goBack();
    }


    public void fetchRepos() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_REGION_LIST, RegionsResponse.class, new RegionListRequest(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(), getDataManager().getCurrentUserId(), getDataManager().getRegionId()), new Response.Listener<RegionsResponse>() {
                @Override
                public void onResponse(RegionsResponse response) {
                    if (response != null) {

                        if (response.getResult().size() != 0) {

                            regionListItemsLiveData.setValue(response.getResult());

                            regionName.set(response.getResult().get(0).getRegionname());

                            totalKitchens.set(response.getResult().size() + " Homes specialize in " + response.getResult().get(0).getRegionname());

                            getNavigator().listLoaded();
                        }

                    }
                    getNavigator().listLoaded();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getNavigator().listLoaded();
                }
            });


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


}
