package com.tovo.eat.ui.home.homemenu.story;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityStoriesBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

public class StoriesActivity extends BaseActivity<ActivityStoriesBinding, StoriesActivityViewModel>
        implements StoriesActivityNavigator {

    @Inject
    StoriesActivityViewModel mStoriesActivityViewModel;

    private ActivityStoriesBinding mActivityStoriesBinding;

    StoriesResponse.Result result;


    public static Intent newIntent(Context context) {
        return new Intent(context, StoriesActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.storiesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_stories;
    }

    @Override
    public StoriesActivityViewModel getViewModel() {
        return mStoriesActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityStoriesBinding = getViewDataBinding();
        mStoriesActivityViewModel.setNavigator(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle!=null){
            result = (StoriesResponse.Result) bundle.getSerializable("stories");
        }
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

}
