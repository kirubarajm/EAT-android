package com.tovo.eat.ui.home.homemenu.dish;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.LatLngPojo;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class DishViewModel extends BaseViewModel<DishNavigator> {


    public ObservableList<DishResponse.Result> dishItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<DishResponse.Result>>dishItemsLiveData;


    public ObservableList<DishResponse.Result> getKitchenItemViewModels() {
        return dishItemViewModels;
    }

    public void setKitchenItemViewModels(ObservableList<DishResponse.Result> kitchenItemViewModels) {
        this.dishItemViewModels = kitchenItemViewModels;
    }

    public MutableLiveData<List<DishResponse.Result>> getKitchenItemsLiveData() {
        return dishItemsLiveData;
    }

    public void setKitchenItemsLiveData(MutableLiveData<List<DishResponse.Result>> kitchenItemsLiveData) {
        this.dishItemsLiveData = kitchenItemsLiveData;
    }

    public DishViewModel(DataManager dataManager) {
        super(dataManager);
        dishItemsLiveData = new MutableLiveData<>();

    //    AlertDialog.Builder builder=new AlertDialog.Builder(getDataManager().);
       /* ConnectivityManager cm =
                (ConnectivityManager)getDataManager(). getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();*/

        fetchRepos();
    }

    public void addDishItemsToList(List<DishResponse.Result> ordersItems) {
        dishItemViewModels.clear();
        dishItemViewModels.addAll(ordersItems);

    }




    public  String datas(){

        return getDataManager().getCartDetails();


    }


    public void removeFavourite(Integer favId){

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.DELETE, AppConstants.EAT_FAV_URL+favId, CommonResponse.class, null,new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        getNavigator().toastMessage(response.getMessage());


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }




    public void addFavourite(Integer dishId, String fav){

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FAV_URL, CommonResponse.class, new DishFavRequest(String.valueOf(getDataManager().getCurrentUserId()),String.valueOf(dishId)),new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {


                        getNavigator().toastMessage(response.getMessage());


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }





    }




    public String getCartPojoDetails(){

        return getDataManager().getCartDetails();
    }

    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;

     //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_DISH_LIST_URL, DishResponse.class, new LatLngPojo(getDataManager().getCurrentLat(), getDataManager().getCurrentLng(),getDataManager().getCurrentUserId()),new Response.Listener<DishResponse>() {
                @Override
                public void onResponse(DishResponse response) {
                    if (response != null) {

                        dishItemsLiveData.setValue(response.getResult());
                        Log.e("----response:---------", response.toString());
                        DishViewModel.this.getNavigator().dishListLoaded();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                    DishViewModel.this.getNavigator().dishListLoaded();
                }
            });




            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



}
