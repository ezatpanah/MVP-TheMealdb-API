package com.ezatpanah.themealdb_api_mvp.ui.home

import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenter
import com.ezatpanah.themealdb_api_mvp.ui.base.BaseView

interface HomeContracts {
    interface View : BaseView {
        fun loadFoodRandom(data: ResponseFoodsList)
        fun loadCategories(data: ResponseCategoriesList)
        fun loadFoodsList(data: ResponseFoodsList)
        fun foodsLoadingState(isShown: Boolean)
        fun emptyList()
    }

    interface Presenter : BasePresenter {
        fun callFoodRandom()
        fun callCategoriesList()
        fun callFoodsList(letter: String)
        fun callSearchFood(letter: String)
        fun callFoodsByCategory(letter: String)
    }
}