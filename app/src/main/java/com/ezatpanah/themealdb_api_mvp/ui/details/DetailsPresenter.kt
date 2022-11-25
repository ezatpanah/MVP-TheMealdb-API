package com.ezatpanah.themealdb_api_mvp.ui.details

import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import com.ezatpanah.themealdb_api_mvp.repository.ApiRepository
import com.ezatpanah.themealdb_api_mvp.repository.DbRepository
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenterImpl
import com.ezatpanah.themealdb_api_mvp.utils.applyIoScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DetailsPresenter @Inject constructor
    (private val repository: ApiRepository,
     private val dbRepository: DbRepository,
     val view: DetailsContracts.View)
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


    override fun saveFood(entity: FoodEntity) {
        disposable = dbRepository.saveFood(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateFavorite(true)
            }
    }

    override fun deleteFood(entity: FoodEntity) {
        disposable = dbRepository.deleteFood(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateFavorite(false)
            }
    }

    override fun checkFavorite(id: Int) {
        disposable = dbRepository.existsFood(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view.updateFavorite(it)
            }
    }

}