package com.kidd.fitness.network.repo

import com.kidd.fitness.di.FitnessPreferenceImpl
import com.kidd.fitness.entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(var fitnessPreference: FitnessPreferenceImpl) {
    fun setUserInfo(user:User){
        fitnessPreference.saveUserInfo(user)
    }

    fun getUserInfo() : User? = fitnessPreference.getUserInfo()
}