package com.hoangduc.foodproject.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api:MealApi by lazy {
        Retrofit.Builder().
                baseUrl("https://www.themealdb.com/api/json/v1/1/")
                    //lấy tập tin json và chuyển đổi thành đối tượng kotlin
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MealApi::class.java)
    }


}