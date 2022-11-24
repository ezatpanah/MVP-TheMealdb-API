package com.ezatpanah.themealdb_api_mvp.ui.home

import com.ezatpanah.themealdb_api_mvp.response.CategoriesListResponse
import com.ezatpanah.themealdb_api_mvp.response.FoodsListResponse
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenter
import com.ezatpanah.themealdb_api_mvp.ui.base.BaseView

interface HomeContracts {
    interface View : BaseView {
        fun loadFoodRandom(data: FoodsListResponse)
        fun loadCategories(data: CategoriesListResponse)
        fun loadFoodsList(data: FoodsListResponse)
        fun foodsLoadingState(isShown: Boolean)
        fun emptyList()
    }

    interface Presenter : BasePresenter {
        fun callFoodRandom()
        fun callCategoriesList()
        fun callFoodsList(letter: String)
//        fun callSearchFood(letter: String)
//        fun callFoodsByCategory(letter: String)
    }
}