package com.ezatpanah.themealdb_api_mvp.ui.favorites

import com.ezatpanah.themealdb_api_mvp.repository.DbRepository
import com.ezatpanah.themealdb_api_mvp.ui.base.BasePresenterImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FavoritesPresenter
@Inject constructor(
    private val repository: DbRepository,
    val view: FavoritesContracts.View
) : FavoritesContracts.Presenter, BasePresenterImpl() {

    override fun getAllFood() {
        disposable = repository
            .loadAllFoods()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it.isNotEmpty()) {
                    view.loadAllFoods(it)
                } else {
                    //Empty
                }
            }
    }
}