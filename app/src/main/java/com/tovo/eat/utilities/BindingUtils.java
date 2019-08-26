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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaDataSource;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.DrawableCrossFadeFactory;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tovo.eat.R;
import com.tovo.eat.ui.account.favorites.favkitchen.FavKitchenAdapter;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesAdapter;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesResponse;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatAdapter;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatResponse;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryActivityAdapter;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.address.list.AddressListAdapter;
import com.tovo.eat.ui.address.list.AddressListResponse;
import com.tovo.eat.ui.address.select.SelectAddressListAdapter;
import com.tovo.eat.ui.address.select.SelectAddressListResponse;
import com.tovo.eat.ui.cart.BillListAdapter;
import com.tovo.eat.ui.cart.CartDishAdapter;
import com.tovo.eat.ui.cart.CartPageResponse;
import com.tovo.eat.ui.cart.coupon.CouponListAdapter;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.cart.refund.RefundListAdapter;
import com.tovo.eat.ui.cart.refund.RefundListResponse;
import com.tovo.eat.ui.filter.FilterAdapter;
import com.tovo.eat.ui.filter.FilterItems;
import com.tovo.eat.ui.home.homemenu.OffersAdapter;
import com.tovo.eat.ui.home.homemenu.RegionsCardAdapter;
import com.tovo.eat.ui.home.homemenu.collection.CollectionAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.homemenu.story.StoriesCardAdapter;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.kitchendish.KitchenDishAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.title.RegionsCardTitleAdapter;
import com.tovo.eat.ui.home.region.viewmore.RegionsListAdapter;
import com.tovo.eat.ui.kitchendetails.FavoriteAdapter;
import com.tovo.eat.ui.kitchendetails.FoodBadgeAdapter;
import com.tovo.eat.ui.kitchendetails.MenuKitchenInfoCommonImageAdapter;
import com.tovo.eat.ui.kitchendetails.SpecialitiesAdapter;
import com.tovo.eat.ui.kitchendetails.TodaysMenuAdapter;
import com.tovo.eat.ui.registration.RegionAdapter;
import com.tovo.eat.ui.registration.RegionResponse;
import com.tovo.eat.ui.search.SearchAdapter;
import com.tovo.eat.ui.search.SearchResponse;
import com.tovo.eat.ui.search.dish.SearchDishAdapter;
import com.tovo.eat.ui.signup.faqs.FaqResponse;
import com.tovo.eat.ui.signup.faqs.FaqsAdapter;

import java.util.List;

import javax.sql.DataSource;

import static android.content.Context.INPUT_METHOD_SERVICE;


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
            adapter.addItems(sales, recyclerView.getContext());
        }
    }

    @BindingAdapter({"favadapter"})
    public static void addFavKitchenItems(RecyclerView recyclerView, List<KitchenResponse.Result> sales) {
        FavKitchenAdapter adapter = (FavKitchenAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(sales, recyclerView.getContext());
        }
    }
    @BindingAdapter({"adapter"})
    public static void addBillItems(RecyclerView recyclerView, List<CartPageResponse.Cartdetail> cartdetails) {
        BillListAdapter adapter = (BillListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(cartdetails);
        }
    }
 /*@BindingAdapter({"adapter"})
    public static void addCollectionItems(RecyclerView recyclerView, List<KitchenResponse.Collection> sales) {
        CollectionAdapter adapter = (CollectionAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(sales, recyclerView.getContext());
        }
    }*/

    @BindingAdapter({"adapter"})
    public static void addCouponListItems(RecyclerView recyclerView, List<CouponListResponse.Result> results) {
        CouponListAdapter adapter = (CouponListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }
 @BindingAdapter({"hAdapter"})
    public static void addOfferListItems(RecyclerView recyclerView, List<CouponListResponse.Result> results) {
       OffersAdapter adapter = (OffersAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
        }
    }


    @BindingAdapter({"adapter"})
    public static void addKitchenDishItems(RecyclerView recyclerView, List<KitchenDishResponse.Result> response) {
        KitchenDishAdapter adapter = (KitchenDishAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();

            adapter.addItems(response);
        }
    }

    @BindingAdapter({"dishadapter"})
    public static void addSearchDishItems(RecyclerView recyclerView, List<KitchenDishResponse.Result> response) {
        SearchDishAdapter adapter = (SearchDishAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(response, recyclerView.getContext());
        }
    }


    @BindingAdapter({"adapter"})
    public static void addSearchItems(RecyclerView recyclerView, List<SearchResponse.Result> response) {
        SearchAdapter adapter = (SearchAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();

            adapter.addItems(response);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addFaqItems(RecyclerView recyclerView, List<FaqResponse.ProductList> blogs) {
        FaqsAdapter adapter = (FaqsAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(blogs);
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

    @BindingAdapter({"favoriteadapter"})
    public static void addFavTodaysMenuItems(RecyclerView recyclerView, List<KitchenDishResponse.Productlist> dishes) {
        FavoriteAdapter adapter = (FavoriteAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes);
        }
    }

    @BindingAdapter({"todaysMenuadapter"})
    public static void addTodaysMenuItems(RecyclerView recyclerView, List<KitchenDishResponse.Productlist> dishes) {
        TodaysMenuAdapter adapter = (TodaysMenuAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRefundItems(RecyclerView recyclerView, List<RefundListResponse.Result> results) {
        RefundListAdapter adapter = (RefundListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(results);
        }
    }


    @BindingAdapter({"cardadapter"})
    public static void addRegionCardItems(RecyclerView recyclerView, List<RegionsResponse.Result> dishes) {
        RegionsCardAdapter adapter = (RegionsCardAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes, recyclerView.getContext());
        }
    }

    @BindingAdapter({"cardtitleadapter"})
    public static void addRegionCardTitleItems(RecyclerView recyclerView, List<RegionsResponse.Result> dishes) {
        RegionsCardTitleAdapter adapter = (RegionsCardTitleAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes, recyclerView.getContext());
        }
    }

    @BindingAdapter({"regionListAdapter"})
    public static void addRegionListItems(RecyclerView recyclerView, List<RegionsResponse.Result> dishes) {
        RegionsListAdapter adapter = (RegionsListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes);
        }
    }


    @BindingAdapter({"storiesadapter"})
    public static void addStoriesCardItems(RecyclerView recyclerView, List<StoriesResponse.Result> dishes) {
        StoriesCardAdapter adapter = (StoriesCardAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes, recyclerView.getContext());
        }
    }

    @BindingAdapter({"foodBadgesadapter"})
    public static void addFoodBadgesCardItems(RecyclerView recyclerView, List<KitchenDishResponse.Foodbadge> response) {
        FoodBadgeAdapter adapter = (FoodBadgeAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(response);
        }
    }

    @BindingAdapter({"specialitiesadapter"})
    public static void addSpecialitiesCardItems(RecyclerView recyclerView, List<KitchenDishResponse.Specialitem> response) {
        SpecialitiesAdapter adapter = (SpecialitiesAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(response);
        }
    }

    @BindingAdapter({"kitchenCommonadapter"})
    public static void addKitchenCommonItems(RecyclerView recyclerView, List<KitchenDishResponse.Kitchenmenuimage> blogs) {
        MenuKitchenInfoCommonImageAdapter adapter = (MenuKitchenInfoCommonImageAdapter) recyclerView.getAdapter();
        if (adapter != null) {
          /*  adapter.clearItems();
            adapter.addItems(blogs);*/
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


    /*@BindingAdapter({"adapter"})
    public static void addRegionKitchenItems(RecyclerView recyclerView, List<RegionsResponse.Kitchen> dishes) {
        DishKitchenAdapter adapter = (DishKitchenAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(dishes,recyclerView.getContext());
        }
    }*/

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
            adapter.addItems(filterItems, 1);
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

    @BindingAdapter({"regionAdapter"})
    public static void addRegionItems(Spinner recyclerView, List<RegionResponse.Result> blogs) {
        RegionAdapter adapter = (RegionAdapter) recyclerView.getAdapter();
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

       /* Glide.with(context).load(url).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/






        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(MvvmApp.getInstance());
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .dontAnimate()
                .dontTransform()
                .error(R.drawable.imagenotavailable) .into(imageView);


/*
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(MvvmApp.getInstance());
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(60f);
        circularProgressDrawable.start();

        Glide.with(context).load(url)
              //  .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/


       /* CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(MvvmApp.getInstance());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(5f);
        circularProgressDrawable.start();


        //Picasso.with(context).load(image_url).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);


        Glide.with(context).load(url)
               // .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
                .placeholder(circularProgressDrawable)
             //   .error(R.drawable.imagenotavailable)
                .into(imageView);*/

        /* Glide.with(context).load(url)
               .placeholder(getProgressBarIndeterminate())
                .error(R.drawable.imagenotavailable)
                .into(imageView);

        */


    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();

        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(MvvmApp.getInstance());
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .dontAnimate()
                .dontTransform()
                .error(R.drawable.imagenotavailable) .into(imageView);









       /* CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(5f);
        circularProgressDrawable.start();*/
/*
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(MvvmApp.getInstance());
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(100f);
        circularProgressDrawable.start();*/

      /*  Glide.with(context).load(url)
                // .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/

        //Picasso.with(context).load(image_url).networkPolicy(NetworkPolicy.NO_CACHE).into(imageView);

/*
        Glide.with(context).load(url)
                // .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
                .placeholder(circularProgressDrawable)
                //   .error(R.drawable.imagenotavailable)
                .into(imageView);*/

/*
        Glide.with(context).load(url)
                .placeholder(getProgressBarIndeterminate())
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/

      /*  Glide.with(context).load(url)
              //  .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
              //  .placeholder(R.drawable.loader)
              //  .error(R.drawable.imagenotavailable)
                .into(imageView);*/


       /* R.raw.loader*/
    //    Glide.with(context).load(url).into(imageView);

    }

   public static Drawable getProgressBarIndeterminate() {
        final int[] attrs = {android.R.attr.indeterminateDrawable};
        final int attrs_indeterminateDrawable_index = 0;
        TypedArray a =MvvmApp.getInstance().obtainStyledAttributes(android.R.style.Widget_Material_ProgressBar_Small, attrs);
        try {
            return a.getDrawable(attrs_indeterminateDrawable_index);
        } finally {
            a.recycle();
        }
    }

 @BindingAdapter("enter")
    public static void closeKeyboaard(EditText editText, boolean status) {
        Context context = editText.getContext();

     editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
         @Override
         public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                 InputMethodManager inputMethodManager = (InputMethodManager)context. getSystemService(INPUT_METHOD_SERVICE);
                 inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

             }
             return false;
         }
     });
    }
@BindingAdapter("closekey")
    public static void closeSoftKeyboaard(ImageView view, boolean status) {
        Context context = view.getContext();

    InputMethodManager inputMethodManager = (InputMethodManager)context. getSystemService(INPUT_METHOD_SERVICE);
    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @BindingAdapter("cusrsiveImageUrl")
    public static void setCursiveImageUrl(RoundCornerImageView imageView, String url) {
        Context context = imageView.getContext();


        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(50f);
        circularProgressDrawable.start();

        Glide.with(context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .dontTransform()
                .dontAnimate()
                .error(R.drawable.imagenotavailable).into(imageView);



/*
        CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(MvvmApp.getInstance());
        circularProgressDrawable.setStrokeWidth(3f);
        circularProgressDrawable.setCenterRadius(100f);
        circularProgressDrawable.start();

        Glide.with(context).load(url)
               // .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
                .placeholder(circularProgressDrawable)
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/

       /* CircularProgressDrawable circularProgressDrawable =new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(5f);
        circularProgressDrawable.start();*/


      /*  Glide.with(context).load(url)
                .placeholder(getProgressBarIndeterminate())
                .error(R.drawable.imagenotavailable)
                .into(imageView);*/

    /*    Glide.with(context).load(url)
            //    .thumbnail(Glide.with(context).fromResource().load(R.raw.loader))
               // .placeholder(R.drawable.loader)
               // .error(R.drawable.imagenotavailable)
                .into(imageView);*/

       /*
        Glide.with(context).load(url).placeholder(R.drawable.images_loading)
                .error(R.drawable.imagenotavailable)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .dontAnimate()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (isFirstResource) {
                            return false; // thumbnail was not shown, do as usual
                        }
                        return true;
                    }
                })
                .into(imageView);*/
       // Glide.with(context).load(url).into(imageView);
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

    @BindingAdapter("touchListener")
    public void setTouchListener(View self, boolean value) {
        self.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Check if the button is PRESSED
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //do some thing
                    view.setBackgroundColor(R.color.eat_color);

                }// Check if the button is RELEASED
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //do some thing
                }
                return false;
            }
        });
    }


}


