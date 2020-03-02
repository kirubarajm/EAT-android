package com.tovo.eat.ui.track.help;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.chat.IssuesListResponse;
import com.tovo.eat.utilities.chat.IssuesRequest;
import com.tovo.eat.utilities.chat.MapOrderidChatRequest;

import java.util.List;

public class OrderHelpViewModel extends BaseViewModel<OrderHelpNavigator> {

    public final ObservableField<String> deliveryName = new ObservableField<>();
    public final ObservableField<String> deliveryNumber = new ObservableField<>();
    public final ObservableField<String> serviceCharges = new ObservableField<>();
    public final ObservableField<String> cancelationMessage = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();


    public ObservableBoolean cancelClicked = new ObservableBoolean();
    public ObservableBoolean deliveryClicked = new ObservableBoolean();
    public ObservableBoolean deliveryAssigned = new ObservableBoolean();
    public ObservableBoolean otherReason = new ObservableBoolean();
    public ObservableBoolean showCancel = new ObservableBoolean();


    public ObservableList<IssuesListResponse.Result> issuesObservableList = new ObservableArrayList<>();
    public MutableLiveData<List<IssuesListResponse.Result>> issuesLiveData;



    public OrderHelpViewModel(DataManager dataManager) {
        super(dataManager);
        otherReason.set(false);
        issuesLiveData = new MutableLiveData<>();

        getIssuesList(1);
    }

    public void goBack() {
        getNavigator().goBack();
    }

    public void cancelDetails() {

        if (cancelClicked.get()) {
            cancelClicked.set(false);
        } else {
            cancelClicked.set(true);
            deliveryClicked.set(false);
        }


    }


    public MutableLiveData<List<IssuesListResponse.Result>> getIssuesLiveData() {
        return issuesLiveData;
    }

    public void setIssuesLiveData(MutableLiveData<List<IssuesListResponse.Result>> issuesLiveData) {
        this.issuesLiveData = issuesLiveData;
    }

    public void addIssuesListItemsToList(List<IssuesListResponse.Result> issuesList) {
        issuesObservableList.clear();
        issuesObservableList.addAll(issuesList);
    }


    public void deliveryDetails() {
        if (deliveryAssigned.get()) {

            if (deliveryClicked.get()) {
                deliveryClicked.set(false);

            } else {
                cancelClicked.set(false);
                deliveryClicked.set(true);
            }


            cancelClicked.set(false);
            deliveryClicked.set(true);


        } else {

            getNavigator().showToast("Delivery executive yet to be assigned");
        }

    }

    public void contactCare() {
        if (getNavigator() != null)
            getNavigator().gotoSupport();

    }

    public void supportQuery() {
        if (getNavigator() != null)
            getNavigator().gotoSupport();
           // getNavigator().createChat(getDataManager().getCurrentUserName(),getDataManager().getCurrentUserEmail(),getDataManager().getCurrentUserPhNo(),"temp note");
    }

    public void calldelivery() {
        if (getNavigator() != null)
            getNavigator().callDelivery();
    }


    public void cancelOrderButton() {
        if (getNavigator() != null)
            getNavigator().orderCancelClicked();
    }


    public void cancelOrder1() {
        cancelOrder(AppConstants.CANCEL_ORDER_MEAASAGE_1);
    }

    public void cancelOrder2() {

        cancelOrder(AppConstants.CANCEL_ORDER_MEAASAGE_2);

    }

    public void cancelOrder3() {

        cancelOrder(AppConstants.CANCEL_ORDER_MEAASAGE_3);

    }


    public void cancelOrder(String reason) {


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {


            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_CANCEL_ORDER, CommonResponse.class, new OrderCancelRequest(getDataManager().getOrderId(), reason), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        Toast.makeText(MvvmApp.getInstance(), response.getMessage(), Toast.LENGTH_SHORT).show();

                        if (response.isStatus()) {
                            if (getNavigator() != null)
                                getNavigator().orderCanceled();
                        } else {
                            if (getNavigator() != null)
                                getNavigator().orderCancelFailed();
                        }


                    } else {
                        if (getNavigator() != null)
                            getNavigator().orderCancelFailed();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().orderCancelFailed();
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }
 public void getIssuesList(int type) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_ISSUES_URL, IssuesListResponse.class, new IssuesRequest(type,getDataManager().getCurrentUserId()), new Response.Listener<IssuesListResponse>() {
                @Override
                public void onResponse(IssuesListResponse response) {
                    if (response != null) {
                        if (response.getResult()!=null&&response.getResult().size()>0) {
                            issuesLiveData.setValue(response.getResult());

                        }

                    }
                    showCancel.set(true);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    showCancel.set(true);
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().orderCancelFailed();
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


    public void getIssuesNote(int type,int issueid) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_ISSUES_NOTE_URL, IssuesListResponse.class, new IssuesRequest(1,issueid,getDataManager().getCurrentUserId(),getDataManager().getOrderId()), new Response.Listener<IssuesListResponse>() {
                @Override
                public void onResponse(IssuesListResponse response) {
                    if (response != null) {
                        if (response.getResult()!=null&&response.getResult().size()>0) {

                            if (getNavigator()!=null)
                                getNavigator().mapChat(response.getResult().get(0).getDepartmentName(),response.getResult().get(0).getTagName(),response.getResult().get(0).getNote(),issueid,response.getResult().get(0).getTid());
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);

                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


public void mapTicketidToOrderid(int issueid,int tid,String tag,String department,String note) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_MAP_ORDERID, CommonResponse.class, new MapOrderidChatRequest(getDataManager().getCurrentUserId(),getDataManager().getOrderId(),issueid,1), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {
                        if (response.isStatus()){
                            if (getNavigator()!=null)
                                getNavigator().createChat(department,tag,note);
                        }

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);

                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


}
