package com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar;

import android.support.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;


public final class NoOpRequestListener<A, B> implements RequestListener<A> {
    private static final RequestListener INSTANCE = new NoOpRequestListener();

    @SuppressWarnings("unchecked")
    public static <A, B> RequestListener<A> get() {
        return INSTANCE;
    }

    private NoOpRequestListener() {
    }

/*    @Override public boolean onException(Exception e, A a, Target<B> target, boolean b) {
        return false;
    }
    @Override public boolean onResourceReady(B b, A a, Target<B> target, boolean b2, boolean b1) {
        return false;
    }*/

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<A> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(A resource, Object model, Target<A> target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }
}
