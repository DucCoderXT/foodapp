package com.hoangduc.foodproject.locallogindata.local

import com.hoangduc.foodproject.db.MealDAO

class MealDataSource(private val mealDao: MealDAO) {

//    suspend fun upsert(meal: Meal) {
//        mealDao.upsert(meal)
//    }
//
//    suspend fun getFavoriteMealsByUserId(userId: Int): List<Meal> {
//        return mealDao.getFavoriteMealsByUserId(userId)
//    }
}