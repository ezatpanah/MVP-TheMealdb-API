package com.ezatpanah.themealdb_api_mvp.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.ezatpanah.themealdb_api_mvp.R
import com.ezatpanah.themealdb_api_mvp.databinding.FragmentDetailsBinding
import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import com.ezatpanah.themealdb_api_mvp.response.FoodsListResponse
import com.ezatpanah.themealdb_api_mvp.ui.details.player.PlayerActivity
import com.ezatpanah.themealdb_api_mvp.utils.Constant.VIDEO_ID
import com.ezatpanah.themealdb_api_mvp.utils.isNetworkAvailable
import com.ezatpanah.themealdb_api_mvp.utils.showSnackBar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import greyfox.rxnetwork.RxNetwork
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() ,DetailsContracts.View{

    private lateinit var binding: FragmentDetailsBinding


    @Inject
    lateinit var presenter: DetailsPresenter

    @Inject
    lateinit var entity: FoodEntity

    //Other
    private val args: DetailsFragmentArgs by navArgs()
    private var foodId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Get food id
        foodId = args.foodId
        if (foodId > 0) {
            //Call api
            presenter.callDetailApi(foodId)
        }
        //Check internet
        RxNetwork.init(requireContext()).observe()
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                internetError(it.isConnected)
            }
        //Back
        binding.detailBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun loadDetail(data: FoodsListResponse) {
        //InitViews
        binding.apply {
            //Set Data
            data.meals?.get(0)?.let { itMeal ->
                //Favorite
                entity.id = itMeal.idMeal.toString().toInt()
                entity.title = itMeal.strMeal.toString()
                entity.img = itMeal.strMealThumb.toString()
                presenter.checkFavorite(itMeal.idMeal.toString().toInt())
                //Get data
                foodCoverImg.load(itMeal.strMealThumb) {
                    crossfade(true)
                    crossfade(500)
                }
                foodCategoryTxt.text = itMeal.strCategory
                foodAreaTxt.text = itMeal.strArea
                foodTitleTxt.text = itMeal.strMeal
                foodDescTxt.text = itMeal.strInstructions
                //Play
                if (itMeal.strYoutube != null) {
                    foodPlayImg.visibility = View.VISIBLE
                    foodPlayImg.setOnClickListener {
                        val videoId = itMeal.strYoutube.split("=")[1]
                        Intent(requireContext(), PlayerActivity::class.java).also {
                            it.putExtra(VIDEO_ID, videoId)
                            startActivity(it)
                        }
                    }
                } else {
                    foodPlayImg.visibility = View.GONE
                }
                //Source
                if (itMeal.strSource != null) {
                    foodSourceImg.visibility = View.VISIBLE
                    foodSourceImg.setOnClickListener {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(itMeal.strSource)))
                    }
                } else {
                    foodSourceImg.visibility = View.GONE
                }
            }
            //Json Array
            val jsonData = JSONObject(Gson().toJson(data))
            val meals = jsonData.getJSONArray("meals")
            val meal = meals.getJSONObject(0)
            //Ingredient
            for (i in 1..15) {
                val ingredient = meal.getString("strIngredient$i")
                if (ingredient.isNullOrEmpty().not()) {
                    ingredientsTxt.append("$ingredient\n")
                }
            }
            //Measure
            for (i in 1..15) {
                val measure = meal.getString("strMeasure$i")
                if (measure.isNullOrEmpty().not()) {
                    measureTxt.append("$measure\n")
                }
            }
        }
    }

    override fun updateFavorite(isAdded: Boolean) {
        binding.apply {
            //Click
            detailFav.setOnClickListener {
                if (isAdded) {
                    presenter.deleteFood(entity)
                } else {
                    presenter.saveFood(entity)
                }
            }
            //Change color
            if (isAdded) {
                detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.tartOrange))
            } else {
                detailFav.setColorFilter(ContextCompat.getColor(requireContext(), R.color.black))
            }
        }
    }

    override fun showLoading() {
        binding.detailLoading.visibility = View.VISIBLE
        binding.detailContentLay.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.detailLoading.visibility = View.GONE
        binding.detailContentLay.visibility = View.VISIBLE
    }

    override fun checkInternet(): Boolean {
        return requireContext().isNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {
        binding.apply {
            if (!hasInternet) {
                detailContentLay.visibility = View.GONE
                homeDisLay.visibility = View.VISIBLE
                //Change view
                disconnectLay.imgDisconnect.setAnimation(R.raw.nointernet)
                disconnectLay.imgDisconnect.playAnimation()
            } else {
                detailContentLay.visibility = View.VISIBLE
                homeDisLay.visibility = View.GONE
            }
        }
    }

    override fun serverError(message: String) {
        binding.root.showSnackBar(message)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onStop()
    }
}