package com.ezatpanah.themealdb_api_mvp.ui.home

import com.ezatpanah.themealdb_api_mvp.repository.ApiRepository
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenterImpl
import com.ezatpanah.themealdb_api_mvp.utils.applyIoScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val repository: ApiRepository,
    val view: HomeContracts.View,
) : BasePresenterImpl(), HomeContracts.Presenter {
    override fun callFoodRandom() {
        if (view.checkInternet()) {
            disposable = repository.getFoodRandom()
                .applyIoScheduler()
                .subscribe({ response ->
                    view.hideLoading()
                    when (response.code()) {
                        in 200..202 -> {
                            response.body()?.let {
                                view.loadFoodRandom(it)
                            }
                        }
                        in 400..499 -> {
                            view.serverError("${response.code()}")
                        }
                        in 500..599 -> {
                            view.serverError("${response.code()}")
                        }
                    }
                }, { error ->
                    view.serverError(error.message.toString())
                    view.hideLoading()
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callCategoriesList() {
        if (view.checkInternet()) {
            view.showLoading()
            disposable = repository.getCategoriesList()
                .applyIoScheduler()
                .subscribe({ response ->
                    view.hideLoading()
                    when (response.code()) {
                        in 200..202 -> {
                            response.body()?.let {
                                if (it.categories!!.isNotEmpty()) {
                                    view.loadCategories(it)
                                }
                            }
                        }
                        in 400..499 -> {
                            view.serverError("${response.code()}")
                        }
                        in 500..599 -> {
                            view.serverError("${response.code()}")
                        }
                    }
                }, { error ->
                    view.serverError(error.message.toString())
                    view.hideLoading()
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callFoodsList(letter: String) {
        if (view.checkInternet()) {
            view.foodsLoadingState(true)
            disposable = repository.getFoodList(letter)
                .applyIoScheduler()
                .subscribe({ response ->
                    view.foodsLoadingState(false)
                    when (response.code()) {
                        in 200..202 -> {
                            response.body()?.let {
                                if (it.meals!!.isNotEmpty()) {
                                    view.loadFoodsList(it)
                                }
                            }
                        }
                        in 400..499 -> {
                            view.serverError("${response.code()}")
                        }
                        in 500..599 -> {
                            view.serverError("${response.code()}")
                        }
                    }
                }, { error ->
                    view.serverError(error.message.toString())
                    view.foodsLoadingState(false)
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callSearchFood(letter: String) {
        if (view.checkInternet()) {
            view.foodsLoadingState(true)
            disposable = repository.getSearchList(letter)
                .applyIoScheduler()
                .subscribe({ response ->
                    view.foodsLoadingState(false)
                    when (response.code()) {
                        in 200..202 -> {
                            response.body()?.let {
                                if (it.meals!!.isNotEmpty()) {
                                    view.loadFoodsList(it)
                                }
                            }
                        }
                        in 400..499 -> {
                            view.serverError("${response.code()}")
                        }
                        in 500..599 -> {
                            view.serverError("${response.code()}")
                        }
                    }
                }, { error ->
                    view.serverError(error.message.toString())
                    view.foodsLoadingState(false)
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callFoodsByCategory(letter: String) {
        if (view.checkInternet()) {
            view.foodsLoadingState(true)
            disposable = repository.getFilterList(letter)
                .applyIoScheduler()
                .subscribe({ response ->
                    view.foodsLoadingState(false)
                    when (response.code()) {
                        in 200..202 -> {
                            response.body()?.let {
                                if (it.meals!!.isNotEmpty()) {
                                    view.loadFoodsList(it)
                                }
                            }
                        }
                        in 400..499 -> {
                            view.serverError("${response.code()}")
                        }
                        in 500..599 -> {
                            view.serverError("${response.code()}")
                        }
                    }
                }, { error ->
                    view.serverError(error.message.toString())
                    view.foodsLoadingState(false)
                })
        } else {
            view.internetError(false)
        }
    }

}