package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRepliesBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatActivity;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class RepliesActivity extends BaseActivity<ActivityRepliesBinding, RepliesActivityViewModel> implements RepliesActivityNavigator,RepliesAdapter.RepliesAdapterListener {


    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    RepliesAdapter mRepliesAdapter;
    @Inject
    RepliesActivityViewModel mRepliesActivityViewModel;

    private ActivityRepliesBinding mActivityRepliesBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RepliesActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.repliesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_replies;
    }

    @Override
    public RepliesActivityViewModel getViewModel() {
        return mRepliesActivityViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
        finish();
    }

    @Override
    public void next() {

    }

    @Override
    public void onFrefresh() {
        mRepliesActivityViewModel.fetchQueryListServiceCall(1);
    }

    @Override
    public void onRefreshSuccess() {
        mActivityRepliesBinding.swipeQueries.setRefreshing(false);
    }

    @Override
    public void onRefreshFailure() {
        mActivityRepliesBinding.swipeQueries.setRefreshing(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRepliesBinding = getViewDataBinding();
        mRepliesActivityViewModel.setNavigator(this);
        mRepliesAdapter.setListener(this);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRepliesBinding.recyclerReplies.setLayoutManager(new LinearLayoutManager(this));
        mActivityRepliesBinding.recyclerReplies.setAdapter(mRepliesAdapter);
        subscribeToLiveData();

        setTitle("Questions");
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityRepliesBinding.toolbar1.setTitle("Questions");
    }

    private void subscribeToLiveData() {
        mRepliesActivityViewModel.getReplies().observe(this, repliesItemViewModels -> mRepliesActivityViewModel.addRepliesItemsToList(repliesItemViewModels));
    }

    @Override
    public void chatList(RepliesResponse.Result replies) {
        Log.e("orders",replies.toString());
        String strQid= String.valueOf(replies.getQid());
        Log.e("orders", strQid);

        Intent intent = ChatActivity.newIntent(this);
        intent.putExtra("qId",strQid);
        intent.putExtra("question",replies.getQuestion());
        intent.putExtra("date",replies.getCreatedAt());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

}
