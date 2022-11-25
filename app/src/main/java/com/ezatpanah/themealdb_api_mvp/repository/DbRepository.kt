package com.ezatpanah.themealdb_api_mvp.repository

import com.ezatpanah.themealdb_api_mvp.api.ApiServices
import com.ezatpanah.themealdb_api_mvp.db.FoodDao
import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import javax.inject.Inject

class DbRepository  @Inject constructor(private val dao: FoodDao){
    fun saveFood(entity: FoodEntity) = dao.saveFood(entity)
    fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)
    fun existsFood(id: Int) = dao.existsFood(id)
    fun loadAllFoods() = dao.getAllFoods()
}