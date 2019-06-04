package com.tovo.eat.ui.account.edit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityAccEditBinding;
import com.tovo.eat.databinding.ActivityNameGenderBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.ui.signup.namegender.RegionListAdapter;
import com.tovo.eat.utilities.AppConstants;

import java.util.List;

import javax.inject.Inject;

public class EditAccountActivity extends BaseActivity<ActivityAccEditBinding, EditAccountViewModel>
        implements EditAccountNavigator {

    @Inject
    EditAccountViewModel mLoginViewModelMain;
    int gender;
    int regionId = 0;
    private ActivityAccEditBinding mActivityNameGenderBinding;


    RegionListAdapter regionListAdapter;


    RegionSearchModel.Result result;


    public static Intent newIntent(Context context) {
        return new Intent(context, EditAccountActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void proceedClick() {
        String name = mActivityNameGenderBinding.edtName.getText().toString();
        String email = mActivityNameGenderBinding.edtEmail.getText().toString();

        if (validForProceed())
            mLoginViewModelMain.insertNameGenderServiceCall(name, email, gender, regionId);
    }

    @Override
    public void genderSuccess(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        //mLoginViewModelMain.fetchUserDetails();
        Intent intent = MainActivity.newIntent(EditAccountActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void genderFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void male() {
        mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.dark_gray));
        mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.dark_gray));
        gender = AppConstants.MALE;
    }

    @Override
    public void female() {
        mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.dark_gray));
        mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.eat_color));
        mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.dark_gray));
        gender = AppConstants.FEMALE;
    }

    @Override
    public void regionListLoaded(List<RegionSearchModel.Result> regionList) {

        regionListAdapter = new RegionListAdapter(this, android.R.layout.simple_dropdown_item_1line);
        mActivityNameGenderBinding.region.setThreshold(1);
        mActivityNameGenderBinding.region.setAdapter(regionListAdapter);
        regionListAdapter.setData(regionList);

    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.editAccountViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_acc_edit;
    }

    @Override
    public EditAccountViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityNameGenderBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            String name = intent.getExtras().getString("name");
            String email = intent.getExtras().getString("email");
            String regionName = intent.getExtras().getString("region");
            gender = intent.getExtras().getInt("gender");


            if (gender == 1) {
                mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.eat_color));
                mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.dark_gray));
                mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.eat_color));
                mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.dark_gray));

            } else {
                mActivityNameGenderBinding.imgMale.setTextColor(getResources().getColor(R.color.dark_gray));
                mActivityNameGenderBinding.imgFemale.setTextColor(getResources().getColor(R.color.eat_color));
                mActivityNameGenderBinding.txtMale.setTextColor(getResources().getColor(R.color.dark_gray));
                mActivityNameGenderBinding.txtFemale.setTextColor(getResources().getColor(R.color.eat_color));

            }


            mActivityNameGenderBinding.edtName.setText(name);
            if (email != null)
                mActivityNameGenderBinding.edtEmail.setText(email);

            mActivityNameGenderBinding.region.setText(regionName);


        }


        mLoginViewModelMain.regionList();


        mActivityNameGenderBinding.region.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result = ((RegionListAdapter) mActivityNameGenderBinding.region.getAdapter()).getFilterList().get(position);
                //  Log.e("", selectedItem.getMenuitem_name());

                regionId = result.getRegionid();

                mActivityNameGenderBinding.regionList.setErrorEnabled(false);

            }
        });


        mActivityNameGenderBinding.edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mActivityNameGenderBinding.inputName.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = SignUpActivity.newIntent(EditAccountActivity.this);
        startActivity(intent);
        finish();
        return true;
    }

    private boolean validForProceed() {
        if (mActivityNameGenderBinding.edtName.getText().toString().equals("") || regionId == 0) {


            if ((mActivityNameGenderBinding.edtName.getText().toString().equals(""))) {
                mActivityNameGenderBinding.inputName.setError("Enter your name");

            }

            if (regionId == 0) {
                mActivityNameGenderBinding.regionList.setError("Enter your region");
            }

            // Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
