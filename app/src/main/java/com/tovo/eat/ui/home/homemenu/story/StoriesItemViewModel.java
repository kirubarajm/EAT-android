package com.tovo.eat.ui.home.homemenu.story;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class StoriesItemViewModel {


    public final ObservableField<String> stories_url = new ObservableField<>();
    public StoriesItemViewModelListener mListener;
    public StoriesResponse.Result blog;
    public final ObservableBoolean isSeen = new ObservableBoolean();
    public StoriesItemViewModel(StoriesItemViewModelListener mListener, StoriesResponse.Result blog) {
        this.mListener = mListener;
        this.blog = blog;

        this.stories_url.set(blog.getThumb());

       isSeen.set(blog.getStories().get(blog.getStories().size()-1).isSeen());



    }

    public void onItemClick() {
        if (blog.getStories().size()>0) {
            mListener.onItemClick(blog);
        }
    }

    public interface StoriesItemViewModelListener {

        void onItemClick(StoriesResponse.Result blog);
    }
}
