package com.ezatpanah.themealdb_api_mvp.api

import com.ezatpanah.themealdb_api_mvp.response.CategoriesListResponse
import com.ezatpanah.themealdb_api_mvp.response.FoodsListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    //https://www.themealdb.com/api.php
    /**

     Header
    Lookup a single random meal
    www.themealdb.com/api/json/v1/1/random.php

    Categories
    List all meal categories
    www.themealdb.com/api/json/v1/1/categories.php

    Search meal by name
    www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata

    List all meals by first letter
    www.themealdb.com/api/json/v1/1/search.php?f=a


     Filter by Category
     www.themealdb.com/api/json/v1/1/filter.php?c=Seafood

    **/

    @GET("random.php")
    fun getFoodRandom() : Single<Response<FoodsListResponse>>

    @GET("categories.php")
    fun getCategoriesList() : Single<Response<CategoriesListResponse>>

    @GET("search.php")
    fun getFoodList(@Query("f") latter : String) : Single<Response<FoodsListResponse>>

    @GET("search.php")
    fun searchList(@Query("s") latter : String) : Single<Response<FoodsListResponse>>

    @GET("filter.php")
    fun filterList(@Query("c") latter : String) : Single<Response<FoodsListResponse>>
}