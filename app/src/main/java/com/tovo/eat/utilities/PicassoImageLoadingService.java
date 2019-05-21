package com.tovo.eat.utilities;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;
import com.tovo.eat.R;

import ss.com.bannerslider.ImageLoadingService;

public class PicassoImageLoadingService implements ImageLoadingService {
    public Context context;

    public PicassoImageLoadingService(Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadImage(int resource, ImageView imageView) {
        Glide.with(context).load(resource).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    @Override
    public void loadImage(String url, int placeHolder, int errorDrawable, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}