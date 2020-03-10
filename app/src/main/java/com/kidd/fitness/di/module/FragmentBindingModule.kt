package com.kidd.fitness.di.module

import com.kidd.fitness.ui.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment
}