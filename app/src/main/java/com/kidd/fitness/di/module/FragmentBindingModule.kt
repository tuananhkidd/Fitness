package com.kidd.fitness.di.module

import com.kidd.fitness.ui.SplashFragment
import com.kidd.fitness.ui.history.HistoryFragment
import com.kidd.fitness.ui.home.HomeFragment
import com.kidd.fitness.ui.insert_food.InertMealFragment
import com.kidd.fitness.ui.insert_meal.CreateMealFragment
import com.kidd.fitness.ui.login.LoginFragment
import com.kidd.fitness.ui.meal.UserMealFragment
import com.kidd.fitness.ui.register.RegisterFragment
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
    abstract fun bindUserMealFragment(): UserMealFragment

    @ContributesAndroidInjector
    abstract fun bindCreateMealFragment(): CreateMealFragment

    @ContributesAndroidInjector
    abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun bindRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    abstract fun bindHistoryFragment(): HistoryFragment
}