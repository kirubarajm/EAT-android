package com.tovo.eat.ui.cart;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.LatLngPojo;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class CartViewModel extends BaseViewModel<CartNavigator> {



    public final ObservableField<String> makeit_username = new ObservableField<>();
    public final ObservableField<String> makeit_image = new ObservableField<>();
    public final ObservableField<Integer> makeit_userid = new ObservableField<>();




    public ObservableList<CartRequestPojo.Result> cartDishItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<CartRequestPojo.Result>>dishItemsLiveData;


    public ObservableList<CartRequestPojo.Result> getCartDishItemViewModels() {
        return cartDishItemViewModels;
    }

    public void setCartDishItemViewModels(ObservableList<CartRequestPojo.Result> cartDishItemViewModels) {
        this.cartDishItemViewModels = cartDishItemViewModels;
    }

    public MutableLiveData<List<CartRequestPojo.Result>> getKitchenItemsLiveData() {
        return dishItemsLiveData;
    }

    public void setKitchenItemsLiveData(MutableLiveData<List<CartRequestPojo.Result>> kitchenItemsLiveData) {
        this.dishItemsLiveData = kitchenItemsLiveData;
    }

    public CartViewModel(DataManager dataManager) {
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

    public void addDishItemsToList(List<CartRequestPojo.Result> ordersItems) {
        cartDishItemViewModels.clear();
        cartDishItemViewModels.addAll(ordersItems);

    }


    public void saveToCartPojo(String cartJsonString){


        getDataManager().setCartDetails(cartJsonString);
    }




    public String getCartPojoDetails(){

        return getDataManager().getCartDetails();
    }

    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;


        Gson sGson = new GsonBuilder().create();
        CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

        if (cartRequestPojo == null) {
            cartRequestPojo = new CartRequestPojo();
        }else {

            makeit_username.set(cartRequestPojo.getMakeit_username());
            makeit_image.set(cartRequestPojo.getKitchenImage());



            if (cartRequestPojo.getResult() != null) {
                dishItemsLiveData.setValue(cartRequestPojo.getResult());
            }
        }

    }

}
