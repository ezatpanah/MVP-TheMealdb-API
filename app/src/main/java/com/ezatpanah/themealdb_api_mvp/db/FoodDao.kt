package com.ezatpanah.themealdb_api_mvp.db

import androidx.room.*
import com.ezatpanah.themealdb_api_mvp.utils.Constant.FOOD_TABLE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFood(entity: FoodEntity): Completable

    @Delete
    fun deleteFood(entity: FoodEntity): Completable

    @Query("SELECT * FROM $FOOD_TABLE")
    fun getAllFoods(): Observable<MutableList<FoodEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_TABLE WHERE id = :id)")
    fun existsFood(id: Int): Observable<Boolean>
}