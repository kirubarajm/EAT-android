package com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;


public class DelayBitmapTransformation extends BitmapTransformation {
    private final int delay;
    public DelayBitmapTransformation(int delay) {
        this.delay = delay;
    }
    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int w, int h) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Log.i("DELAY", "Sleeping for " + delay + "ms was interrupted.", e);
        }
        return toTransform;
    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
