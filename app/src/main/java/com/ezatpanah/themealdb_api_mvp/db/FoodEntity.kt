package com.ezatpanah.themealdb_api_mvp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ezatpanah.themealdb_api_mvp.utils.Constant.FOOD_TABLE

@Entity(tableName = FOOD_TABLE)
data class FoodEntity(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var img: String = "",
    var cat: String = "",
    var area: String = "",
)