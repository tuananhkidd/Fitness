package com.kidd.fitness.di.module

import com.kidd.fitness.di.FitnessPreference
import com.kidd.fitness.di.FitnessPreferenceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharePrefModule {
    @Provides
    @Singleton
    fun provideFitnessPreference(fitnessPreference: FitnessPreferenceImpl): FitnessPreference {
        return fitnessPreference
    }
}