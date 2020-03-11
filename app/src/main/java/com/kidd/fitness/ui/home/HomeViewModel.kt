package com.kidd.fitness.ui.home

import android.util.Log
import com.kidd.fitness.base.BaseViewModel
import com.kidd.fitness.network.Repository
import com.kidd.fitness.network.repo.UserRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(var repo:UserRepository) : BaseViewModel() {
    fun getUser(){
        Log.v("ahuhu","${repo.getUserInfo()?.id  + repo.getUserInfo()?.name}")
    }
}
