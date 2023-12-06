package com.hoangduc.foodproject.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
//4
@TypeConverters
class MealTypeConvertor {
    //room use this funtion when it wants to insert inside the table
    @TypeConverter
    fun fromAnyToString(attribute: Any?): String {
        if (attribute == null)
            return ""
        return attribute as String
    }
    //room use this function with it'll retrieve data from database
    @TypeConverter
    fun fromStringToAny(attribute: String?): Any {
        if (attribute == null)
            return ""
        return attribute as Any
    }
}