package com.tovo.eat.ui.forgotpassword;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityForgotPasswordBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordActivityViewModel>
        implements ForgotPasswordActivityNavigator {

    String strPhoneNumber = "";
    @Inject
    ForgotPasswordActivityViewModel mForgotPasswordActivityViewModel;

    private ActivityForgotPasswordBinding mActivityForgotPasswordBinding;
    private EditText[] editTexts;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                mActivityForgotPasswordBinding.edt1.setText(message.substring(0, 1));
                mActivityForgotPasswordBinding.edt2.setText(message.substring(1, 2));
                mActivityForgotPasswordBinding.edt3.setText(message.substring(2, 3));
                mActivityForgotPasswordBinding.edt4.setText(message.substring(3, 4));
                mActivityForgotPasswordBinding.edt5.setText(message.substring(4, 5));
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, ForgotPasswordActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void otpVerificationProceed() {
        String str1 = mActivityForgotPasswordBinding.edt1.getText().toString();
        String str2 = mActivityForgotPasswordBinding.edt2.getText().toString();
        String str3 = mActivityForgotPasswordBinding.edt3.getText().toString();
        String str4 = mActivityForgotPasswordBinding.edt4.getText().toString();
        String str5 = mActivityForgotPasswordBinding.edt5.getText().toString();
        String strOtp = str1 + str2 + str3 + str4 + str5;
        mForgotPasswordActivityViewModel.otpVerificationServiceCall(strPhoneNumber, strOtp);
    }

    @Override
    public void otpVerificationSubmit() {
        if (validForPass()) {
            String strpass = mActivityForgotPasswordBinding.edtConfirmPassword.getText().toString();
            mForgotPasswordActivityViewModel.passwordVerificationServiceCall(strpass);
        }
    }

    @Override
    public void confirmPassSuccess() {
        mActivityForgotPasswordBinding.edtPassword.setText("");
        mActivityForgotPasswordBinding.edtConfirmPassword.setText("");
        Toast.makeText(getApplicationContext(), "Password changed successfully", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void confirmPassFailre() {
        Toast.makeText(getApplicationContext(), "Password not changed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void otpInvalid() {
        Toast.makeText(getApplicationContext(), "Invalid Otp", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.forgotPasswordViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_forgot_password;
    }

    @Override
    public ForgotPasswordActivityViewModel getViewModel() {
        return mForgotPasswordActivityViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityForgotPasswordBinding = getViewDataBinding();
        mForgotPasswordActivityViewModel.setNavigator(this);


        Toolbar toolbar = findViewById(R.id.toolbar_forgot_pass);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mActivityForgotPasswordBinding.toolbarForgotPass.setTitle("Forgot Password");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strPhoneNumber = bundle.getString("strPhoneNumber");
            mForgotPasswordActivityViewModel.otpServiceCall(strPhoneNumber);
            mActivityForgotPasswordBinding.txtMessageSent.setText("(OTP) Sent to " + strPhoneNumber);
        }
        otpFocusOnTextChange();

    }

    @Override
    public void onFragmentDetached(String tag) {

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

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    private boolean validForPass() {
        String pass = mActivityForgotPasswordBinding.edtPassword.getText().toString();
        String cPass = mActivityForgotPasswordBinding.edtConfirmPassword.getText().toString();

        if (mActivityForgotPasswordBinding.edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityForgotPasswordBinding.edtConfirmPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_RE_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!pass.equals(cPass)) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_PASSWORD_NOT_MATCHING, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void otpFocusOnTextChange(){
        editTexts = new EditText[]{mActivityForgotPasswordBinding.edt1, mActivityForgotPasswordBinding.edt2, mActivityForgotPasswordBinding.edt3, mActivityForgotPasswordBinding.edt4,mActivityForgotPasswordBinding.edt5};
        mActivityForgotPasswordBinding.edt1.addTextChangedListener(new PinTextWatcher(0));
        mActivityForgotPasswordBinding.edt2.addTextChangedListener(new PinTextWatcher(1));
        mActivityForgotPasswordBinding.edt3.addTextChangedListener(new PinTextWatcher(2));
        mActivityForgotPasswordBinding.edt4.addTextChangedListener(new PinTextWatcher(3));
        mActivityForgotPasswordBinding.edt5.addTextChangedListener(new PinTextWatcher(4));

        mActivityForgotPasswordBinding.edt1.setOnKeyListener(new PinOnKeyListener(0));
        mActivityForgotPasswordBinding.edt2.setOnKeyListener(new PinOnKeyListener(1));
        mActivityForgotPasswordBinding.edt3.setOnKeyListener(new PinOnKeyListener(2));
        mActivityForgotPasswordBinding.edt4.setOnKeyListener(new PinOnKeyListener(3));
        mActivityForgotPasswordBinding.edt5.setOnKeyListener(new PinOnKeyListener(4));
    }

    public class PinOnKeyListener implements View.OnKeyListener {

        private int currentIndex;

        PinOnKeyListener(int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (editTexts[currentIndex].getText().toString().isEmpty() && currentIndex != 0)
                    editTexts[currentIndex - 1].requestFocus();
            }
            return false;
        }

    }

    public class PinTextWatcher implements TextWatcher {

        private int currentIndex;
        private boolean isFirst = false, isLast = false;
        private String newTypedString = "";

        PinTextWatcher(int currentIndex) {
            this.currentIndex = currentIndex;

            if (currentIndex == 0)
                this.isFirst = true;
            else if (currentIndex == editTexts.length - 1)
                this.isLast = true;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            newTypedString = s.subSequence(start, start + count).toString().trim();
        }

        @Override
        public void afterTextChanged(Editable s) {

            String text = newTypedString;

            /* Detect paste event and set first char */
            if (text.length() > 1)
                text = String.valueOf(text.charAt(0)); // TODO: We can fill out other EditTexts

            editTexts[currentIndex].removeTextChangedListener(this);
            editTexts[currentIndex].setText(text);
            editTexts[currentIndex].setSelection(text.length());
            editTexts[currentIndex].addTextChangedListener(this);

            if (text.length() == 1)
                moveToNext();
            else if (text.length() == 0)
                moveToPrevious();
        }

        private void moveToNext() {
            if (!isLast)
                editTexts[currentIndex + 1].requestFocus();

            if (isAllEditTextsFilled() && isLast) { // isLast is optional
                editTexts[currentIndex].clearFocus();
                hideKeyboard();
            }
        }

        private void moveToPrevious() {
            if (!isFirst)
                editTexts[currentIndex - 1].requestFocus();
        }

        private boolean isAllEditTextsFilled() {
            for (EditText editText : editTexts)
                if (editText.getText().toString().trim().length() == 0)
                    return false;
            return true;
        }

        private void hideKeyboard() {
            if (getCurrentFocus() != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }

    }
}
