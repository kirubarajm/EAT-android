package com.tovo.eat.ui.home.homemenu.story.storiesactivity.fragment;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StoriesViewPagerProvider {

    @ContributesAndroidInjector(modules = StoriesPagerFragmentModule.class)
    abstract StoriesPagerFragment provideSamplePagerFragmentFactory();
}
