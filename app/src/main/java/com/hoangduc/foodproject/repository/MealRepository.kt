package com.hoangduc.foodproject.repository

import com.hoangduc.foodproject.locallogindata.local.MealDataSource

class MealRepository(private val mealDataSource: MealDataSource) {

//    private val _mealDetailsLiveData = MutableLiveData<Meal>()
//    val mealDetailsLiveData: LiveData<Meal> = _mealDetailsLiveData
//    suspend fun saveMealToFavorite(meal: Meal, userId: Int) {
//        meal.userId = userId
//        mealDataSource.upsert(meal)
//    }
//
//    suspend fun getFavoriteMealsByUserId(userId: Int): List<Meal> {
//        return mealDataSource.getFavoriteMealsByUserId(userId)
//    }
//    fun getMealDetail(id: String) {
//        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList> {
//            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
//                if (response.body() != null) {
//                    _mealDetailsLiveData.value = response.body()!!.meals[0]
//                } else {
//                    return
//                }
//            }
//
//            override fun onFailure(call: Call<MealList>, t: Throwable) {
//                Log.d("MealActivity", t.message.toString())
//            }
//
//        })
//    }
}