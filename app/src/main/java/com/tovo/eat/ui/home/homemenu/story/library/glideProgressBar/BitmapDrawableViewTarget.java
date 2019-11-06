package com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.request.target.ImageViewTarget;

public class BitmapDrawableViewTarget extends ImageViewTarget<BitmapDrawable> {


    public BitmapDrawableViewTarget(ImageView view) {
        super(view);
    }

    @Override
    protected void setResource(@Nullable BitmapDrawable resource) {
        view.setBackgroundDrawable(resource);
    }
}


