package com.hoangduc.foodproject.videomodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangduc.foodproject.db.MealDatabase
import com.hoangduc.foodproject.pojo.Category
import com.hoangduc.foodproject.pojo.CategoryList
import com.hoangduc.foodproject.pojo.Meal
import com.hoangduc.foodproject.pojo.MealList
import com.hoangduc.foodproject.pojo.MealsByCategory
import com.hoangduc.foodproject.pojo.MealsByCategoryList
import com.hoangduc.foodproject.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDataBase: MealDatabase
) : ViewModel() {
    private var _randomMealLiveData = MutableLiveData<Meal>()
    val randomMealLiveData: LiveData<Meal> = _randomMealLiveData

    private var _popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    val popularItemsLiveData: LiveData<List<MealsByCategory>> = _popularItemsLiveData

    private var _categoriesLiveData = MutableLiveData<List<Category>>()
    val categoriesLiveData: LiveData<List<Category>> = _categoriesLiveData

    private var _favoritesMealsLiveData = mealDataBase.mealDao().getAllMeals()
    val favoritesMealsLiveData: LiveData<List<Meal>> = _favoritesMealsLiveData

    private var _searchedMealLiveData = MutableLiveData<List<Meal>>()
    val searchedMealLiveData: LiveData<List<Meal>> = _searchedMealLiveData

    private var _bottomSheetMealLiveData = MutableLiveData<Meal>()
    val bottomSheetMealLiveData: LiveData<Meal> = _bottomSheetMealLiveData

    init {
        getRandomMeal()
    }

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
                Log.d("HomeFragment", t.message.toString())
            }

        })
    }

    fun getPopularItems() {
        RetrofitInstance.api.getPopularItems("Seafood")
            .enqueue(object : Callback<MealsByCategoryList> {
                override fun onResponse(
                    call: Call<MealsByCategoryList>,
                    response: Response<MealsByCategoryList>
                ) {
                    if (response.body() != null) {
                        _popularItemsLiveData.value = response.body()!!.meals
                    }
                }

                override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                    Log.d("HomeFragment", t.message.toString())
                }

            })
    }

    //get all categories
    fun getCategories() {
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    _categoriesLiveData.postValue(categoryList.categories)
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeViewModel", t.message.toString())
            }

        })
    }

    fun getMealById(id: String) {
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let { meal ->
                    _bottomSheetMealLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("HomeViewModel",t.message.toString())
            }

        })
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealDataBase.mealDao().delete(meal)
        }
    }

    fun insertMeal(meal: Meal) {
        //khởi chạy

        viewModelScope.launch {
            mealDataBase.mealDao().upsert(meal)
        }
    }

    fun searchMeals(searchMealName: String) =
        RetrofitInstance.api.searchMeals(searchMealName).enqueue(
            object : Callback<MealList> {
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val mealList = response.body()?.meals
                    mealList?.let {
                        _searchedMealLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    Log.d("HomeViewModel", t.message.toString())
                }

            }
        )
}