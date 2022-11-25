package com.ezatpanah.themealdb_api_mvp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.ezatpanah.themealdb_api_mvp.R
import com.ezatpanah.themealdb_api_mvp.adapter.CategoriesAdapter
import com.ezatpanah.themealdb_api_mvp.adapter.FoodsAdapter
import com.ezatpanah.themealdb_api_mvp.databinding.FragmentHomeBinding
import com.ezatpanah.themealdb_api_mvp.response.CategoriesListResponse
import com.ezatpanah.themealdb_api_mvp.response.FoodsListResponse
import com.ezatpanah.themealdb_api_mvp.utils.isNetworkAvailable
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import greyfox.rxnetwork.RxNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContracts.View {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var foodsAdapter: FoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            presenter.callFoodRandom()
            presenter.callCategoriesList()
            presenter.callFoodsList("a")

            //Search Food
            searchEdt.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.toString().length > 1) {
                        presenter.callSearchFood(it.toString())
                    }
                }

            //Filter Food
            filterFood()

            //CheckInternet
            RxNetwork.init(requireContext()).observe()
                .subscribeOn(Schedulers.io())
                .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe { internetError(it.isConnected) }
        }
    }

    private fun filterFood() {
        val filters = listOf('A'..'Z').flatten()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, filters)
        adapter.setDropDownViewResource(R.layout.item_spinner_list)
        binding.filterSpinner.adapter = adapter
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                presenter.callFoodsList(filters[p2].toString())
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    override fun loadFoodRandom(data: FoodsListResponse) {
        binding.apply {
            headerImg.load(data.meals[0].strMealThumb)
        }
    }

    override fun loadCategories(data: CategoriesListResponse) {
        categoriesAdapter.setData(data.categories)
        binding.categoryList.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = categoriesAdapter
        }
        categoriesAdapter.setOnItemClickListener {
            presenter.callFoodsByCategory(it.strCategory.toString())
        }
    }

    override fun loadFoodsList(data: FoodsListResponse) {
        data.meals?.let {
            foodsAdapter.setData(data.meals)
        }
        binding.foodsList.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = foodsAdapter
        }
        foodsAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it.idMeal!!.toInt())
            findNavController().navigate(direction)
        }
    }

    override fun foodsLoadingState(isShown: Boolean) {
        binding.apply {
            if (isShown) {
                homeFoodsLoading.visibility = View.VISIBLE
                foodsList.visibility = View.GONE
            } else {
                homeFoodsLoading.visibility = View.GONE
                foodsList.visibility = View.VISIBLE
            }
        }
    }

    override fun emptyList() {
        binding.apply {
            foodsList.visibility = View.GONE
            homeDisLay.visibility = View.VISIBLE
            disconnectLay.disImg.setImageResource(R.drawable.box)
            disconnectLay.disTxt.text = getString(R.string.emptyList)
        }
    }

    override fun showLoading() {
        binding.homeCategoryLoading.visibility = View.VISIBLE
        binding.categoryList.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.homeCategoryLoading.visibility = View.GONE
        binding.categoryList.visibility = View.VISIBLE
    }

    override fun checkInternet(): Boolean {
        return requireContext().isNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {
        binding.apply {
            if (!hasInternet) {
                homeContent.visibility = View.GONE
                homeDisLay.visibility = View.VISIBLE
                disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                disconnectLay.disTxt.text = getString(R.string.checkInternet)
            } else {
                homeContent.visibility = View.VISIBLE
                homeDisLay.visibility = View.GONE
                presenter.callCategoriesList()
                presenter.callFoodsList("a")
            }
        }

    }

    override fun serverError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}