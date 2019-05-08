package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityChatBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
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
    List<ChatRepliesReadRequest.Aidlist> mChatRepliesReadRequest = new ArrayList<>();

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

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityChatBinding.recyclerChat.setLayoutManager(new LinearLayoutManager(this));
        mActivityChatBinding.recyclerChat.setAdapter(mChatAdapter);
        subscribeToLiveData();



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strQId = bundle.getString("qId");
            strQuestion = bundle.getString("question");
            strDate = bundle.getString("date");
            mActivityChatBinding.txtQuestion.setText(strQuestion+"?");

            mChatActivityViewModel.fetchChatServiceCall(strQId, 1);
            try {
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy" +" | "+"hh:mm a");
                DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String outputDateStr = "";
                //Date  date1 = new Date(strDate);
                Date date = currentFormat.parse(strDate);
                outputDateStr = dateFormat.format(date);
                mActivityChatBinding.txtDate.setText(outputDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    private void subscribeToLiveData() {
        mChatActivityViewModel.getOrders().observe(this, chatItemViewModel -> mChatActivityViewModel.addChatItemsToList(chatItemViewModel));
    }

    @Override
    public void ordersHistory(ChatResponse.Result orders) {

    }

    @Override
    public void ordersDetailed(ChatResponse.Result orders) {

    }

    @Override
    public void handleError() {

    }

    @Override
    public void send() {
        String strMessage = mActivityChatBinding.edtMessage.getText().toString();
        if (!strMessage.equals("")) {
            mChatActivityViewModel.insertAnswerServiceCall(strMessage, strQId);
        }else {
            Toast.makeText(this, AppConstants.TOAST_ENTER_REPLY_TO_SEND, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshLayout() {
        mChatActivityViewModel.fetchChatServiceCall(strQId, 0);
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
    }

    @Override
    public void sendSuccess(String strFailure) {
        Toast.makeText(this, "message sent", Toast.LENGTH_SHORT).show();
        mActivityChatBinding.edtMessage.setText("");
        mChatActivityViewModel.fetchChatServiceCall(strQId,1);
    }

    @Override
    public void sendFailure(String strFailure) {
        Toast.makeText(this, "message not sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }
}
