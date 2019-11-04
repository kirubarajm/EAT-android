package com.tovo.eat.ui.cart.xfactoralert;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentFilterBinding;
import com.tovo.eat.databinding.FragmentXfactorBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;


public class XFactorFragment extends BaseBottomSheetFragment<FragmentXfactorBinding, XFactorViewModel> implements XFactorNavigator {


    public static final String TAG = XFactorFragment.class.getSimpleName();
    @Inject
    XFactorViewModel mXFactorViewModel;

    FragmentXfactorBinding mFragmentXfactorBinding;


    XfactorListner xfactorListner;
    Analytics analytics;
    String  pageName= AppConstants.SCREEN_X_FACTOR;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof XfactorListner) {
            //init the listener
            xfactorListner = (XfactorListner) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }

    }

    public static XFactorFragment newInstance() {
        Bundle args = new Bundle();
        XFactorFragment fragment = new XFactorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.xFactorViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_xfactor;
    }

    @Override
    public XFactorViewModel getViewModel() {
        return mXFactorViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mXFactorViewModel.setNavigator(this);
        analytics=new Analytics(getActivity(),pageName);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentXfactorBinding = getViewDataBinding();

        if (getArguments() != null) {
            mXFactorViewModel.message.set(getArguments().getString("message"));
            mXFactorViewModel.title.set(getArguments().getString("title"));
        }


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goHome() {
        dismiss();
        xfactorListner.goHome();
    }

}
