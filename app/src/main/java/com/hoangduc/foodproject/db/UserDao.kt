package com.hoangduc.foodproject.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hoangduc.foodproject.locallogindata.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE email = :email AND password = :password")
    fun findByEmailPassword(email: String, password: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    fun findByEmail(email: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun findById(userId: Long): UserEntity?
}
