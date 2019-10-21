package com.tovo.eat.ui.account.favorites.dialog;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.databinding.DialogChangeKitchenBinding;
import com.tovo.eat.ui.base.BaseDialog;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchenCallBack;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchenViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DialogChangeKitchen extends BaseDialog implements DialogChangeKitchenCallBack {

    private static final String TAG = DialogChangeKitchen.class.getSimpleName();
    String strProductId = "";
    @Inject
    DialogChangeKitchenViewModel mDialogChangeKitchenViewModel;
    DialogChangeKitchenBinding binding;
    Activity activity;
    Analytics analytics;
    String  pageName=AppConstants.SCREEN_CHANGE_KITCHEN;
    Integer  productId,quantity,price;
    Long makeitId;
    AddKitchenDishListener addKitchenDishListener;

    public DialogChangeKitchen() {

    }

    public static DialogChangeKitchen newInstance() {
        DialogChangeKitchen fragment = new DialogChangeKitchen();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
       // fragment.setTargetFragment(new DialogRefundAlert(),0);
        fragment.setCancelable(false);
        return fragment;
    }

    public static DialogChangeKitchen newInstance(DialogChangeKitchen fragment) {
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        addKitchenDishListener =(AddKitchenDishListener)context;

        super.onAttach(context);
    }

    public void show(FragmentManager fragmentManager, Activity activity, Long makeitId, Integer productId, Integer quantity, Integer price) {
        this.activity = activity;
        this.makeitId=makeitId;
        this.productId=productId;
        this.quantity=quantity;
        this.price=price;
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_DISH,AppConstants.CLICK_CHANGE_KITCHEN_CANCEL);
        dismissDialog(TAG);
    }

    @Override
    public void confirmClick() {
        dismissDialog();
        //add item to cart
        mDialogChangeKitchenViewModel.addToCart(makeitId,productId,quantity,price);
        addKitchenDishListener.confirmClick(true);
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_DISH,AppConstants.CLICK_CHANGE_KITCHEN_CONFIRM);

    }

    @Override
    public void cancelClick() {
        addKitchenDishListener.confirmClick(false);
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_DISH,AppConstants.CLICK_CHANGE_KITCHEN_CANCEL);
        dismissDialog();
    }

    public void show(FragmentManager fragmentManager, String strProductId) {
        super.show(fragmentManager, TAG);
        this.strProductId = strProductId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics=new Analytics(getBaseActivity(),pageName);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_change_kitchen, container, false);
        View view = binding.getRoot();
        AndroidSupportInjection.inject(this);
        binding.setDialogChangeKitchenViewModel(mDialogChangeKitchenViewModel);
        mDialogChangeKitchenViewModel.setNavigator(this);
        return view;
    }
}
