package com.tovo.eat.ui.home.homemenu.story.storyviewpager;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StoriesViewPagerProvider {

    @ContributesAndroidInjector(modules = StoriesViewPagerActivityModule.class)
    abstract StoriesViewPagerActivity provideStoriesViewPagerFragmentFactory();
}
