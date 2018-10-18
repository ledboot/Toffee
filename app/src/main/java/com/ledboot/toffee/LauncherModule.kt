package com.ledboot.toffee

import com.ledboot.toffee.di.FragmentScoped
import com.ledboot.toffee.module.girl.GirlFragment
import com.ledboot.toffee.module.home.HomeFragment
import com.ledboot.toffee.module.user.UserFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class LauncherModule {


    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeHomeFrament(): HomeFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeGirlFrament(): GirlFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeUserFrament(): UserFragment
}