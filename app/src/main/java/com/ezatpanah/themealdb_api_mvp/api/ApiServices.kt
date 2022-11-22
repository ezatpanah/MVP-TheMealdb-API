package com.ezatpanah.themealdb_api_mvp.api

import com.ezatpanah.themealdb_api_mvp.response.FoodsListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    //https://www.themealdb.com/api.php
    /**
     *
    Search meal by name
    www.themealdb.com/api/json/v1/1/search.php?s=Arrabiata
    List all meals by first letter
    www.themealdb.com/api/json/v1/1/search.php?f=a
    Lookup full meal details by id
    www.themealdb.com/api/json/v1/1/lookup.php?i=52772
    Lookup a single random meal
    www.themealdb.com/api/json/v1/1/random.php

    **/

    @GET
    fun getFoodRandom() : Single<Response<FoodsListResponse>>

}