package com.tovo.eat.ui.account.referrals;

import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class ReferralsActivityViewModel extends BaseViewModel<ReferralsActivityNavigator> {

    public final ObservableField<String> referalcode = new ObservableField<>();
    public final ObservableField<String> referalMessage = new ObservableField<>();
    public String referallink = "";


    public ReferralsActivityViewModel(DataManager dataManager) {
        super(dataManager);
        fetchRepos();
    }


    public void goBack() {
        getNavigator().goBack();
    }

    public void sendReferrals() {
        getNavigator().sendReferralsClick();
    }

    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            long userId = getDataManager().getCurrentUserId();
            //String strUserId = String.valueOf(userId);
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_REFERRALS + userId, ReferralsResponse.class, new Response.Listener<ReferralsResponse>() {
                @Override
                public void onResponse(ReferralsResponse response) {
                    try {
                        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
                            Log.e("----response:---------", String.valueOf(response.getSuccess()));
                            setIsLoading(false);
                            referalcode.set(response.getResult().get(0).getReferalcode());
                            referallink = response.getResult().get(0).getApplink();
                            referalMessage.set(response.getResult().get(0).getApplink());
                            if (getNavigator() != null)
                            getNavigator().success(String.valueOf(referallink));
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    } catch (Exception ee) {

                        ee.printStackTrace();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        setIsLoading(false);
                        if (getNavigator() != null)
                        getNavigator().failure("failed");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

}
