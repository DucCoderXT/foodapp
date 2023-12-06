package com.hoangduc.foodproject.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hoangduc.foodproject.R
import com.hoangduc.foodproject.databinding.ActivityHomeBinding
import com.hoangduc.foodproject.db.MealDatabase
import com.hoangduc.foodproject.videomodel.HomeViewModel
import com.hoangduc.foodproject.videomodel.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this, R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        val displayName = intent.getStringExtra(EXTRA_KEY_DISPLAY_NAME)
        binding.tvDisplayName.text = getString(R.string.txt_welcome, displayName)

    }

    companion object {
        const val EXTRA_KEY_DISPLAY_NAME = "com.hoangduc.foodproject.EXTRA_KEY_DISPLAY_NAME"
    }


}