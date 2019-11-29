package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChatActivityViewModel extends BaseViewModel<ChatActivityNavigator> {

    public ObservableList<ChatResponse.Result> chatItemViewModels = new ObservableArrayList<>();
    Response.ErrorListener errorListener;
    List<ChatResponse.Result> chatListUserRead = new ArrayList<>();
    List<ChatRepliesReadRequest.Aidlist> chatListUserReadFinal = new ArrayList<>();
    ChatRepliesReadRequest.Aidlist aidlist;
    Long userId;
    private MutableLiveData<List<ChatResponse.Result>> chatItemsLiveData;

    public ChatActivityViewModel(DataManager dataManager) {
        super(dataManager);
        userId = getDataManager().getCurrentUserId();
        chatItemsLiveData = new MutableLiveData<>();
    }

    public void fetchChatServiceCall(String strQid, int val) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        if (val == 1) {
            setIsLoading(true);
        }
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_REPLIES_CHAT + strQid, ChatResponse.class, new Response.Listener<ChatResponse>() {
                @Override
                public void onResponse(ChatResponse response) {
                    if (response != null && response.getResult().size() > 0) {
                        chatItemsLiveData.setValue(response.getResult());
                        chatListUserRead.addAll(response.getResult());
                        if (val == 1) {
                            setIsLoading(false);
                        }
                        for (int i = 0; i < chatListUserRead.size(); i++) {
                            if (chatListUserRead.get(i).getUserRead().equals(0)) {
                                aidlist = new ChatRepliesReadRequest.Aidlist();
                                aidlist.setAid(chatListUserRead.get(i).getAid());
                                chatListUserReadFinal.add(aidlist);
                            }
                        }
                        getNavigator().onRefreshSuccess(chatListUserReadFinal);
                    }
                    if (getNavigator() != null)
                        getNavigator().apiLoaded();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        if (val == 1) {
                            setIsLoading(false);
                        }
                        if (getNavigator() != null)
                            getNavigator().onRefreshFailure("");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void insertAnswerServiceCall(String strMessage, String strQid) {
        Long qId = Long.parseLong(strQid);
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_CHAT_ANSWER, ChatReplyResponse.class,
                    new ChatReplyRequest(qId, strMessage, AppConstants.EAT, AppConstants.ADMIN, userId), new Response.Listener<ChatReplyResponse>() {
                @Override
                public void onResponse(ChatReplyResponse response) {
                    if (response != null) {
                        boolean success = response.getSuccess();
                        String strMessage = response.getMessage();
                        if (getNavigator() != null)
                            getNavigator().sendSuccess(strMessage);
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().sendSuccess(strMessage);
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void readMessageServiceCall(List<ChatRepliesReadRequest.Aidlist> mChatRepliesReadRequest) {
        setIsLoading(true);
        List<ChatRepliesReadRequest.Aidlist> itemid = new ArrayList<>();
        itemid = mChatRepliesReadRequest;
        ChatRepliesReadRequest dialogPickedUpRequest = new ChatRepliesReadRequest(itemid);
        Gson gson = new GsonBuilder().create();
        String payloadStr = gson.toJson(dialogPickedUpRequest);

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, AppConstants.URL_CHAT_REPLIES_READ, new JSONObject(payloadStr), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    setIsLoading(false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };

            MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } catch (Exception ee) {

            ee.printStackTrace();
        }
    }

    public void sendClick() {
        getNavigator().send();
    }

    public void goBack() {
        getNavigator().goBack();
    }

    public void onRefreshLayout() {
        getNavigator().onRefreshLayout();
    }

    public void addChatItemsToList(List<ChatResponse.Result> ordersItems) {
        chatItemViewModels.clear();
        chatItemViewModels.addAll(ordersItems);
    }

    public ObservableList<ChatResponse.Result> getChatItemViewModels() {
        return chatItemViewModels;
    }

    public MutableLiveData<List<ChatResponse.Result>> getOrders() {
        return chatItemsLiveData;
    }

}
