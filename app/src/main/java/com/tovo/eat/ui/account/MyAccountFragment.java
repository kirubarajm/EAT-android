package com.tovo.eat.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentMyAccountBinding;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivity;
import com.tovo.eat.ui.account.feedbackandsupport.FeedbackAndSupportActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.referrals.ReferralsActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;

import javax.inject.Inject;


public class MyAccountFragment extends BaseFragment<FragmentMyAccountBinding, MyAccountViewModel> implements MyAccountNavigator {


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




        //   ((MainActivity) getActivity()).setActionBarTitle("My Account");

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

        //  ((MainActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void manageAddress() {
        Intent intent= AddressListActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void orderHistory() {
        Intent intent = OrderHistoryActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void favourites() {
        /*FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        FavoritesTabActivity fragment = new FavoritesTabActivity();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();*/

        Intent intent = FavoritesTabActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void referrals() {
        Intent intent = ReferralsActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void offers() {

    }

    @Override
    public void feedbackAndSupport() {

        Intent intent = FeedbackAndSupportActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void editProfile() {

    }


}
