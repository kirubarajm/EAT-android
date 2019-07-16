package com.tovo.eat.ui.home.homemenu.story.library;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class StatusStoriesProvider {

    @ContributesAndroidInjector(modules = StatusStoriesModule.class)
    abstract StatusStoriesFragment provideStatusStoriesFragmentFactory();
}
