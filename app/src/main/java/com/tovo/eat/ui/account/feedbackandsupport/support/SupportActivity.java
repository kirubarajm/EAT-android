package com.tovo.eat.ui.account.feedbackandsupport.support;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityQueriesBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import javax.inject.Inject;

public class SupportActivity extends BaseActivity<ActivityQueriesBinding, SupportActivityViewModel> implements SupportActivityNavigator {

    @Inject
    SupportActivityViewModel mQueriesViewModel;
    private ActivityQueriesBinding mActivityQueriesBinding;
    String strQueries="";
    public static final String TAG = SupportActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, SupportActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.queriesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_queries;
    }

    @Override
    public SupportActivityViewModel getViewModel() {
        return mQueriesViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
        finish();
    }

    @Override
    public void repliesOnClick() {
        Intent intent = RepliesActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void onRefreshLayout() {
        mQueriesViewModel.fetchCountSertviceCall(0);
    }

    @Override
    public void submit() {
        strQueries=mActivityQueriesBinding.edtQueries.getText().toString();
        if (!strQueries.equals("")) {
            mQueriesViewModel.insertQueriesServiceCall(strQueries);
        }else {
            Toast.makeText(this, AppConstants.TOAST_ENTER_QUERY_TO_SEND, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(String strSuccess) {
        Toast.makeText(this, strSuccess, Toast.LENGTH_SHORT).show();
        mActivityQueriesBinding.edtQueries.setText("");
    }

    @Override
    public void failure(String strFailure) {
        Toast.makeText(this, strFailure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshSuccess() {
        mActivityQueriesBinding.swipeQueries.setRefreshing(false);
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshFailure() {
        mActivityQueriesBinding.swipeQueries.setRefreshing(false);
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityQueriesBinding = getViewDataBinding();
        mQueriesViewModel.setNavigator(this);

        setTitle("Support");
        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityQueriesBinding.toolbar1.setTitle("Support");
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
