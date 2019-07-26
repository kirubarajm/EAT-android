package com.tovo.eat.ui.home.homemenu.story.sample.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentSampleBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.StoryStatusView;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.DelayBitmapTransformation;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.LoggingListener;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.ProgressTarget;
import com.tovo.eat.ui.home.homemenu.story.sample.SampleTabActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;

import java.util.Locale;

import javax.inject.Inject;

public class SamplePagerFragment extends BaseFragment<FragmentSampleBinding, SamplePagerFragmentViewModel>
        implements SamplePagerFragmentNavigator, StoryStatusView.UserInteractionListener {

    private static boolean isTextEnabled = true;
    @Inject
    SamplePagerFragmentViewModel mSplashActivityViewModel;
    StoriesResponse storiesFullResponse;
    int position;
    int tempPosition = 0;
    boolean isVisibleToUser;
    boolean isoNcREATRE;
    StoriesResponse.Result storiesResponse;
    VideoView videoView;
    ProgressBar imageProgressBar;
    TextView txtProgress;
    String strPosition = "";
    private StoryStatusView storyStatusView;
    private FragmentSampleBinding mFragmentSampleBinding;
    private ImageView image;
    private int counter = 0;
    private long[] statusResourcesDuration;
    private long statusDuration = 3000L;
    private boolean isImmersive = true;
    private boolean isCaching = true;
    private ProgressTarget<String, Bitmap> target;

    public static SamplePagerFragment newInstance() {
        Bundle args = new Bundle();
        SamplePagerFragment fragment = new SamplePagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onSeeMore() {
        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", mSplashActivityViewModel.category_id.get());
        intent.putExtra("type", mSplashActivityViewModel.category_type.get());
        startActivity(intent);
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
    public SamplePagerFragmentViewModel getViewModel() {
        return mSplashActivityViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashActivityViewModel.setNavigator(this);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mFragmentSampleBinding = getViewDataBinding();
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }

        image = view.findViewById(R.id.image_stories1);
        View reverseClick = view.findViewById(R.id.reverse);
        View skipClick = view.findViewById(R.id.skip);
        View actions = view.findViewById(R.id.actions);
        txtProgress = view.findViewById(R.id.textView);
        storyStatusView = view.findViewById(R.id.storiesStatus);
        imageProgressBar = view.findViewById(R.id.imageProgressBar);
        videoView = view.findViewById(R.id.video_view);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storyStatusView.skip();
            }
        });

        reverseClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyStatusView.reverse();
            }
        });

        skipClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyStatusView.skip();
            }
        });

        actions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    storyStatusView.pause();
                    videoView.pause();
                } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
                    storyStatusView.resume();
                    videoView.start();
                }
                return true;
            }
        });

        counter = 0;
        isoNcREATRE = true;
        mSplashActivityViewModel.getData();
        ((SampleTabActivity) getActivity()).methodDDD(position);
        Log.e("position", "" + position);
/*
        if (position == 0 && position == tempPosition) {
            isVisibleToUser = true;
            storyStatusView.playStories();
            videoView.start();
        }
*/
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // isoNcREATRE = isVisibleToUser;

    }

    public void onPlayStorie(int position) {

        if (!isoNcREATRE) return;
        if (isVisibleToUser) {
            storyStatusView.resume();
            videoView.start();
        } else if (position != 0) {
            isVisibleToUser = true;
            // mSplashActivityViewModel.getData();
            storyStatusView.playStories();
            videoView.start();
        } else if (isoNcREATRE) {
            //mSplashActivityViewModel.getData();
            storyStatusView.playStories();
            videoView.start();
        }
    }

    public void onPasue(int position) {
        if (!isoNcREATRE) return;
        storyStatusView.pause();
        videoView.pause();
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

        int count = storiesResponse.getStories().size();
        if (count > 0) {
            storyStatusView.setStoriesCount(count);
        }
        storyStatusView.setUserInteractionListener(this);
        //  storyStatusView.playStories();
        target = new MyProgressTarget<>(new BitmapImageViewTarget(image), imageProgressBar, txtProgress);
        // storyStatusView.pause();
        if (counter == 0) {
            mSplashActivityViewModel.aBooleanImg.set(true);
        }

        if (storiesResponse.getStories().size() > 0) {
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
                // storyStatusView.pause();
                videoView.stopPlayback();
                target.setModel(storiesResponse.getStories().get(counter).getUrl());
                Glide.with(image.getContext())
                        .load(target.getModel())
                        .asBitmap()
                        .crossFade()
                        .skipMemoryCache(!isCaching)
                        .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
                        .listener(new LoggingListener<String, Bitmap>())
                        .into(target);
            } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));

                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }

                // storyStatusView.pause();
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }
        }
       /* } else {
            storyStatusView.clear();
            storyStatusView.destroy();
            getActivity().finish();
        }*/
    }

    @Override
    public void onNext() {
        ++counter;
        mSplashActivityViewModel.aBooleanImg.set(false);
        if (storiesResponse.getStories().size() > 0) {
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
                //storyStatusView.pause();
                videoView.stopPlayback();
                target.setModel(storiesResponse.getStories().get(counter).getUrl());
                Glide.with(image.getContext())
                        .load(target.getModel())
                        .asBitmap()
                        .crossFade()
                        .skipMemoryCache(!isCaching)
                        .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
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
                //storyStatusView.pause();
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }
        }
        /*}else {
            storyStatusView.clear();
            storyStatusView.destroy();
            getActivity().finish();
        }*/
    }

    @Override
    public void onPrev() {
        if (counter - 1 >= 0) {
            --counter;
            if (counter == 0) {
                mSplashActivityViewModel.aBooleanImg.set(true);
            }

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
                //storyStatusView.pause();
                videoView.stopPlayback();
                target.setModel(storiesResponse.getStories().get(counter).getUrl());
                Glide.with(image.getContext())
                        .load(target.getModel())
                        .asBitmap()
                        .crossFade()
                        .skipMemoryCache(!isCaching)
                        .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                        .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
                        .listener(new LoggingListener<String, Bitmap>())
                        .into(target);
            } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                mSplashActivityViewModel.title.set(String.valueOf(storiesResponse.getStories().get(counter).getTitle()));
                mSplashActivityViewModel.subTitle.set(String.valueOf(storiesResponse.getStories().get(counter).getSubtitle()));
                if (storiesResponse.getStories().get(counter).getCatType() != null) {
                    mSplashActivityViewModel.category_type.set((storiesResponse.getStories().get(counter).getCatType()));
                    mSplashActivityViewModel.category_id.set((storiesResponse.getStories().get(counter).getCatIds()));
                }

                //storyStatusView.pause();
                storyStatusView.setStoryDuration(storiesResponse.getStories().get(counter).getDuration());
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }
        } else {
            onPreviousComplete();
        }
    }

    public void onPreviousComplete() {
        counter = 0;
        storyStatusView.clear();
        resetMethod();
        ((SampleTabActivity) getActivity()).moveToPrevious();
    }

    @Override
    public void onComplete() {
        try {
            isVisibleToUser = false;
            /*int pos = position + 1;
            if (videoView != null) {
                videoView.stopPlayback();
            }
            storyStatusView.destroy();
            if (pos > storiesFullResponse.getResult().size()) {
                //finish();
            } else {
                //finish();
                onDetach();
                //Intent intent = StatusStoriesFragment.newIntent(getContext());
                //intent.putExtra("position", pos);
                //intent.putExtra("fullStories", storiesFullResponse);
                //startActivity(intent);
            }*/
            counter = 0;
            storyStatusView.clear();
            resetMethod();
            ((SampleTabActivity) getActivity()).moveToNext();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void resetMethod() {
        counter = 0;
        mainFunction();
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
        storyStatusView.destroy();
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
    }

    /**
     * Demonstrates 3 different ways of showing the txtProgress:
     * <ul>
     * <li>Update a full fledged txtProgress bar</li>
     * <li>Update a text view to display size/percentage</li>
     * <li>Update the placeholder via Drawable.level</li>
     * </ul>
     * This last one is tricky: the placeholder that Glide sets can be used as a txtProgress drawable
     * without any extra Views in the view hierarchy if it supports levels via <code>usesLevel="true"</code>
     * or <code>level-list</code>.
     *
     * @param <Z> automatically match any real Glide target so it can be used flexibly without reimplementing.
     */
    @SuppressLint("SetTextI18n") // text set only for debugging
    private static class MyProgressTarget<Z> extends ProgressTarget<String, Z> {
        private final TextView text;
        private final ProgressBar progress;

        public MyProgressTarget(Target<Z> target, ProgressBar progress, TextView text) {
            super(target);
            this.progress = progress;
            this.text = text;
        }

        @Override
        public float getGranualityPercentage() {
            return 0.1f; // this matches the format string for #text below
        }

        @Override
        protected void onConnecting() {
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);

            if (isTextEnabled) {
                text.setVisibility(View.VISIBLE);
                text.setText("connecting");
            } else {
                text.setVisibility(View.INVISIBLE);
            }
            //storyStatusView.pause();
        }

        @Override
        protected void onDownloading(long bytesRead, long expectedLength) {
            progress.setIndeterminate(false);
            progress.setProgress((int) (100 * bytesRead / expectedLength));

            if (isTextEnabled) {
                text.setVisibility(View.VISIBLE);
                text.setText(String.format(Locale.ROOT, "downloading %.2f/%.2f MB %.1f%%",
                        bytesRead / 1e6, expectedLength / 1e6, 100f * bytesRead / expectedLength));
            } else {
                text.setVisibility(View.INVISIBLE);
            }


            //  storyStatusView.pause();

        }

        @Override
        protected void onDownloaded() {
            progress.setIndeterminate(true);
            if (isTextEnabled) {
                text.setVisibility(View.VISIBLE);
                text.setText("decoding and transforming");
            } else {
                text.setVisibility(View.INVISIBLE);
            }
            // storyStatusView.pause();
        }

        @Override
        protected void onDelivered() {
            progress.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
            //storyStatusView.resume();
        }
    }
}
