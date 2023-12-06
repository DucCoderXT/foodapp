package com.hoangduc.foodproject.videomodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hoangduc.foodproject.db.MealDatabase

class HomeViewModelFactory(val mealDatabase: MealDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mealDatabase) as T
    }
}