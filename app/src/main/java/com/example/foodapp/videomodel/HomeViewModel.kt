package com.example.foodapp.videomodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.pojo.Meal
import com.example.foodapp.pojo.MealList
import com.example.foodapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {
    private var _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData:LiveData<Meal> = _randomMealLiveData

    fun getRandomMeal() {
//use retrofit callback
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    _randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                //thông báo lỗi, nó cho biết tại sao kết nối k thành công
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }
//    fun observeRandomMealLiveData():LiveData<Meal>{
//        return randomMealLiveData
//    }
}