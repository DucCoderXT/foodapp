package com.hoangduc.foodproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hoangduc.foodproject.locallogindata.model.UserEntity
import com.hoangduc.foodproject.pojo.Meal

//3
@Database(entities = [Meal::class, UserEntity::class], version = 4, exportSchema = false)
@TypeConverters(MealTypeConvertor::class)
abstract class MealDatabase : RoomDatabase(){
    abstract fun mealDao():MealDAO
    abstract fun userDao():UserDao


    // thêm các abtract để có thể trả về các bảng tương ứng
    companion object{
        @Volatile
        var INSTANCE:MealDatabase? = null

        @Synchronized
        fun getInstance(context: Context):MealDatabase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meals.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
        }
    }
}