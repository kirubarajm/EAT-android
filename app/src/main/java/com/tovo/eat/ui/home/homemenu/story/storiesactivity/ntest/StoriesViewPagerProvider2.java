package com.tovo.eat.ui.home.homemenu.story.storiesactivity.ntest;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StoriesViewPagerProvider2 {

    @ContributesAndroidInjector(modules = StoriesPagerFragmentModule2.class)
    abstract StoriesPagerFragment22 provideSamplePagerFragmentFactory();
}
