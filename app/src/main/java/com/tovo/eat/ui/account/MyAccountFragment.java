package com.tovo.eat.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentMyAccountBinding;
import com.tovo.eat.ui.account.edit.EditAccountActivity;
import com.tovo.eat.ui.account.feedbackandsupport.FeedbackAndSupportActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.referrals.ReferralsActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.GetUserDetailsResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;


public class MyAccountFragment extends BaseBottomSheetFragment<FragmentMyAccountBinding, MyAccountViewModel> implements MyAccountNavigator {

    public static final String TAG = MyAccountFragment.class.getSimpleName();
    @Inject
    MyAccountViewModel mMyAccountViewModel;

    FragmentMyAccountBinding mFragmentMyAccountBinding;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_MY_ACCOUNT;


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
        return mMyAccountViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentMyAccountBinding = getViewDataBinding();
        mMyAccountViewModel.setNavigator(this);
        analytics = new Analytics(getActivity(), pageName);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMyAccountViewModel.toolbarTitle.set(getString(R.string.my_account));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMyAccountViewModel.fetchUserDetails();
        mMyAccountViewModel.loadUserDetails();

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void manageAddress() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_MANAGE_ADDRESS);

        Intent intent = AddressListActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void orderHistory() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_ORDER_HISTORY);
        Intent intent = OrderHistoryActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void favourites() {
    }

    @Override
    public void referrals() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_REFERRALS);
        Intent intent = ReferralsActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void offers() {
    }

    @Override
    public void logout() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_LOGOUT);


      /*  SharedPreferences settings = getBaseActivity().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().apply();*/

        Intent intent = SignUpActivity.newIntent(getActivity());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
       // getActivity().finish();
    }

    @Override
    public void feedbackAndSupport() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_FEEDBACK_SUPPORT);

        Intent intent = FeedbackAndSupportActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void editProfile(GetUserDetailsResponse getUserDetailsResponse) {
        if (getUserDetailsResponse.getResult() != null && getUserDetailsResponse.getResult().size() > 0) {
            Intent intent = EditAccountActivity.newIntent(getContext());
            intent.putExtra("name", getUserDetailsResponse.getResult().get(0).getName());
            intent.putExtra("email", getUserDetailsResponse.getResult().get(0).getEmail());
            intent.putExtra("gender", getUserDetailsResponse.getResult().get(0).getGender());
            intent.putExtra("hometown", getUserDetailsResponse.getResult().get(0).getHometownName());
            intent.putExtra("hometownid", getUserDetailsResponse.getResult().get(0).getHometownId());
            intent.putExtra("other_hometown", getUserDetailsResponse.getResult().get(0).getOtherHometown());
            startActivity(intent);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();


    }
}
