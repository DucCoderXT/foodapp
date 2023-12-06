package com.hoangduc.foodproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoangduc.foodproject.pojo.Meal

//2
@Dao
interface MealDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)//nếu meal đã tồn tại sẽ update it instead of insert
    suspend fun upsert(meal: Meal) //insert and update

    @Delete
    suspend fun delete(meal: Meal)


    @Query("SELECT * FROM mealInformation")
    fun getAllMeals(): LiveData<List<Meal>>

    //    @Query(" SELECT * FROM mealInformation WHERE userId = :userId ")
//    fun getFavoriteMealsByUserId(userId: Int): LiveData<List<Meal>>
//    @Query("SELECT * FROM mealInformation WHERE userId = :userId")
//    suspend fun getFavoriteMealsByUserId(userId: Int): List<Meal>


}