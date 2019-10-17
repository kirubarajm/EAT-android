package com.tovo.eat.ui.account.feedbackandsupport.feedback;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class FeedbackActivityViewModel extends BaseViewModel<FeedbackActivityNavigator> {
    Response.ErrorListener errorListener;
    String strMessage = "";

    public FeedbackActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void submitClick() {
        getNavigator().submit();
    }

    public void insertFeedbackServiceCall(int rating, String message) {
        Long userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_APP_FEEDBACK, FeedBackResponse.class, new FeedBackRequest(rating, userIdMain, message), new Response.Listener<FeedBackResponse>() {
                @Override
                public void onResponse(FeedBackResponse response) {
                    if (response != null) {
                        //int faqId = response.getFaqid();
                        strMessage = response.getMessage();
                        boolean booleanSucces = response.getSuccess();
                        if (booleanSucces) {
                            getNavigator().feedBackSucess(strMessage);
                        } else {
                            getNavigator().feedBackFailure(strMessage);
                        }
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    getNavigator().feedBackFailure(strMessage);
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();
        }
    }


    public void goBack() {
        getNavigator().goBack();
    }
}
