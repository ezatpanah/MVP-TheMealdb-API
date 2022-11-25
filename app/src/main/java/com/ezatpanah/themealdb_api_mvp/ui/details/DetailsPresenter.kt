package com.ezatpanah.themealdb_api_mvp.ui.details

import com.ezatpanah.themealdb_api_mvp.repository.ApiRepository
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenterImpl
import com.ezatpanah.themealdb_api_mvp.utils.applyIoScheduler
import javax.inject.Inject

class DetailsPresenter @Inject constructor
    (private val repository: ApiRepository, val view: DetailsContracts.View)
    : BasePresenterImpl(), DetailsContracts.Presenter {
    override fun callDetailApi(id: Int) {
        if (view.checkInternet()) {
        view.showLoading()
        disposable = repository.getFoodDetails(id)
            .applyIoScheduler()
            .subscribe({ response ->
                view.hideLoading()
                when (response.code()) {
                    in 200..202 -> {
                        response.body()?.let {
                            if (it.meals!!.isNotEmpty()) {
                                view.loadDetail(it)
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

}