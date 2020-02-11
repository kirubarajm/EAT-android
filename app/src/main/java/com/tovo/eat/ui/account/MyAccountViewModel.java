package com.tovo.eat.ui.account;


import android.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.coupon.CouponCheckRequest;
import com.tovo.eat.ui.signup.namegender.GetUserDetailsResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import dagger.Module;

@Module
public class MyAccountViewModel extends BaseViewModel<MyAccountNavigator> {

    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userEmail = new ObservableField<>();
    public final ObservableField<String> userPhoneNo = new ObservableField<>();
    public final ObservableField<String> gender = new ObservableField<>();
    public final ObservableField<String> regionname = new ObservableField<>();

    private GetUserDetailsResponse getUserDetailsResponse;


    public MyAccountViewModel(DataManager dataManager) {
        super(dataManager);
        //fetchUserDetails();




    }


    public void loadUserDetails(){
        userName.set(getDataManager().getCurrentUserName());
        userEmail.set(getDataManager().getCurrentUserEmail());
        userPhoneNo.set(getDataManager().getCurrentUserPhNo());
    }

    public void manageAddress() {
        getNavigator().manageAddress();
    }


    public void orderHistory() {
        getNavigator().orderHistory();
    }


    public void favourites() {
        getDataManager().setIsFav(true);
        getNavigator().favourites();
    }

    public void referrals() {
        getNavigator().referrals();
    }


    public void offers() {
        getNavigator().offers();
    }

    public void logOut() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_LOG_OUT, GetUserDetailsResponse.class, new CouponCheckRequest(getDataManager().getCurrentUserId()), new Response.Listener<GetUserDetailsResponse>() {
                @Override
                public void onResponse(GetUserDetailsResponse response) {
                    logOutSession();
                    if (getNavigator() != null)
                        getNavigator().logout();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        setIsLoading(false);
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


    public void feedbackAndSupport() {
        getNavigator().feedbackAndSupport();
    }

    public void editProfile() {
        if (getUserDetailsResponse != null) {
            getNavigator().editProfile(getUserDetailsResponse);
        }
    }

    public void logOutSession() {
        getDataManager().setLogout();
        getDataManager().setCartDetails(null);
        getDataManager().setAddressId(0L);
        getDataManager().setCurrentUserId(0L);
        getDataManager().setCurrentAddress(null);
        getDataManager().setCurrentAddressArea(null);
        getDataManager().setCurrentAddressTitle(null);
        getDataManager().setCurrentLat(0.0);
        getDataManager().setCurrentLng(0.0);


        getDataManager().updateCurrentAddress(null,null,0.0,0.0,null,0L);
        getDataManager().updateUserGender(false);
        getDataManager().updateEmailStatus(false);
        getDataManager().updateUserPasswordStatus(false);
        getDataManager().showFunnel(false);
        getDataManager().saveMaster(null);
        getDataManager().saveFilterSort(null);
        getDataManager().saveStoriesList(null);
        getDataManager().saveVegType(0);
        getDataManager().currentFragment(0);
        getDataManager().kitchenId(0L);
        getDataManager().totalOrders(0);
        getDataManager().saveRefundId(0);
        getDataManager().saveRazorpayCustomerId(null);
        getDataManager().saveRefundBalance(0);
        getDataManager().saveRegionId(0);
        getDataManager().saveCouponId(0);
        getDataManager().saveRatingOrderId(0L);
        getDataManager().saveRatingSkipDate(null,0);
        getDataManager().saveRatingSkipDate(0);
        getDataManager().saveRatingAppStatus(false);
        getDataManager().saveIsFilterApplied(false);
        getDataManager().saveApiToken(null);
        getDataManager().saveCouponCode(null);
        getDataManager().saveSupportNumber(null);
        getDataManager().orderInstruction(null);
        getDataManager().currentOrderId(0L);
        getDataManager().homeAddressadded(false);
        getDataManager().officeAddressadded(false);
        getDataManager().isFavClicked(false);
        getDataManager().appStartedAgain(false);
        getDataManager().saveFirstLocation(null,null,null);


    }

    public void fetchUserDetails() {

        userName.set(getDataManager().getCurrentUserName());
        userEmail.set(getDataManager().getCurrentUserEmail());
        userPhoneNo.set(getDataManager().getCurrentUserPhNo());


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            Long userId = getDataManager().getCurrentUserId();
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_GET_USER_DETAILS + userId, GetUserDetailsResponse.class, new Response.Listener<GetUserDetailsResponse>() {
                @Override
                public void onResponse(GetUserDetailsResponse response) {
                    try {
                        if (response != null && response.getResult() != null && response.getResult().size() > 0) {

                            getUserDetailsResponse = response;

                            setIsLoading(false);
                            //  String strUserName = response.getResult().get(0).getName();
                            // String strUserEmail = response.getResult().get(0).getEmail();
                            //  String strUserPhNo = response.getResult().get(0).getPhoneno();
                            //  getDataManager().updateUserInformation(userId, strUserName, strUserEmail, strUserPhNo, null);

                            //  userName.set(getDataManager().getCurrentUserName());
                            //  userEmail.set(getDataManager().getCurrentUserEmail());
                            // userPhoneNo.set(getDataManager().getCurrentUserPhNo());
                            if (response.getResult() != null && response.getResult().size() > 0) {

                                if (response.getResult().get(0).getRegionid()==0){
                                    regionname.set(response.getResult().get(0).getOtherHometown());
                                }else {
                                    regionname.set(response.getResult().get(0).getRegionname());

                                }


                             //   regionname.set(response.getResult().get(0).getRegionname());

                                if (response.getResult().get(0).getGender() == 1) {

                                    gender.set("M");
                                } else {

                                    gender.set("F");

                                }
                            }

                        }

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        setIsLoading(false);
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
