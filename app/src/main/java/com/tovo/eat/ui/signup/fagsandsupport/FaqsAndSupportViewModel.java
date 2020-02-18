package com.tovo.eat.ui.signup.fagsandsupport;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.CartPageResponse;
import com.tovo.eat.ui.home.homemenu.collection.CollectionRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MasterPojo;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.SupportResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class FaqsAndSupportViewModel extends BaseViewModel<FaqsAndSupportNavigator> {
    public ObservableBoolean contact = new ObservableBoolean();

    public ObservableField<String> support=new ObservableField<>();

    public final ObservableBoolean supportNumber = new ObservableBoolean();


    public FaqsAndSupportViewModel(DataManager dataManager) {
        super(dataManager);
        contact.set(false);
        support.set("0");
        supportNumber.set(false);
        getSupportContact();
        //support.set(getDataManager().getSupportNumber());
    }


    public void faq() {
        getNavigator().feedBackClick();
    }

    public void supportClick() {
           getNavigator().supportClick();

        /*if (contact.get()) {
            contact.set(false);
        } else {
            contact.set(true);
        }*/

    }



    public void getSupportContact() {

        JsonObjectRequest jsonObjectRequest = null;
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CUSTOMER_SUPPORT, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                        if (response != null) {

                            Gson gson = new Gson();
                            SupportResponse supportResponse = gson.fromJson(response.toString(), SupportResponse.class);

                            if (supportResponse.getStatus()){
                                getDataManager().saveSupportNumber(String.valueOf(supportResponse.getCustomerSupport()));
                                support.set(String.valueOf(supportResponse.getCustomerSupport()));
                            }

                        }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                }
            }) {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };


        MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);


    }






    public void goBack() {
        getNavigator().goBack();

    }

    public void callCustomer() {
        getNavigator().callCusstomerCare();

    }

}
