package com.ezatpanah.themealdb_api_mvp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.ezatpanah.themealdb_api_mvp.R
import com.ezatpanah.themealdb_api_mvp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        binding.apply {

        }
    }
}