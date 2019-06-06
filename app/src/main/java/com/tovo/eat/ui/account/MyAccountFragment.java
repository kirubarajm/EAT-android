package com.tovo.eat.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentMyAccountBinding;
import com.tovo.eat.ui.account.edit.EditAccountActivity;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivity;
import com.tovo.eat.ui.account.feedbackandsupport.FeedbackAndSupportActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.referrals.ReferralsActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.GetUserDetailsResponse;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;


public class MyAccountFragment extends BaseBottomSheetFragment<FragmentMyAccountBinding, MyAccountViewModel> implements MyAccountNavigator {

    public static final String TAG = MyAccountFragment.class.getSimpleName();
    @Inject
    MyAccountViewModel mMyAccountViewModel;

    FragmentMyAccountBinding mFragmentMyAccountBinding;

    public static MyAccountFragment newInstance() {
        Bundle args = new Bundle();
        MyAccountFragment fragment = new MyAccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.myAccountViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_account;
    }

    @Override
    public MyAccountViewModel getViewModel() {
        //  mFoodMenuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FoodMenuViewModel.class);
        return mMyAccountViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentMyAccountBinding = getViewDataBinding();
        mMyAccountViewModel.setNavigator(this);


        //   ((FilterActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // mFragmentMyAccountBinding = getViewDataBinding();

        mMyAccountViewModel.toolbarTitle.set(getString(R.string.my_account));
    }

    @Override
    public void onResume() {
        super.onResume();


        mMyAccountViewModel.fetchUserDetails();

        //  ((FilterActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void manageAddress() {
        Intent intent = AddressListActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void orderHistory() {
        Intent intent = OrderHistoryActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void favourites() {





        Intent intent=FavoritesTabActivity.newIntent(getContext());
        startActivity(intent);



/*
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FavoritesTabActivity fragment = new FavoritesTabActivity();
        transaction.replace(R.id.content_main, fragment);
       // transaction.addToBackStack(FavoritesTabActivity.class.getSimpleName());
      //  transaction.addToBackStack(null);
        transaction.commitNow();
        // transaction.commitNowAllowingStateLoss();*/



    }

    @Override
    public void referrals() {
        Intent intent = ReferralsActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void offers() {
       /* Intent intent = RegistrationActivity.newIntent(getContext());
        startActivity(intent);*/
    }

    @Override
    public void logout() {
        mMyAccountViewModel.logOutSession();

        SharedPreferences settings = getBaseActivity().getSharedPreferences(AppConstants.PACKAGE_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();


        startActivity(SignUpActivity.newIntent(getActivity()));
        getActivity().finish();
    }

    @Override
    public void feedbackAndSupport() {
        Intent intent = FeedbackAndSupportActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void editProfile(GetUserDetailsResponse getUserDetailsResponse) {
        Intent intent = EditAccountActivity.newIntent(getContext());
        intent.putExtra("name",getUserDetailsResponse.getResult().get(0).getName());
        intent.putExtra("email",getUserDetailsResponse.getResult().get(0).getEmail());
        intent.putExtra("gender",getUserDetailsResponse.getResult().get(0).getGender());
        intent.putExtra("region",getUserDetailsResponse.getResult().get(0).getRegionname());
        intent.putExtra("regionid",getUserDetailsResponse.getResult().get(0).getRegionid());
        startActivity(intent);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
