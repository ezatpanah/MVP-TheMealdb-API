package com.ezatpanah.themealdb_api_mvp.repository

import com.ezatpanah.themealdb_api_mvp.api.ApiServices
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiServices: ApiServices){
    fun loadFoodRandom()=apiServices.getFoodRandom()
}