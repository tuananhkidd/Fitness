package com.kidd.fitness.ui.home

import androidx.lifecycle.MutableLiveData
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.entity.User
import com.kidd.fitness.network.repo.UserRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(var repo:UserRepository) : BaseViewModel() {
    var userInfo = MutableLiveData<User>()

    init {
        userInfo.value = repo.getUserInfo()!!
    }
}
