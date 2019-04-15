package com.tovo.eat.ui.account.favorites.favkitchen;

import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class FavoritesKitchenViewModel extends BaseViewModel<FavoritesKitchenNavigator> {


    //public ObservableList<PaymentResponse.PaymentList> dishViewModels = new ObservableArrayList<>();
    //private MutableLiveData<List<PaymentResponse.PaymentList>> personLiveData;
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



    public FavoritesKitchenViewModel(DataManager dataManager) {
        super(dataManager);
        //personLiveData = new MutableLiveData<>();
        //fetchRepos();
    }

/*
    public void addPersonToList(List<PaymentResponse.PaymentList> paymentsItems) {
        dishViewModels.clear();
        dishViewModels.addAll(paymentsItems);
    }
*/

/*
    public void fetchRepos() {
        if(!MvvmApp.getInstance().onCheckNetWork()) return;
        long userId = getDataManager().getCurrentUserId();
        String strUserId = String.valueOf(userId);
        //if(!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_USER_PAYMENTS+strUserId, PaymentResponse.class, new Response.Listener<PaymentResponse>() {
            @Override
            public void onResponse(PaymentResponse response) {
                try {
                    if (response != null && response.getResult() != null && response.getResult().size()>0) {
                        personLiveData.setValue(response.getResult());
                        userid.set(String.valueOf(response.getResult().get(0).getUserid()));
                        name.set(response.getResult().get(0).getName());
                        email.set(response.getResult().get(0).getEmail());
                        password.set(response.getResult().get(0).getPassword());
                        bank_account_no.set(String.valueOf(response.getResult().get(0).getBankAccountNo()));
                        phoneno.set(response.getResult().get(0).getPhoneno()==null?"":response.getResult().get(0).getPhoneno());
                        lat.set(String.valueOf(response.getResult().get(0).getLat()));
                        localityid.set(String.valueOf(response.getResult().get(0).getLocalityid()));
                        verified_status.set(String.valueOf(response.getResult().get(0).getVerifiedStatus()));
                        referalcode.set(String.valueOf(response.getResult().get(0).getReferalcode()));
                        created_at.set(response.getResult().get(0).getCreatedAt());
                        bank_name.set(String.valueOf(response.getResult().get(0).getBankName()));
                        ifsc.set(String.valueOf(response.getResult().get(0).getIfsc()));
                        bank_holder_name.set(String.valueOf(response.getResult().get(0).getBankHolderName()));
                        address.set(String.valueOf(response.getResult().get(0).getAddress()==null?"":response.getResult().get(0).getAddress()));
                        if (response.getResult().get(0).getBranch()==null)
                        {
                            branch.set("");
                        }else {
                            branch.set(String.valueOf(response.getResult().get(0).getBranch()));
                        }

                        //Log.e("", response.getMessage());
                    }
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
*/

 /*   public ObservableList<PaymentResponse.PaymentList> getPersonalViewModels() {
        return dishViewModels;
    }

    public MutableLiveData<List<PaymentResponse.PaymentList>> getPersonal() {
        return personLiveData;
    }*/
}
