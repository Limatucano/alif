package com.tcc.alif.di

import android.content.Context
import android.content.SharedPreferences
import com.tcc.alif.data.local.SharedPreferencesHelper
import com.tcc.alif.data.local.SharedPreferencesHelper.Companion.SHARED_PREFERENCES_NAME
import com.tcc.alif.data.util.getEncryptedSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule{

    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getEncryptedSharedPreferences(SHARED_PREFERENCES_NAME)
    }

    @Singleton
    @Provides
    fun provideSharedPreferenceHelper(
        sharedPreferences: SharedPreferences
    ) : SharedPreferencesHelper = SharedPreferencesHelper(
        sharedPreferences = sharedPreferences
    )
}