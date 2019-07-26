package com.tovo.eat.ui.home.homemenu.story.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentStatusStoriesBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.DelayBitmapTransformation;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.LoggingListener;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.ProgressTarget;

import java.util.Locale;

import javax.inject.Inject;

public class StatusStoriesFragment extends BaseFragment<FragmentStatusStoriesBinding,StatusStoriesFramentViewModel> implements
        StatusStoriesNavigator,StoryStatusView.UserInteractionListener  {

    private static StoryStatusView storyStatusView;
    private static boolean isTextEnabled = true;
    StoriesResponse.Result storiesResponse;
    StoriesResponse storiesFullResponse;
    VideoView videoView;
    int position;
    int tempPosition;
    ProgressBar imageProgressBar;
    TextView txtProgress;
    String strPosition = "";
    private ImageView image;
    private int counter = 0;
    private long[] statusResourcesDuration;
    private long statusDuration = 3000L;
    private boolean isImmersive = true;
    private boolean isCaching = true;
    private ProgressTarget<String, Bitmap> target;

    /////////////////
    FragmentStatusStoriesBinding mFragmentStatusStoriesBinding;
    @Inject
    StatusStoriesFramentViewModel mStatusStoriesFramentViewModel;


    public static StatusStoriesFragment newInstance() {
        Bundle args = new Bundle();
        StatusStoriesFragment fragment = new StatusStoriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.statusStoriesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_status_stories;
    }

    @Override
    public StatusStoriesFramentViewModel getViewModel() {
        return mStatusStoriesFramentViewModel;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStatusStoriesFramentViewModel.setNavigator(this);
        counter=0;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentStatusStoriesBinding = getViewDataBinding();

        if (getArguments()!=null){
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

/*
        actions.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    storyStatusView.pause();
                } else {
                    storyStatusView.resume();
                }
                return true;
            }
        });
*/
        //afterViewCreated();

        mStatusStoriesFramentViewModel.getData();
    }

    public void setUserVisibleHint(int pos,StoriesResponse storiesResponseFull) {
        this.position = pos;
        this.storiesFullResponse = storiesResponseFull;
        //afterViewCreated();
    }

    public void afterViewCreated(){
            storiesResponse = storiesFullResponse.getResult().get(position);

            isImmersive = false;
            isCaching = true;
            isTextEnabled = false;

            int count = storiesResponse.getStories().size();
            if (count > 0) {
                storyStatusView.setStoriesCount(count);
            }
            storyStatusView.setStoryDuration(statusDuration);
            storyStatusView.setUserInteractionListener(this);

            target = new MyProgressTarget<>(new BitmapImageViewTarget(image), imageProgressBar, txtProgress);

            if (storiesResponse.getStories().get(counter).getMediatype() == 1) {
                videoView.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
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
            } else {
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                videoView.start();
            }

        if (position==tempPosition) {
            storyStatusView.playStories();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Log.e("position",""+position);
        afterViewCreated();
    }

    @Override
    public void onNext() {
        //storyStatusView.pause();
        counter++;
        if (counter > storiesResponse.getStories().size()) {
            onCompletePrev();
            return;
        }

        if (storiesResponse.getStories().get(counter).getMediatype() == 1) {
            storyStatusView.setStoryDuration(statusDuration);
            videoView.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            //storyStatusView.pause();
            videoView.stopPlayback();
            target.setModel(storiesResponse.getStories().get(counter).getUrl());
            Glide.with(image.getContext())
                    .load(target.getModel())
                    .asBitmap()
                    .crossFade()
                    .centerCrop()
                    .skipMemoryCache(!isCaching)
                    .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                    .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
                    .listener(new LoggingListener<String, Bitmap>())
                    .into(target);
        }else {
            storyStatusView.setStoryDuration(statusDuration);
            //storyStatusView.pause();
            videoView.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
            videoView.start();
        }
/*
        try {
            Log.e("", "" + counter);
            storyStatusView.pause();
            counter++;
                if (storiesResponse.getStories().get(counter) != null) {
                    if (storiesResponse.getStories().get(counter).getMediatype() == 1) {
                        storyStatusView.setStoryDuration(3000L);
                        videoView.stopPlayback();
                        videoView.setVisibility(View.GONE);
                        image.setVisibility(View.VISIBLE);
                        storyStatusView.pause();
                        target.setModel(storiesResponse.getStories().get(counter).getUrl());
                        Glide.with(image.getContext())
                                .load(target.getModel())
                                .asBitmap()
                                .crossFade()
                                .centerCrop()
                                .skipMemoryCache(!isCaching)
                                .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                                .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
                                .listener(new LoggingListener<String, Bitmap>())
                                .into(target);
                    } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                        storyStatusView.pause();
                        storyStatusView.setStoryDuration(30000L);
                        videoView.setVisibility(View.VISIBLE);
                        image.setVisibility(View.GONE);
                        Uri uri = Uri.parse(storiesResponse.getStories().get(counter).getUrl());
                        videoView.setVideoURI(uri);
                        videoView.start();
                    }
                }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
*/
    }

    @Override
    public void onPrev() {
        try {
            Log.e("", "" + counter);
            //storyStatusView.pause();
            --counter;
            if (counter < 0) {
                onCompletePrev();
                return;
            }
            if (storiesResponse.getStories().get(counter) != null) {
                if (storiesResponse.getStories().get(counter).getMediatype() == 1) {
                    storyStatusView.setStoryDuration(statusDuration);
                    if (counter - 1 < 0) return;
                    //storyStatusView.pause();
                    videoView.stopPlayback();
                    target.setModel(storiesResponse.getStories().get(counter).getUrl());
                    Glide.with(image.getContext())
                            .load(target.getModel())
                            .asBitmap()
                            .centerCrop()
                            .crossFade()
                            .skipMemoryCache(!isCaching)
                            .diskCacheStrategy(isCaching ? DiskCacheStrategy.ALL : DiskCacheStrategy.NONE)
                            .transform(new CenterCrop(image.getContext()), new DelayBitmapTransformation(1000))
                            .listener(new LoggingListener<String, Bitmap>())
                            .into(target);
                } else if (storiesResponse.getStories().get(counter).getMediatype() == 2) {
                    //storyStatusView.pause();
                    storyStatusView.setStoryDuration(statusDuration);
                    videoView.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                    Uri uri = Uri.parse(storiesResponse.getStories().get(counter).getUrl());
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {
        try {
            int pos = position + 1;
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
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_status_stories);

        try {
            Bundle bundle = getIntent().getExtras();

            if (bundle != null) {
                position = bundle.getInt("position");
                storiesFullResponse = (StoriesResponse) bundle.getSerializable("fullStories");
                if (position > storiesFullResponse.getResult().size()) return;
                storiesResponse = storiesFullResponse.getResult().get(position);
            }

            ProgressBar imageProgressBar = findViewById(R.id.imageProgressBar);
            TextView textView = findViewById(R.id.textView);
            image = findViewById(R.id.image_stories);
            videoView = findViewById(R.id.video_view);

            storyStatusView = findViewById(R.id.storiesStatus);
            storyStatusView.setStoriesCount(storiesResponse.getStories().size());
            storyStatusView.setStoryDuration(3000L);
            // or
            //statusView.setStoriesCountWithDurations(statusResourcesDuration);
            storyStatusView.setUserInteractionListener(this);
            if (position > storiesFullResponse.getResult().size()) return;
            if (storiesResponse.getStories().size() > 0) {
                storyStatusView.playStories();
            }

            target = new MyProgressTarget<>(new BitmapImageViewTarget(image), imageProgressBar, textView);

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storyStatusView.skip();
                }
            });

            findViewById(R.id.reverse).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storyStatusView.reverse();
                }
            });

            findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    storyStatusView.skip();
                }
            });

            findViewById(R.id.actions).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        storyStatusView.pause();
                    } else {
                        storyStatusView.resume();
                    }
                    return true;
                }
            });

            if (storiesResponse.getStories().size() > 0) {
                if (storiesResponse.getStories().get(counter).getMediatype() == 1) {
                    videoView.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    storyStatusView.pause();
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
                    videoView.setVisibility(View.VISIBLE);
                    image.setVisibility(View.GONE);
                    videoView.setVideoPath(storiesResponse.getStories().get(counter).getUrl());
                    videoView.start();
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }
*/

    @Override
    public void onPause() {
        //storyStatusView.pause();
        super.onPause();
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
    public void onDestroy() {
        // Very important !
        storyStatusView.destroy();
        super.onDestroy();
    }

    @Override
    public void getStoriesDataFromLocal(StoriesResponse storiesResponseFull) {
        this.storiesFullResponse = storiesResponseFull;
        //afterViewCreated();
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


            //storyStatusView.pause();

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
            //storyStatusView.pause();
        }

        @Override
        protected void onDelivered() {
            progress.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
           // storyStatusView.resume();
        }
    }
}
