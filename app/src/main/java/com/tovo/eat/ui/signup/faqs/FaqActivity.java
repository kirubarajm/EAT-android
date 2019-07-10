package com.tovo.eat.ui.signup.faqs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFaqsBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class FaqActivity extends BaseActivity<ActivityFaqsBinding, FaqFragmentViewModel> implements FaqFragmentNavigator {

    public static final String TAG = FaqActivity.class.getSimpleName();
    @Inject
    FaqFragmentViewModel mFaqViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    FaqsAdapter mFaqsAdapter;
    private ActivityFaqsBinding mActivityFaqsBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, FaqActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.faqsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs;
    }

    @Override
    public FaqFragmentViewModel getViewModel() {
        return mFaqViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
        finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqViewModel.setNavigator(this);
        mActivityFaqsBinding = getViewDataBinding();



        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityFaqsBinding.recyclerFaqs.setLayoutManager(mLayoutManager);
        mActivityFaqsBinding.recyclerFaqs.setAdapter(mFaqsAdapter);
        subscribeToLiveData();
    }


    private void subscribeToLiveData() {
        mFaqViewModel.getFaqs().observe(this,
                FaqFragmentViewModel -> mFaqViewModel.addFaqsItemsToList(FaqFragmentViewModel));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
