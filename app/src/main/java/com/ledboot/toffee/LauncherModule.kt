package com.ledboot.toffee

import androidx.lifecycle.ViewModel
import com.ledboot.toffee.di.FragmentScoped
import com.ledboot.toffee.di.ViewModelKey
import com.ledboot.toffee.module.girl.GirlFragment
import com.ledboot.toffee.module.home.HomeFragment
import com.ledboot.toffee.module.home.HomeViewModel
import com.ledboot.toffee.module.user.UserFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

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

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}