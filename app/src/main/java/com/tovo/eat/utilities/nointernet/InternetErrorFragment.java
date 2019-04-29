package com.tovo.eat.utilities.nointernet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentNoInternetBinding;
import com.tovo.eat.ui.base.BaseFragment;

import javax.inject.Inject;


public class InternetErrorFragment extends BaseFragment<FragmentNoInternetBinding, InternetErrorViewModel> implements InternetErrorNavigator {


    public static final String TAG = InternetErrorFragment.class.getSimpleName();
    @Inject
    InternetErrorViewModel mInternetErrorViewModel;

    FragmentNoInternetBinding mFragmentNoInternetBinding;


    InternetListener internetListener;

    public static InternetErrorFragment newInstance() {
        Bundle args = new Bundle();
        InternetErrorFragment fragment = new InternetErrorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        internetListener = (InternetListener) context;
        super.onAttach(context);
    }

    @Override
    public int getBindingVariable() {
        return BR.internetErrorViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_no_internet;
    }

    @Override
    public InternetErrorViewModel getViewModel() {
        //  mFoodMenuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FoodMenuViewModel.class);
        return mInternetErrorViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInternetErrorViewModel.setNavigator(this);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentNoInternetBinding = getViewDataBinding();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void retry() {
        internetListener.isInternet(mInternetErrorViewModel.checkInternet());
    }

}
