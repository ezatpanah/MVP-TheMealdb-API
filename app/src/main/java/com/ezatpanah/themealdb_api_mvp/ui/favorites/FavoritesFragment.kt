package com.ezatpanah.themealdb_api_mvp.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezatpanah.themealdb_api_mvp.R
import com.ezatpanah.themealdb_api_mvp.adapter.FavoriteAdapter
import com.ezatpanah.themealdb_api_mvp.databinding.FragmentFavoritesBinding
import com.ezatpanah.themealdb_api_mvp.db.FoodEntity
import com.ezatpanah.themealdb_api_mvp.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() ,FavoritesContracts.View {
    //Binding
    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var presenter: FavoritesPresenter

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Load data from database
        presenter.callFoodList()
    }

    override fun loadFoodsList (list: MutableList<FoodEntity>) {
        favoriteAdapter.setData(list)

        binding.favoriteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }

        favoriteAdapter.setOnItemClickListener {
            val direction = HomeFragmentDirections.actionHomeToDetail(it.id)
            findNavController().navigate(direction)
        }
    }

    override fun emptyList() {
        binding.apply {
            favoriteList.visibility = View.GONE
            favEmptyLay.visibility = View.VISIBLE
            emptyLay.imgDisconnect.setAnimation(R.raw.empty)
            emptyLay.imgDisconnect.playAnimation()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}