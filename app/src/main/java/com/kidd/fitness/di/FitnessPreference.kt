package com.kidd.fitness.di

import com.kidd.fitness.entity.User
import javax.inject.Singleton

@Singleton
interface FitnessPreference {
    fun saveUserInfo(user:User)

    fun getUserInfo() : User?
}