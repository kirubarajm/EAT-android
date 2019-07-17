package com.tovo.eat.ui.home.homemenu.story.sample.fragment;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SampleViewPagerProvider {

    @ContributesAndroidInjector(modules = SamplePagerFragmentModule.class)
    abstract SamplePagerFragment provideSamplePagerFragmentFactory();
}
