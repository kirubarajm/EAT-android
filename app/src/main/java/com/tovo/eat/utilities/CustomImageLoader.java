package com.tovo.eat.utilities;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.freshchat.consumer.sdk.FreshchatImageLoader;
import com.freshchat.consumer.sdk.FreshchatImageLoaderRequest;

public class CustomImageLoader implements FreshchatImageLoader {
  
  @Override
  public void load(@NonNull FreshchatImageLoaderRequest request, @NonNull ImageView imageView) {
    // your code to download image and set to imageView
  }

  @Nullable
  @Override
  public Bitmap get(@NonNull FreshchatImageLoaderRequest request) {
    // code to download and return bitmap image



    return null;
  }

  @Override
  public void fetch(@NonNull FreshchatImageLoaderRequest request) {
    // code to download image
  }
}