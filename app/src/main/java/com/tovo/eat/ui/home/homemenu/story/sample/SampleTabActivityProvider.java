package com.tovo.eat.ui.home.homemenu.story.sample;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SampleTabActivityProvider {

    @ContributesAndroidInjector(modules = SampleTabActivityModule.class)
    abstract SampleTabActivity provideSampleTabFragmentFactory();
}
