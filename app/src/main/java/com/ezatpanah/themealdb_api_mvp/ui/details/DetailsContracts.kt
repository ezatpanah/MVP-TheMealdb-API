package com.ezatpanah.themealdb_api_mvp.ui.details

import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import com.ezatpanah.themealdb_api_mvp.response.FoodsListResponse
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenter
import com.ezatpanah.themealdb_api_mvp.ui.base.BaseView

interface DetailsContracts {
    interface View : BaseView {
        fun loadDetail(data: FoodsListResponse)
        fun updateFavorite(isAdded: Boolean)
    }

    interface Presenter : BasePresenter {
        fun callDetailApi(id: Int)
        fun saveFood(entity: FoodEntity)
        fun deleteFood(entity: FoodEntity)
        fun checkFavorite(id: Int)
    }
}