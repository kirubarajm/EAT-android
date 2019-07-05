package com.tovo.eat.ui.home.region.list;

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
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class RegionDetailsViewModel extends BaseViewModel<RegionDetailsNavigator> {




    public final ObservableField<String> regionName = new ObservableField<>();
    public final ObservableField<String> totalKitchens = new ObservableField<>();



    public ObservableList<KitchenResponse.Result> kitchenListItemViewModels = new ObservableArrayList<>();
    boolean haveAddress = false;
    private MutableLiveData<List<KitchenResponse.Result>> kitchenListItemsLiveData;

    public RegionDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        kitchenListItemsLiveData = new MutableLiveData<>();

    }

    public ObservableList<KitchenResponse.Result> getkitchenListItemViewModels() {
        return kitchenListItemViewModels;
    }

    public void setkitchenListItemViewModels(ObservableList<KitchenResponse.Result> kitchenListItemViewModels) {
        this.kitchenListItemViewModels = kitchenListItemViewModels;
    }

    public MutableLiveData<List<KitchenResponse.Result>> getkitchenListItemsLiveData() {
        return kitchenListItemsLiveData;
    }

    public void setkitchenListItemsLiveData(MutableLiveData<List<KitchenResponse.Result>> kitchenListItemsLiveData) {
        this.kitchenListItemsLiveData = kitchenListItemsLiveData;
    }

    public void addDishItemsToList(List<KitchenResponse.Result> ordersItems) {
        kitchenListItemViewModels.clear();
        kitchenListItemViewModels.addAll(ordersItems);

    }

    public void goBack() {

        getNavigator().goBack();
    }


    public void fetchRepos(Integer regionId) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_REGION_KITCHEN_LIST, KitchenResponse.class,new RegionDetailsRequest(getDataManager().getCurrentLat(),getDataManager().getCurrentLng(),getDataManager().getCurrentUserId(),regionId), new Response.Listener<KitchenResponse>() {
                @Override
                public void onResponse(KitchenResponse response) {
                    if (response != null) {



                        if (response.getResult().size()!=0) {


                            kitchenListItemsLiveData.setValue(response.getResult());

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
        } catch (Exception ee){

        ee.printStackTrace();

    }
    }


}
