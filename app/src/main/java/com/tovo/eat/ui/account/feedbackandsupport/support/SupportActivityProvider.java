package com.tovo.eat.ui.account.feedbackandsupport.support;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SupportActivityProvider {

    @ContributesAndroidInjector(modules = SupportActivityModule.class)
    abstract SupportActivity provideQueriesFragmentFactory();
}
