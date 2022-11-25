package com.ezatpanah.themealdb_api_mvp.di

import android.content.Context
import androidx.room.Room
import com.ezatpanah.themealdb_api_mvp.db.FoodDatabase
import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import com.ezatpanah.themealdb_api_mvp.utils.Constant.FOOD_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, FoodDatabase::class.java, FOOD_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(db: FoodDatabase) = db.foodDao()

    @Provides
    @Singleton
    fun provideEntity() = FoodEntity()
}