package com.tovo.eat.ui.track.help;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderHelpBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.chat.IssuesAdapter;
import com.tovo.eat.utilities.chat.IssuesListResponse;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class OrderHelpActivity extends BaseActivity<ActivityOrderHelpBinding, OrderHelpViewModel> implements OrderHelpNavigator, View.OnTouchListener, HasSupportFragmentInjector,IssuesAdapter.IssuesAdapterListener{

    @Inject
    OrderHelpViewModel mOrderHelpViewModel;
    ActivityOrderHelpBinding mActivityOrderHelpBinding;
    String strOrderId;
    String message = null;
    ProgressDialog dialog;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Inject
    IssuesAdapter issuesAdapter;

    @Inject
    LinearLayoutManager layoutManager;


    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_HELP;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHelpActivity.class);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_ORDER_HELP, AppConstants.CLICK_BACK_BUTTON);

        super.onBackPressed();
    }

    @Override
    public void callDelivery() {

      /*  Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);*/
        new Analytics().sendClickData(AppConstants.SCREEN_ORDER_HELP, AppConstants.CLICK_CALL_DELIVERY_EXECUTIVE);

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);

    }

    @Override
    public void gotoSupport() {
        new Analytics().sendClickData(AppConstants.SCREEN_ORDER_HELP, AppConstants.CLICK_CONTACT_SUPPORT);
        Intent intent = SupportActivity.newIntent(OrderHelpActivity.this);
        intent.putExtra("orderid", mOrderHelpViewModel.getDataManager().getOrderId());
        intent.putExtra("type", AppConstants.QUERY_TYPE_ORDER);
        startActivity(intent);


    }

    @Override
    public void orderCanceled() {

        if (dialog.isShowing()) dialog.show();

        Intent intent = MainActivity.newIntent(OrderHelpActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void orderCancelClicked() {

        new Analytics().sendClickData(AppConstants.SCREEN_ORDER_HELP, AppConstants.CLICK_ORDER_CANCEL);

        if (mOrderHelpViewModel.otherReason.get()) {

            message = mActivityOrderHelpBinding.otherReason.getText().toString();

            if (message != null && !message.isEmpty()) {
                showAlert();
            } else {
                Toast.makeText(OrderHelpActivity.this, "Please enter the reason for order cancel.", Toast.LENGTH_SHORT).show();
            }

        } else {

            if (message != null && !message.isEmpty()) {
                showAlert();
            } else {
                Toast.makeText(OrderHelpActivity.this, "Please select the reason for order cancel.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void orderCancelFailed() {
        if (dialog.isShowing()) dialog.dismiss();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createChat( String department, String tag,String note) {

        ZopimChat.init(getString(R.string.zopim_account_id));
        final VisitorInfo.Builder build = new VisitorInfo.Builder()
                .email(mOrderHelpViewModel.getDataManager().getCurrentUserEmail())
                .name(mOrderHelpViewModel.getDataManager().getCurrentUserName())
                .note(note)
                .phoneNumber(mOrderHelpViewModel.getDataManager().getCurrentUserPhNo());
        ZopimChat.setVisitorInfo(build.build());

// build pre chat form config
        PreChatForm preChatForm = new PreChatForm.Builder()
                .name(PreChatForm.Field.REQUIRED)
                .email(PreChatForm.Field.NOT_REQUIRED)
                .phoneNumber(PreChatForm.Field.REQUIRED)
                .department(PreChatForm.Field.OPTIONAL)
                .message(PreChatForm.Field.NOT_REQUIRED)
                .build();
// build session config
        ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
                .preChatForm(preChatForm)
                .department(department)
                .tags("Current Order", tag);
// start chat activity with config
        ZopimChatActivity.startActivity(this, config);
    }

    @Override
    public int getBindingVariable() {
        return BR.orderHelpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_help;
    }

    @Override
    public OrderHelpViewModel getViewModel() {
        return mOrderHelpViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHelpBinding = getViewDataBinding();
        mOrderHelpViewModel.setNavigator(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");

        issuesAdapter.setListener(this);

        analytics = new Analytics(this, pageName);
        mActivityOrderHelpBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                // mOrderHelpViewModel.cancelOrder(rb.getText().toString());

                if (checkedId == R.id.radioButton4) {
                    mOrderHelpViewModel.otherReason.set(true);
                } else {
                    message = rb.getText().toString();
                    mOrderHelpViewModel.otherReason.set(false);
                }


                //   Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mOrderHelpViewModel.deliveryName.set(getIntent().getExtras().getString("name"));
            mOrderHelpViewModel.deliveryNumber.set(getIntent().getExtras().getString("number"));
            mOrderHelpViewModel.deliveryAssigned.set(getIntent().getExtras().getBoolean("status"));
            mOrderHelpViewModel.serviceCharges.set(getIntent().getExtras().getString("charge"));
            mOrderHelpViewModel.cancelationMessage.set(getIntent().getExtras().getString("message"));
        }

     /*   mActivityOrderHelpBinding.cancelReason1.setOnTouchListener(this);
        mActivityOrderHelpBinding.cancelReason2.setOnTouchListener(this);
        mActivityOrderHelpBinding.cancelReason3.setOnTouchListener(this);*/

        subscribeToLiveData();

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityOrderHelpBinding.recyclerviewIssue.setLayoutManager(layoutManager);
        mActivityOrderHelpBinding.recyclerviewIssue.setAdapter(issuesAdapter);


    }

    private void subscribeToLiveData() {
        mOrderHelpViewModel.getIssuesLiveData().observe(this,
                issuesItemViewModel -> mOrderHelpViewModel.addIssuesListItemsToList(issuesItemViewModel));

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int id = v.getId();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.setBackgroundColor(getResources().getColor(R.color.light_eat_color));
                break;
            case MotionEvent.ACTION_UP:
                v.setBackgroundColor(getResources().getColor(R.color.gray));
                //set color back to default

                showAlert();

                break;
        }
        return true;
    }

    public void showAlert() {

        AlertDialog alertDialog = new AlertDialog.Builder(OrderHelpActivity.this).create();
        alertDialog.setMessage(mOrderHelpViewModel.cancelationMessage.get());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startProgresDialog();
                        mOrderHelpViewModel.cancelOrder(message);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    public void startProgresDialog() {
        dialog.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    @Override
    public void canceled() {
        finish();
    }

    @Override
    public void issueItemClick(IssuesListResponse.Result issues) {

        mOrderHelpViewModel.getIssuesNote(issues.getType(),issues.getId());

    }
}

