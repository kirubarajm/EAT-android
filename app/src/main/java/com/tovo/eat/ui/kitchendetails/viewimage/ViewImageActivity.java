package com.tovo.eat.ui.kitchendetails.viewimage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityViewImageBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.analytics.Analytics;

import java.io.IOException;
import java.net.URL;

import javax.inject.Inject;

public class ViewImageActivity extends BaseActivity<ActivityViewImageBinding, ViewImageViewModel> implements ViewImageNavigator {


    @Inject
    ViewImageViewModel mViewImageViewModel;

    ActivityViewImageBinding mActivityViewImageBinding;

    Analytics analytics;
    String  pageName="View Image";

    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, ViewImageActivity.class);
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float pixels, int width, int height) {
        final Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int sourceWidth = bitmap.getWidth();
        final int sourceHeight = bitmap.getHeight();

        float xScale = (float) width / bitmap.getWidth();
        float yScale = (float) height / bitmap.getHeight();
        float scale = Math.max(xScale, yScale);

        float scaledWidth = scale * sourceWidth;
        float scaledHeight = scale * sourceHeight;

        float left = (width - scaledWidth) / 2;
        float top = (height - scaledHeight) / 2;

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        final RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, pixels, pixels, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, null, targetRect, paint);

        return output;
    }

    public static Bitmap getBitmapFromURL(String src) {


        Bitmap image = null;

        try {
            URL url = new URL(src);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            System.out.println(e);
        }


        return image;

     /*   try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* KitchenDishSubBinding mBinding = DataBindingUtil
                .setContentView(this, R.layout.kitchen_dish_sub);*/

        mViewImageViewModel.setNavigator(this);


        analytics=new Analytics(this,pageName);

       /* Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID = intent.getExtras().getInt("kitchenId");

            mKitchenDetailsViewModel.fetchRepos(kitchenID);

        }*/
        String url = getIntent().getExtras().getString("image", null);

        mViewImageViewModel.imageUrl.set(url);


       /* Bitmap bitmap=getBitmapFromURL(url);

        mActivityViewImageBinding.image.setImageBitmap(bitmap);

        mActivityViewImageBinding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewImageActivity.super.onBackPressed();
            }
        });

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            loadFullSizeBitmap(bitmap);
        } else {
            getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {

                private boolean isClosing = false;

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    if (isClosing) {
//                        addCardCorners();
                    }
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (!isClosing) {
                        isClosing = true;

                       *//* removeCardCorners();*//*
                        loadFullSizeBitmap(bitmap);
                    }
                }
            });
        }*/

    }

    private void loadFullSizeBitmap(Bitmap bitmap) {


        final DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        final int w = metrics.widthPixels;
        final int h = metrics.heightPixels;

        Bitmap result;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            result = getRoundedCornerBitmap(bitmap,
                    10, w, h);
        } else {
            result = bitmap;
        }


        mActivityViewImageBinding.image.setImageBitmap(result);


    }

    @Override
    public int getBindingVariable() {
        return BR.viewImageViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_image;
    }

    @Override
    public ViewImageViewModel getViewModel() {
        return mViewImageViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }
}

