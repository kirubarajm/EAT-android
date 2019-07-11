package com.tovo.eat.ui.home.homemenu.story;


import android.databinding.ObservableField;

public class StoriesItemViewModel {


    public final ObservableField<String> stories_url = new ObservableField<>();
    public StoriesItemViewModelListener mListener;
    public StoriesResponse.Result blog;

    public StoriesItemViewModel(StoriesItemViewModelListener mListener, StoriesResponse.Result blog) {
        this.mListener = mListener;
        this.blog = blog;

        this.stories_url.set(blog.getThumb());
    }

    public void onItemClick() {
        mListener.onItemClick(blog);
    }

    public interface StoriesItemViewModelListener {

        void onItemClick(StoriesResponse.Result blog);
    }
}
