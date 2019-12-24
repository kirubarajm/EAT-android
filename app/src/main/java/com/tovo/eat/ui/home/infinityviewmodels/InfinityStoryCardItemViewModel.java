package com.tovo.eat.ui.home.infinityviewmodels;

import android.databinding.ObservableField;

import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;


public class InfinityStoryCardItemViewModel {

    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> subTitle = new ObservableField<>();
    public final ObservableField<String> content = new ObservableField<>();
    public final ObservableField<String> imageUrl = new ObservableField<>();


    public final InfinityStoryItemViewModelListener mListener;

    KitchenResponse.Story stories;
    int position=0;

    public InfinityStoryCardItemViewModel(InfinityStoryItemViewModelListener mListener, KitchenResponse.Story stories,int position) {
        this.mListener = mListener;
        this.stories = stories;
        this.position=position;
        imageUrl.set(stories.getStoryCollectionImg());

    }

    public void onItemClick() {
        mListener.onItemClick(stories,position);
    }

    public interface InfinityStoryItemViewModelListener {
        void onItemClick(KitchenResponse.Story stories,int position);
    }

}
