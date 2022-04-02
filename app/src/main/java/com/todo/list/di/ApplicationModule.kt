package com.todo.list.di

import android.content.Context
import com.todo.list.data.database.TaskDatabase
import com.todo.list.data.preferences.PreferenceStorage
import com.todo.list.data.preferences.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase = TaskDatabase.buildDatabase(context)

    @Provides
    @Singleton
    fun providePreferenceStorage(@ApplicationContext context: Context): PreferenceStorage = SharedPreferenceStorage(context)
}