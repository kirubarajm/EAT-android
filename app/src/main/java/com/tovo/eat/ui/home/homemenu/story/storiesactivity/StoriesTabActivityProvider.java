package com.tovo.eat.ui.home.homemenu.story.storiesactivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StoriesTabActivityProvider {

    @ContributesAndroidInjector(modules = StoriesTabActivityModule.class)
    abstract StoriesTabActivity provideSampleTabFragmentFactory();
}
