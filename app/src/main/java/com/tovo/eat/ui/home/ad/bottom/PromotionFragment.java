package com.tovo.eat.ui.home.ad.bottom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentPromotionBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.DelayBitmapTransformation;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;


public class PromotionFragment extends BaseBottomSheetFragment<FragmentPromotionBinding, PromotionViewModel> implements PromotionNavigator {


    public static final String TAG = PromotionFragment.class.getSimpleName();
    @Inject
    PromotionViewModel mPromotionViewModel;

    FragmentPromotionBinding mFragmentPromotionBinding;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_KITCHEN_FILTER;


    public static PromotionFragment newInstance() {
        Bundle args = new Bundle();
        PromotionFragment fragment = new PromotionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.filterViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_promotion;
    }

    @Override
    public PromotionViewModel getViewModel() {
        return mPromotionViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPromotionViewModel.setNavigator(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentPromotionBinding = getViewDataBinding();

       /* setStyle(STYLE_NORMAL, R.style. AppBottomSheetDialogTheme);
        getBaseActivity().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/

        //  this.getDialog().getWindow().setBackgroundDrawableResource(R.drawable.round_bottom_sheet);

        //      mPromotionViewModel.url.set("https://eattovo.s3.amazonaws.com/upload/admin/makeit/product/1578500485888-Infinity%20regions-40.jpg");


        Glide.with(getBaseActivity())
                .load("https://eattovo.s3.ap-south-1.amazonaws.com/upload/admin/makeit/product/1580808257672-Infinity%20regions-%20free%20delivery-04.png")
                //   .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new DelayBitmapTransformation(0))
                // .listener(new LoggingListener<String, Bitmap>())
                .into(mFragmentPromotionBinding.imageView);


        mFragmentPromotionBinding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void closeDialog() {
        dismiss();
    }

}
