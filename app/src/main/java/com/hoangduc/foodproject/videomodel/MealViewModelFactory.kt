package com.hoangduc.foodproject.videomodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hoangduc.foodproject.db.MealDatabase

class MealViewModelFactory(val mealDatabase: MealDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealViewModel(mealDatabase) as T
    }
}