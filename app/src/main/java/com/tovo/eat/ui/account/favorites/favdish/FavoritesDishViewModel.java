package com.tovo.eat.ui.account.favorites.favdish;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class FavoritesDishViewModel extends BaseViewModel<FavoritesDishNavigator> {

    public ObservableList<FavoriteDishResponse.Result> dishViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<FavoriteDishResponse.Result>> favDishLiveData;
    public final ObservableField<String> userid = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> email = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableField<String> bank_account_no = new ObservableField<>();
    public final ObservableField<String> phoneno = new ObservableField<>();
    public final ObservableField<String> lat = new ObservableField<>();
    public final ObservableField<String> localityid = new ObservableField<>();
    public final ObservableField<String> verified_status = new ObservableField<>();
    public final ObservableField<String> referalcode = new ObservableField<>();
    public final ObservableField<String> created_at = new ObservableField<>();
    public final ObservableField<String> bank_name = new ObservableField<>();
    public final ObservableField<String> ifsc = new ObservableField<>();
    public final ObservableField<String> bank_holder_name = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> branch = new ObservableField<>();


    public FavoritesDishViewModel(DataManager dataManager) {
        super(dataManager);
        favDishLiveData = new MutableLiveData<>();
        fetchRepos();
    }

    public void addFavDishesToList(List<FavoriteDishResponse.Result> paymentsItems) {
        dishViewModels.clear();
        dishViewModels.addAll(paymentsItems);
    }

    public void fetchRepos() {
        if(!MvvmApp.getInstance().onCheckNetWork()) return;
        /*long userId = getDataManager().getCurrentUserId();
        String strUserId = String.valueOf(userId);*/
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_DISH_LIST+"12", FavoriteDishResponse.class, new Response.Listener<FavoriteDishResponse>() {
            @Override
            public void onResponse(FavoriteDishResponse response) {
                try {
                    favDishLiveData.setValue(response.getResult());
                    Log.e("response",response.getSuccess());

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                setIsLoading(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.e("", error.getMessage());
                    setIsLoading(false);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }

    public ObservableList<FavoriteDishResponse.Result> getDishViewModels() {
        return dishViewModels;
    }

    public MutableLiveData<List<FavoriteDishResponse.Result>> getDish() {
        return favDishLiveData;
    }
}
