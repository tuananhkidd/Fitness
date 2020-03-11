package com.kidd.fitness.ui

import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.network.repo.UserRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(var repo: UserRepository) : BaseViewModel() {
    fun isUserLogin() = repo.getUserInfo() != null
}
