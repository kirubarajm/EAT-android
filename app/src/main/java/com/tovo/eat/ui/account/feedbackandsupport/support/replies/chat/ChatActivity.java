package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityChatBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class ChatActivity extends BaseActivity<ActivityChatBinding, ChatActivityViewModel> implements ChatActivityNavigator, ChatAdapter.ChatAdapterListener {

    ActivityChatBinding mActivityChatBinding;
    @Inject
    ChatActivityViewModel mChatActivityViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ChatAdapter mChatAdapter;
    String strQId = "";
    String strQuestion = "";
    String strDate = "";
    Analytics analytics;
    String pageName = AppConstants.SCREEN_QUERY_CHAT;

    List<ChatRepliesReadRequest.Aidlist> mChatRepliesReadRequest = new ArrayList<>();
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, ChatActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.chatViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public ChatActivityViewModel getViewModel() {
        return mChatActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChatBinding = getViewDataBinding();
        mChatActivityViewModel.setNavigator(this);
        mChatAdapter.setListener(this);

        // Save screen view to analytics
        analytics = new Analytics(this, pageName);
        //Start page loader
        mActivityChatBinding.loader.setVisibility(View.VISIBLE);
        //initialize recyclerview
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityChatBinding.recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        mActivityChatBinding.recyclerChat.setAdapter(mChatAdapter);
        //Subscribe mutable live data to array list
        subscribeToLiveData();
    }

    private void subscribeToLiveData() {
        mChatActivityViewModel.getOrders().observe(this, chatItemViewModel -> mChatActivityViewModel.addChatItemsToList(chatItemViewModel));
    }

    @Override
    public void handleError() {

    }

    @Override
    public void send() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_SEND);
        String strMessage = mActivityChatBinding.edtMessage.getText().toString();
        if (!strMessage.equals("")) {
            mChatActivityViewModel.insertAnswerServiceCall(strMessage, strQId);
            //Send click to analytics
            new Analytics().queriesChat(strQuestion, strMessage);
        } else {
            Toast.makeText(this, AppConstants.TOAST_ENTER_REPLY_TO_SEND, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshLayout() {
        mChatActivityViewModel.fetchChatServiceCall(strQId, 0);
        new Analytics().sendClickData(pageName, AppConstants.CLICK_REFRESH);
    }

    @Override
    public void apiLoaded() {
        mActivityChatBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshSuccess(List<ChatRepliesReadRequest.Aidlist> aidlist) {
        mActivityChatBinding.swipeChat.setRefreshing(false);
        mActivityChatBinding.edtMessage.setText("");
        mChatActivityViewModel.readMessageServiceCall(aidlist);
    }

    @Override
    public void onRefreshFailure(String strFailure) {
        mActivityChatBinding.swipeChat.setRefreshing(false);
        mActivityChatBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void sendSuccess(String strFailure) {
        Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();
        mActivityChatBinding.edtMessage.setText("");
        mChatActivityViewModel.fetchChatServiceCall(strQId, 1);
    }


    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strQId = bundle.getString("qId");
            strQuestion = bundle.getString("question");
            strDate = bundle.getString("date");
            mActivityChatBinding.txtQuestion.setText(strQuestion + "?");

            mChatActivityViewModel.fetchChatServiceCall(strQId, 1);
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy" + " | " + "hh:mm a");
                DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String outputDateStr = "";
                //Date  date1 = new Date(strDate);
                Date date = currentFormat.parse(strDate);
                outputDateStr = dateFormat.format(date);
                mActivityChatBinding.txtDate.setText(outputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


}
