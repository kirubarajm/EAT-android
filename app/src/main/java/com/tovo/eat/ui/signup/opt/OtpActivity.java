package com.tovo.eat.ui.signup.opt;


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

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOtpBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.forgotpassword.ForgotPasswordActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.NameGenderActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class OtpActivity extends BaseActivity<ActivityOtpBinding, OtpActivityViewModel> implements OtpActivityNavigator {

    @Inject
    OtpActivityViewModel mLoginViewModelMain;
    String strPhoneNumber = "";
    String strOtpId = "";
    String UserId = "";
    private ActivityOtpBinding mActivityOtpBinding;
    private EditText[] editTexts;

    private BroadcastReceiver otpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                mActivityOtpBinding.edt1.setText(message.substring(0, 1));
                mActivityOtpBinding.edt2.setText(message.substring(1, 2));
                mActivityOtpBinding.edt3.setText(message.substring(2, 3));
                mActivityOtpBinding.edt4.setText(message.substring(3, 4));
                mActivityOtpBinding.edt5.setText(message.substring(4, 5));

                mActivityOtpBinding.edt1.setEnabled(false);
                mActivityOtpBinding.edt2.setEnabled(false);
                mActivityOtpBinding.edt3.setEnabled(false);
                mActivityOtpBinding.edt4.setEnabled(false);
                mActivityOtpBinding.edt5.setEnabled(false);

                mLoginViewModelMain.continueClick();
            }
        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, OtpActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void continueClick() {
        //hideKeyboard();
        //strPhoneNumber=mActivityOtpBinding.edtPassword.getText().toString();
        String st1 = mActivityOtpBinding.edt1.getText().toString();
        String st2 = mActivityOtpBinding.edt2.getText().toString();
        String st3 = mActivityOtpBinding.edt3.getText().toString();
        String st4 = mActivityOtpBinding.edt4.getText().toString();
        String st5 = mActivityOtpBinding.edt5.getText().toString();

        String otp = st1 + st2 + st3 + st4 + st5;
        if (validForOtp())
            mLoginViewModelMain.userContinueClick(strPhoneNumber, Integer.parseInt(otp), Integer.parseInt(strOtpId));
    }

    @Override
    public void openHomeActivity(boolean trueOrFalse) {
        if (trueOrFalse) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
            Intent intent = MainActivity.newIntent(OtpActivity.this);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void nameGenderScreen() {
        Intent intent = NameGenderActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void login() {
        if (validForPassword()) {
            String strPassword = mActivityOtpBinding.edtPassword.getText().toString();
            mLoginViewModelMain.login(strPhoneNumber, strPassword);
        }
    }

    @Override
    public void forgotPassword() {
        mActivityOtpBinding.edtPassword.setText("");
        Intent intent = ForgotPasswordActivity.newIntent(OtpActivity.this);
        intent.putExtra("strPhoneNumber", strPhoneNumber);
        startActivity(intent);
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
        Intent intent = MainActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure() {
        Toast.makeText(getApplicationContext(), AppConstants.TOAST_LOGIN_FAILED, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.otpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_otp;
    }

    @Override
    public OtpActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOtpBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String booleanOtp = bundle.getString("booleanOpt");
            strOtpId = bundle.getString("optId");
            strPhoneNumber = bundle.getString("strPhoneNumber");
            UserId = bundle.getString("UserId");
            if (booleanOtp.equalsIgnoreCase("true")) {
                mLoginViewModelMain.otp.set(true);
                Toolbar toolbar = findViewById(R.id.toolbar_otp);
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                toolbar.setTitle("Login");

            } else {
                mLoginViewModelMain.otp.set(false);
                Toolbar toolbar = findViewById(R.id.toolbar_otp);
                setSupportActionBar(toolbar);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
                toolbar.setTitle("OTP");
            }
            mActivityOtpBinding.txtMessageSent.setText("(OTP) Sent to " + strPhoneNumber);

        }

        otpFocusOnTextChange();
    }

    private void otpFocusOnTextChange(){
        editTexts = new EditText[]{mActivityOtpBinding.edt1, mActivityOtpBinding.edt2, mActivityOtpBinding.edt3, mActivityOtpBinding.edt4,mActivityOtpBinding.edt5};
        mActivityOtpBinding.edt1.addTextChangedListener(new PinTextWatcher(0));
        mActivityOtpBinding.edt2.addTextChangedListener(new PinTextWatcher(1));
        mActivityOtpBinding.edt3.addTextChangedListener(new PinTextWatcher(2));
        mActivityOtpBinding.edt4.addTextChangedListener(new PinTextWatcher(3));
        mActivityOtpBinding.edt5.addTextChangedListener(new PinTextWatcher(4));

        mActivityOtpBinding.edt1.setOnKeyListener(new PinOnKeyListener(0));
        mActivityOtpBinding.edt2.setOnKeyListener(new PinOnKeyListener(1));
        mActivityOtpBinding.edt3.setOnKeyListener(new PinOnKeyListener(2));
        mActivityOtpBinding.edt4.setOnKeyListener(new PinOnKeyListener(3));
        mActivityOtpBinding.edt5.setOnKeyListener(new PinOnKeyListener(4));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(OtpActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForOtp() {
        if (mActivityOtpBinding.edt1.getText().toString().equals("") && mActivityOtpBinding.edt2.getText().toString().equals("") && mActivityOtpBinding.edt4.getText().toString().equals("")
                && mActivityOtpBinding.edt4.getText().toString().equals("") && mActivityOtpBinding.edt5.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_OTP, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean validForPassword() {
        if (mActivityOtpBinding.edtPassword.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), AppConstants.TOAST_ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(otpReceiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(otpReceiver);
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
