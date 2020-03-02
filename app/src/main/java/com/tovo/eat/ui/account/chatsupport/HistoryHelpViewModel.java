package com.tovo.eat.ui.account.chatsupport;

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
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.track.help.OrderCancelRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.chat.IssuesListResponse;
import com.tovo.eat.utilities.chat.IssuesRequest;
import com.tovo.eat.utilities.chat.MapOrderidChatRequest;

import java.util.List;

public class HistoryHelpViewModel extends BaseViewModel<HistoryHelpNavigator> {

    public ObservableList<IssuesListResponse.Result> issuesObservableList = new ObservableArrayList<>();
    public MutableLiveData<List<IssuesListResponse.Result>> issuesLiveData;

public Long orderid=0L;

    public HistoryHelpViewModel(DataManager dataManager) {
        super(dataManager);
        issuesLiveData = new MutableLiveData<>();

        getIssuesList(2);
    }

    public void goBack() {
        getNavigator().goBack();
    }




    public MutableLiveData<List<IssuesListResponse.Result>> getIssuesLiveData() {
        return issuesLiveData;
    }


    public void addIssuesListItemsToList(List<IssuesListResponse.Result> issuesList) {
        issuesObservableList.clear();
        issuesObservableList.addAll(issuesList);
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
    public void getIssuesNote(int type,int issueid) {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {

            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_ISSUES_NOTE_URL, IssuesListResponse.class, new IssuesRequest(2,issueid,getDataManager().getCurrentUserId(),orderid), new Response.Listener<IssuesListResponse>() {
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
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_CHAT_MAP_ORDERID, CommonResponse.class, new MapOrderidChatRequest(getDataManager().getCurrentUserId(),getDataManager().getOrderId(),issueid,2), new Response.Listener<CommonResponse>() {
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
