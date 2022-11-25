package com.ezatpanah.themealdb_api_mvp.ui.favorites

import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenter

interface FavoritesContracts {
    interface View {
        fun showAllFoods(list: MutableList<FoodEntity>)
    }

    interface Presenter : BasePresenter {
        fun loadAllFood()
    }
}