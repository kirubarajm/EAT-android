package com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;

import java.util.Locale;

public class LoggingListener<A, B> implements RequestListener<A> {
    private final int level;
    private final String name;
    private final RequestListener<A> delegate;

    public LoggingListener() {
        this("");
    }

    public LoggingListener(@NonNull String name) {
        this(Log.VERBOSE, name);
    }

    public LoggingListener(int level, @NonNull String name) {
        this(level, name, null);
    }

    public LoggingListener(RequestListener<A> delegate) {
        this(Log.VERBOSE, "", delegate);
    }

    public LoggingListener(int level, @NonNull String name, RequestListener<A> delegate) {
        this.level = level;
        this.name = name;
        this.delegate = delegate == null ? NoOpRequestListener.<A, B>get() : delegate;
    }


    private String isMem(boolean isFromMemoryCache) {
        return isFromMemoryCache ? "sync" : "async";
    }

    private String isFirst(boolean isFirstResource) {
        return isFirstResource ? "first" : "not first";
    }

    private String getTargetDescription(Target<?> target) {
        String result;
        if (target instanceof WrappingTarget) {
            Target wrapped = ((WrappingTarget) target).getWrappedTarget();
            result = String.format(Locale.ROOT, "%s in %s", getTargetDescription(wrapped), target);
        } else if (target instanceof ViewTarget) {
            View v = ((ViewTarget) target).getView();
            LayoutParams p = v.getLayoutParams();
            result = String.format(Locale.ROOT,
                    "%s(params=%dx%d->size=%dx%d)", target, p.width, p.height, v.getWidth(), v.getHeight());
        } else {
            result = String.valueOf(target);
        }
        return result;
    }

    private String getResourceDescription(B resource) {
        String result;
        if (resource instanceof Bitmap) {
            Bitmap bm = (Bitmap) resource;
            result = String.format(Locale.ROOT,
                    "%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
        } else if (resource instanceof BitmapDrawable) {
            Bitmap bm = ((BitmapDrawable) resource).getBitmap();
            result = String.format(Locale.ROOT,
                    "%s(%dx%d@%s)", resource, bm.getWidth(), bm.getHeight(), bm.getConfig());
        } else if (resource instanceof Drawable) {
            Drawable d = (Drawable) resource;
            result = String.format(Locale.ROOT,
                    "%s(%dx%d)", resource, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        } else {
            result = String.valueOf(resource);
        }
        return result;
    }

    private static String strip(Object text) {
        return String.valueOf(text).replaceAll("(com|android|net|org)(\\.[a-z]+)+\\.", "");
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<A> target, boolean isFirstResource) {
        return delegate.onLoadFailed(e, model, target, isFirstResource);
    }

    @Override
    public boolean onResourceReady(A resource, Object model, Target<A> target, DataSource dataSource, boolean isFirstResource) {
        return delegate.onResourceReady(resource, model, target, dataSource, isFirstResource);
    }
}
