package com.tovo.eat.ui.home.homemenu.story.storiesactivity.ntest;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.databinding.library.baseAdapters.BR;
import com.bolaware.viewstimerstory.Momentz;
import com.bolaware.viewstimerstory.MomentzCallback;
import com.bolaware.viewstimerstory.MomentzView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentSampleBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.StoryStatusView;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.ProgressTarget;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.StoriesTabActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.inject.Inject;

public class StoriesPagerFragment22 extends BaseFragment<FragmentSampleBinding, StoriesPagerFragmentViewModel2>
        implements StoriesPagerFragmentNavigator2, MomentzCallback {

    private static boolean isTextEnabled = true;
    public StoryStatusView storyStatusView;
    @Inject
    StoriesPagerFragmentViewModel2 mSplashActivityViewModel;
    StoriesResponse storiesFullResponse;
    int position;
    boolean isVisibleToUser;
    boolean isoNcREATRE;
    StoriesResponse.Result storiesResponse;
    VideoView videoView;
    ProgressBar imageProgressBar;
    TextView txtProgress;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_STORIES;
    private FragmentSampleBinding mFragmentSampleBinding;
    private ImageView image;
    private int counter = 0;
    private long[] statusResourcesDuration;
    private long statusDuration = 3000L;
    private boolean isImmersive = true;
    private boolean isCaching = true;
    private ProgressTarget<String, Bitmap> target;
    Momentz momentz;
    public static StoriesPagerFragment22 newInstance() {
        Bundle args = new Bundle();
        StoriesPagerFragment22 fragment = new StoriesPagerFragment22();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onSeeMore() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_SEE_MORE);
        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", mSplashActivityViewModel.category_id.get());
        intent.putExtra("type", mSplashActivityViewModel.category_type.get());
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public int getBindingVariable() {
        return BR.sampleFragmentViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_sample;
    }

    @Override
    public StoriesPagerFragmentViewModel2 getViewModel() {
        return mSplashActivityViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashActivityViewModel.setNavigator(this);


        analytics = new Analytics(getActivity(), pageName);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentSampleBinding = getViewDataBinding();
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }


        mSplashActivityViewModel.getData();
        ((StoriesTabActivity) getActivity()).methodDDD(position);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // isoNcREATRE = isVisibleToUser;

    }


    @Override
    public void getStoriesDataFromLocal(StoriesResponse storiesResponseFull) {
        this.storiesFullResponse = storiesResponseFull;
        storiesResponse = storiesFullResponse.getResult().get(position);
        mainFunction();
    }

    public void mainFunction() {
        isImmersive = false;
        isCaching = true;
        isTextEnabled = false;


        View imageView = new ImageView(getContext());
        View videoView = new VideoView(getContext());


        int count = storiesResponse.getStories().size();


       /* if (counter == 0) {
            mSplashActivityViewModel.aBooleanImg.set(true);
        }*/


        List<MomentzView> momentzViews=new ArrayList<>();

        if (storiesResponse.getStories().size() > 0) {


            for (int i = 1; i < count; i++) {

                if (storiesResponse.getStories().get(i).getMediatype() == 1 || storiesResponse.getStories().get(i).getMediatype() == 0) {

                    momentzViews.add(

                    new MomentzView(
                            imageView,
                            5,
                            storiesResponse.getStories().get(i).getTitle(),
                            storiesResponse.getStories().get(i).getSubtitle(),
                            storiesResponse.getStories().get(i).getMediatype(),
                            storiesResponse.getStories().get(i).getCatIds(),
                            storiesResponse.getStories().get(i).getUrl()
                    ));


                } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {

                    momentzViews.add( new MomentzView(
                            videoView,
                          5,
                            storiesResponse.getStories().get(i).getTitle(),
                            storiesResponse.getStories().get(i).getSubtitle(),
                            storiesResponse.getStories().get(i).getMediatype(),
                            storiesResponse.getStories().get(i).getCatIds(),
                            storiesResponse.getStories().get(i).getUrl()
                    ));

                }


            }


//new Momentz(getContext(),momentzViews,mFragmentSampleBinding.container,this,R.drawable.green_lightgrey_drawable).start();


           /* if (storiesResponse.getStories().get(counter).getMediatype() == 1 || storiesResponse.getStories().get(counter).getMediatype() == 0) {


                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));


                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());

                videoView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                storyStatusView.pause();
                videoView.stopPlayback();
                target.setModel(storiesResponse.getStories().get(counter).getUrl());
                Glide.with(image.getContext())
                        .load(target.getModel())
                        .asBitmap()
                        .crossFade()
                        .skipMemoryCache(!isCaching)
                        .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(0))
                        .listener(new LoggingListener<String, Bitmap>())
                        .into(target);
            } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));

                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }

                storyStatusView.pause();
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }*/
        }
    }

    public void pauseView() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                storyStatusView.pause();
            }
        });
    }

    /*@Override
    public void onNext() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_NEXT);

        ++counter;
        mSplashActivityViewModel.aBooleanImg.set(false);
        if (storiesResponse.getStories().size() > 0 && storiesResponse.getStories().size() > counter) {
            //target = new MyProgressTarget<>(new BitmapImageViewTarget(image), imageProgressBar, txtProgress);
            if (storiesResponse.getStories().get(counter).getMediatype() == 1 || storiesResponse.getStories().get(counter).getMediatype() == 0) {
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));
                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                videoView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                storyStatusView.pause();
                videoView.stopPlayback();
                target.setModel(storiesResponse.getStories().get(counter).getUrl());
                Glide.with(image.getContext())
                        .load(target.getModel())
                        .asBitmap()
                        .crossFade()
                        .skipMemoryCache(!isCaching)
                        .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(0))
                        .listener(new LoggingListener<String, Bitmap>())
                        .into(target);
            } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));
                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                storyStatusView.pause();
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }
        }
    }

    @Override
    public void onPrev() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_PREVIOUS);


        if (counter - 1 >= 0) {
            --counter;
            if (counter == 0) {
                mSplashActivityViewModel.aBooleanImg.set(true);
            }

            if (storiesResponse.getStories().get(counter).getMediatype() == 1 || storiesResponse.getStories().get(counter).getMediatype() == 0) {
                //target = new MyProgressTarget<>(new BitmapImageViewTarget(image), imageProgressBar, txtProgress);
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));
                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }

                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                videoView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
                storyStatusView.pause();
                videoView.stopPlayback();
                target.setModel(storiesResponse.getStories().get(counter).getUrl());
                Glide.with(image.getContext())
                        .load(target.getModel())
                        .asBitmap()
                        .crossFade()
                        .skipMemoryCache(!isCaching)
                        .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(0))
                        .listener(new LoggingListener<String, Bitmap>())
                        .into(target);
            } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));
                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }

                storyStatusView.pause();
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }
        } else {
            onPreviousComplete();
        }
    }*/

    public void onPreviousComplete() {
        counter = 0;
        storyStatusView.clear();
        resetMethod();
        ((StoriesTabActivity) getActivity()).moveToPrevious();
    }

    /*@Override
    public void onComplete() {
        try {
            isVisibleToUser = false;
            StoriesResponse response = mSplashActivityViewModel.storiesResponse;
            response.getResult().get(position).getStories().get(counter).setSeen(true);
            response.getResult().get(position).setSeen(true);

            Gson gson = new Gson();
            String json = gson.toJson(response);
            mSplashActivityViewModel.getDataManager().setStoriesList(null);
            mSplashActivityViewModel.getDataManager().setStoriesList(json);

            if (position + 1 >= response.getResult().size()) {
                Objects.requireNonNull(getActivity()).finish();
                return;
            }

            counter = 0;
            storyStatusView.clear();
            resetMethod();
            ((StoriesTabActivity) getActivity()).moveToNext();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }*/

    public void resetMethod() {
        counter = 0;
        mainFunction();
    }



    public void onPlayStory(int position) {
        if (!isoNcREATRE) return;
        if (isVisibleToUser) {
            momentz.resume();

        } else if (position != 0) {
            isVisibleToUser = true;
           momentz.start();
            videoView.start();
        } else if (isoNcREATRE) {
            isVisibleToUser = true;
            momentz.start();
        }

    }

    private void onCompletePrev() {
        int pos = position - 1;
        if (videoView != null) {
            videoView.stopPlayback();
        }
        storyStatusView.destroy();
        if (pos < 0) {
            //finish();
            onDestroy();
        } else {
            //finish();
            onDestroy();
            /*Intent intent = StatusStoriesFragment.newIntent(getContext());
            intent.putExtra("position", pos);
            intent.putExtra("fullStories", storiesFullResponse);
            startActivity(intent);*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        momentz.resume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // Very important !
        super.onDestroy();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        super.onPause();
        momentz.pause(false);
    }

    @Override
    public void done() {

    }

    @Override
    public void onNextCalled(@NotNull View view, @NotNull Momentz momentz, int index, @NotNull List<MomentzView> momentzViewList) {
this.momentz=momentz;
        momentz.pause(true);
        Picasso.get()
                //.load("https://i.pinimg.com/564x/14/90/af/1490afa115fe062b12925c594d93a96c.jpg")
                .load(momentzViewList.get(index).getUrl())
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into((ImageView) view, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        momentz.resume();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });


        mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
        mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));
        if (storiesResponse.getStories().get(counter).getCatType() != null) {
            mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
            mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
        }
    }

}
