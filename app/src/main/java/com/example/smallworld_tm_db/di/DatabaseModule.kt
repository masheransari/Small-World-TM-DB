package com.example.smallworld_tm_db.di

import android.content.Context
import androidx.room.Room
import com.example.smallworld_tm_db.data.local.AppDatabase
import com.example.smallworld_tm_db.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "tmdb_movie"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideMoviesDao(usersDatabase: AppDatabase): MovieDao {
        return usersDatabase.movieDao()
    }

}