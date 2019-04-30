/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tovo.eat.utilities;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tovo.eat.R;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesAdapter;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesResponse;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatAdapter;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatResponse;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryActivityAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.address.list.AddressListAdapter;
import com.tovo.eat.ui.address.list.AddressListResponse;
import com.tovo.eat.ui.address.select.SelectAddressListAdapter;
import com.tovo.eat.ui.address.select.SelectAddressListResponse;
import com.tovo.eat.ui.cart.CartDishAdapter;
import com.tovo.eat.ui.cart.CartPageResponse;
import com.tovo.eat.ui.filter.FilterAdapter;
import com.tovo.eat.ui.filter.FilterItems;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.kitchendish.KitchenDishAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

import java.util.List;


/**
 * Created by amitshekhar on 11/07/17.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addKitchenItems(RecyclerView recyclerView, List<KitchenResponse.Result> sales) {
        KitchenAdapter adapter = (KitchenAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(sales);
        }
    }


    @BindingAdapter({"adapter"})
    public static void addKitchenDishItems(RecyclerView recyclerView, List<KitchenDishResponse.Result> response) {
        KitchenDishAdapter adapter = (KitchenDishAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();

            adapter.addItems(response.get(0).getProductlist(), response);
        }
    }


    @BindingAdapter({"adapter"})
    public static void addDishItems(RecyclerView recyclerView, List<DishResponse.Result> dishes) {
        DishAdapter adapter = (DishAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes);
        }
    }




    @BindingAdapter({"adapter"})
    public static void addCartDishItems(RecyclerView recyclerView, List<CartPageResponse.Item> dishes) {
        CartDishAdapter adapter = (CartDishAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes);
        }
    }


    @BindingAdapter({"adapter"})
    public static void addAddressListItems(RecyclerView recyclerView, List<AddressListResponse.Result> response) {
        AddressListAdapter adapter = (AddressListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(response);
        }
    }


    @BindingAdapter({"adapter"})
    public static void addSelectAddressListItems(RecyclerView recyclerView, List<SelectAddressListResponse.Result> response) {
        SelectAddressListAdapter adapter = (SelectAddressListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(response);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRepliesItems(RecyclerView recyclerView, List<RepliesResponse.Result> blogs) {
        RepliesAdapter adapter = (RepliesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addOrderItems(blogs);
        }
    }




    @BindingAdapter({"filteradapter"})
    public static void addFilterItems(RecyclerView recyclerView, List<FilterItems> filterItems) {
       FilterAdapter adapter = (FilterAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(filterItems,1);
        }
    }




    @BindingAdapter({"adapter"})
    public static void addChatItems(RecyclerView recyclerView, List<ChatResponse.Result> blogs) {
        ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addChatItems(blogs);
        }
    }


    @BindingAdapter("visibleView")
    public static void setViewVisiblityVisible(LinearLayout imageView, String type) {
        Context context = imageView.getContext();
        if (type.equals("0")) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("visibleGone")
    public static void setViewVisiblityGone(LinearLayout imageView, String type) {
        Context context = imageView.getContext();
        if (type.equals("0")) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter("visibleView")
    public static void setViewVisiblityVisible(TextView imageView, String type) {
        Context context = imageView.getContext();
        if (type.equals("0")) {
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    @BindingAdapter("visibleGone")
    public static void setViewVisiblityGone(TextView imageView, String type) {
        Context context = imageView.getContext();
        if (type.equals("0")) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(RoundCornerImageView imageView, String url) {
        Context context = imageView.getContext();
               /* with(context).load(url).placeholder(R.drawable. images_loading)
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/

        Glide.with(context).load(url).into(imageView);
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .into(imageView);

        Glide.with(context).load(url).into(imageView);
    }


    @BindingAdapter("cusrsiveImageUrl")
    public static void setCursiveImageUrl(RoundCornerImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


    @BindingAdapter("setBitmap")
    public static void setBitmap(ImageView imageView, Bitmap bitmap) {
        Context context = imageView.getContext();
        imageView.setImageBitmap(bitmap);
    }


    @BindingAdapter({"setWebViewClient"})
    public static void setWebViewClient(WebView view, WebViewClient client) {
        view.setWebViewClient(client);
    }

    @BindingAdapter({"loadUrl"})
    public static void loadUrl(WebView view, String url) {
        view.loadUrl(url);
    }


    @BindingAdapter({"videoUrl"})
    public static void setVideourl(VideoView view, String url) {
        if (url != null) {
            view.setVideoURI(Uri.parse(url));
            view.requestFocus();
        }
    }

    @BindingAdapter({"adapter"})
    public static void addOrdersHistoryItems(RecyclerView recyclerView, List<OrdersHistoryListResponse.Result> blogs) {
        OrdersHistoryActivityAdapter adapter = (OrdersHistoryActivityAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addOrderItems(blogs);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addOrdersHistoryViewItems(RecyclerView recyclerView, List<OrdersHistoryActivityResponse.Result.Item> blogs) {
        OrdersHistoryActivityItemAdapter adapter = (OrdersHistoryActivityItemAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addOrderItems(blogs);
        }
    }


}


