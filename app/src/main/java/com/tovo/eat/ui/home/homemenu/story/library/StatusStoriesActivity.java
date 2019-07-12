package com.tovo.eat.ui.home.homemenu.story.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.tovo.eat.R;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.DelayBitmapTransformation;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.LoggingListener;
import com.tovo.eat.ui.home.homemenu.story.library.glideProgressBar.ProgressTarget;

import java.util.Locale;

public class StatusStoriesActivity extends AppCompatActivity implements StoryStatusView.UserInteractionListener {

    private static StoryStatusView storyStatusView;
    private static boolean isTextEnabled = true;
    StoriesResponse.Result storiesResponse;
    StoriesResponse storiesFullResponse;
    VideoView videoView;
    int position;
    private ImageView image;
    private int counter = 0;
    private String[] statusResources;
    //    private long[] statusResourcesDuration;
    private long statusDuration;
    private boolean isImmersive = true;
    private boolean isCaching = true;
    private ProgressTarget<String, Bitmap> target;

    public static Intent newIntent(Context context) {
        return new Intent(context, StatusStoriesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_stories);

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

    @Override
    public void onNext() {
        Log.e("", "" + counter);
        storyStatusView.pause();
        counter++;
        if (counter > storiesResponse.getStories().size()) return;
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
                storyStatusView.setStoryDuration(30000L);
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                Uri uri = Uri.parse(storiesResponse.getStories().get(counter).getUrl());
                videoView.setVideoURI(uri);
                videoView.start();
            }
        }
    }

    @Override
    public void onPrev() {
        Log.e("", "" + counter);
        storyStatusView.pause();
        --counter;
        if (counter < 0) {
            onCompletePrev();
            return;
        }
        if (storiesResponse.getStories().get(counter) != null) {
            if (storiesResponse.getStories().get(counter).getMediatype() == 1) {
                storyStatusView.setStoryDuration(3000L);
                if (counter - 1 < 0) return;
                storyStatusView.pause();
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
                storyStatusView.setStoryDuration(30000L);
                videoView.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
                Uri uri = Uri.parse(storiesResponse.getStories().get(counter).getUrl());
                videoView.setVideoURI(uri);
                videoView.start();
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isImmersive && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            if (hasFocus) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    @Override
    public void onComplete() {
        int pos = position + 1;
        if (videoView != null) {
            videoView.stopPlayback();
        }
        storyStatusView.destroy();
        if (pos > storiesFullResponse.getResult().size()) {
            finish();
        } else {
            finish();
            Intent intent = StatusStoriesActivity.newIntent(this);
            intent.putExtra("position", pos);
            intent.putExtra("fullStories", storiesFullResponse);
            startActivity(intent);
/*
            ActivitySwitcher.animationOut(findViewById(R.id.container),getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    startActivity(intent);
                }
            });
*/

        }
    }

    private void onCompletePrev() {
        int pos = position - 1;
        if (videoView != null) {
            videoView.stopPlayback();
        }
        storyStatusView.destroy();
        if (pos < 0) {
            finish();
        } else {
            finish();
            Intent intent = StatusStoriesActivity.newIntent(this);
            intent.putExtra("position", pos);
            intent.putExtra("fullStories", storiesFullResponse);
            startActivity(intent);
/*
            ActivitySwitcher.animationOut(findViewById(R.id.container),getWindowManager(), new ActivitySwitcher.AnimationFinishedListener() {
                @Override
                public void onAnimationFinished() {
                    startActivity(intent);
                }
            });
*/

        }
    }

    @Override
    protected void onDestroy() {
        // Very important !
        super.onDestroy();
    }

/*
    @Override
    protected void onResume() {
        // animateIn this activity
        //ActivitySwitcher.animationIn(findViewById(R.id.container), getWindowManager());
        super.onResume();
    }
*/

    /**
     * Demonstrates 3 different ways of showing the progress:
     * <ul>
     * <li>Update a full fledged progress bar</li>
     * <li>Update a text view to display size/percentage</li>
     * <li>Update the placeholder via Drawable.level</li>
     * </ul>
     * This last one is tricky: the placeholder that Glide sets can be used as a progress drawable
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
            storyStatusView.pause();
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


            storyStatusView.pause();

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
            storyStatusView.pause();
        }

        @Override
        protected void onDelivered() {
            progress.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
            storyStatusView.resume();
        }
    }
}
