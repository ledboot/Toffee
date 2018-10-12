package com.ledboot.toffee

import androidx.lifecycle.ViewModel
import com.ledboot.toffee.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class LauncherModule {

    @Binds
    @IntoMap
    @ViewModelKey(LauncherViewModel::class)
    abstract fun bindLaucherViewModel(viewModel: LauncherViewModel): ViewModel
}