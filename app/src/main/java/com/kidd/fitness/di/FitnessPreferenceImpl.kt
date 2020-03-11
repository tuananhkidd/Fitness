package com.kidd.fitness.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kidd.fitness.entity.User
import com.kidd.fitness.extension.setString
import javax.inject.Inject

class FitnessPreferenceImpl @Inject constructor(context: Context) : FitnessPreference {
    private lateinit var mPref: SharedPreferences

    companion object {
        const val PREF_FILE_NAME = "fitness"
        const val KEY_USER = "user"
    }

    init {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    override fun saveUserInfo(user: User) {
        mPref.setString(KEY_USER, Gson().toJson(user))
    }

    override fun getUserInfo(): User? =
        Gson().fromJson(mPref.getString(KEY_USER, ""), User::class.java) ?: null
}