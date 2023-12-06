package com.hoangduc.foodproject.retrofit

import com.hoangduc.foodproject.pojo.CategoryList
import com.hoangduc.foodproject.pojo.MealList
import com.hoangduc.foodproject.pojo.MealsByCategoryList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    //get meal img
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    //get meal information
    @GET("lookup.php")
    fun getMealDetail(@Query("i") id: String): Call<MealList>

    //get list most popular meal
    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName: String): Call<MealsByCategoryList>

    //get categoryMeals
    @GET("categories.php")
    fun getCategories(): Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String): Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchMealName: String): Call<MealList>


}