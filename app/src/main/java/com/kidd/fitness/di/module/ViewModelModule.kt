package com.kidd.fitness.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.kidd.fitness.di.ViewModelFactory
import com.kidd.fitness.ui.SplashViewModel
import com.kidd.fitness.ui.home.HomeViewModel
import com.kidd.fitness.ui.insert_food.InertMealViewModel
import com.kidd.fitness.ui.insert_meal.CreateMealViewModel
import com.kidd.fitness.ui.login.LoginViewModel
import com.kidd.fitness.ui.meal.UserMealViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(InertMealViewModel::class)
    internal abstract fun bindInertMealViewModel(viewModel: InertMealViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserMealViewModel::class)
    internal abstract fun bindUserMealViewModel(viewModel: UserMealViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateMealViewModel::class)
    internal abstract fun bindCreateMealViewModel(viewModel: CreateMealViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
