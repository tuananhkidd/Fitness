package com.kidd.fitness.di.module

import com.kidd.fitness.ui.SplashFragment
import com.kidd.fitness.ui.home.HomeFragment
import com.kidd.fitness.ui.insert_food.InertMealFragment
import com.kidd.fitness.ui.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {
    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun bindInertMealFragment(): InertMealFragment

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment
}