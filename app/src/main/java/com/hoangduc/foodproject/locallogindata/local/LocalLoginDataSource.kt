package com.hoangduc.foodproject.locallogindata.local

import com.hoangduc.foodproject.db.UserDao
import com.hoangduc.foodproject.locallogindata.LoginDataSource
import com.hoangduc.foodproject.locallogindata.Result
import com.hoangduc.foodproject.locallogindata.model.UserEntity
import com.hoangduc.foodproject.ui.login.LoggedInUserView
import com.hoangduc.foodproject.ui.login.RegisteredResult
import java.io.IOException

class LocalLoginDataSource(private val userDao: UserDao) : LoginDataSource {
    override suspend fun register(
        email: String,
        password: String,
        displayName: String
    ):Result<RegisteredResult> {
        val user = UserEntity(email = email, password = password, displayName = displayName)

        val existedUser = userDao.findByEmail(email)

        return if (existedUser != null) {
            Result.Error(Exception("This user already exists"))
        } else {
            val userId = userDao.insert(user)
            val newUser = userDao.findById(userId) // Lấy thông tin đầy đủ của người dùng mới được thêm
            Result.Success(
                data = RegisteredResult(
                    success  = newUser?.let { LoggedInUserView(it.displayName) },
                    exception = null
                )
            )
        }
    }

    override fun login(username: String, password: String): Result<UserEntity> {
        return try {
            val loggedInUser = userDao.findByEmailPassword(username, password)
            if (loggedInUser != null) {
                Result.Success(loggedInUser)
            } else {
                Result.Error(Exception("Incorrect username or password."))
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error logging in", e))
        }
    }
}