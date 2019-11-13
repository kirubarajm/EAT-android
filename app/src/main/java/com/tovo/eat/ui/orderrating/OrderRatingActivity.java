package com.tovo.eat.ui.orderrating;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderRatingBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class OrderRatingActivity extends BaseBottomSheetFragment<ActivityOrderRatingBinding, OrderRatingActivityViewModel>
        implements OrderRatingActivityNavigator {

    int foodRating = 0;
    int deliveryRating = 0;
    long orderId = 0;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_RATING;


    @Inject
    OrderRatingActivityViewModel mLoginViewModelMain;
    private ActivityOrderRatingBinding mActivityOrderRatingBinding;

    public static OrderRatingActivity newInstance() {
        Bundle args = new Bundle();
        OrderRatingActivity fragment = new OrderRatingActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void submit() {
        if (validForRating()) {

            new Analytics().sendClickData(AppConstants.SCREEN_ORDER_RATING, AppConstants.CLICK_SUBMIT);

            String strFood = mActivityOrderRatingBinding.edtFood.getText().toString();
            String strDelivery = mActivityOrderRatingBinding.edtDelivery.getText().toString();
            mLoginViewModelMain.orderRatingSubmit(foodRating, deliveryRating, strFood, strDelivery);
        }
    }

    @Override
    public void ratingSuccess() {
        dismiss();
        //  Toast.makeText(getContext(), "Thanks for your feedback", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ratingFailure() {
        Toast.makeText(MvvmApp.getInstance(), "Rating Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void foodSmileyLow() {
        foodRating = 1;
        mActivityOrderRatingBinding.smileyFoodLow.setTextColor(Color.RED);
        mActivityOrderRatingBinding.smileyFoodMedium.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyFoodHigh.setTextColor(Color.BLACK);
    }

    @Override
    public void foodSmileyMedium() {
        foodRating = 2;
        mActivityOrderRatingBinding.smileyFoodLow.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyFoodMedium.setTextColor(Color.parseColor("#FFA500"));
        mActivityOrderRatingBinding.smileyFoodHigh.setTextColor(Color.BLACK);
    }

    @Override
    public void foodSmileyHigh() {
        foodRating = 3;
        mActivityOrderRatingBinding.smileyFoodLow.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyFoodMedium.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyFoodHigh.setTextColor(Color.GREEN);
    }

    @Override
    public void deliverySmileyLow() {
        deliveryRating = 1;
        mActivityOrderRatingBinding.smileyDeliveryLow.setTextColor(Color.RED);
        mActivityOrderRatingBinding.smileyDeliveryMedium.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyDeliveryHigh.setTextColor(Color.BLACK);
    }

    @Override
    public void deliverySmileyMedium() {
        deliveryRating = 2;
        mActivityOrderRatingBinding.smileyDeliveryLow.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyDeliveryMedium.setTextColor(Color.parseColor("#FFA500"));
        mActivityOrderRatingBinding.smileyDeliveryHigh.setTextColor(Color.BLACK);
    }

    @Override
    public void deliverySmileyHigh() {
        deliveryRating = 3;
        mActivityOrderRatingBinding.smileyDeliveryLow.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyDeliveryMedium.setTextColor(Color.BLACK);
        mActivityOrderRatingBinding.smileyDeliveryHigh.setTextColor(Color.GREEN);
    }

    @Override
    public void maybeLater() {
        dismiss();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderRatingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_rating;
    }

    @Override
    public OrderRatingActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModelMain.setNavigator(this);

        analytics = new Analytics(getActivity(), pageName);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityOrderRatingBinding = getViewDataBinding();


        if (getArguments() != null) {
            mLoginViewModelMain.order.set("Order #" + getArguments().getLong("orderid"));
            mLoginViewModelMain.kitchen.set(getArguments().getString("brandname"));
            mLoginViewModelMain.orderID = getArguments().getInt("orderid");

        }


    }


    private boolean validForRating() {
        if (foodRating == 0) {
            Toast.makeText(getContext(), "Please give food rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (deliveryRating == 0) {
            Toast.makeText(getContext(), "Please give delivery rating", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
