package com.hoangduc.foodproject.locallogindata

import com.hoangduc.foodproject.locallogindata.model.UserEntity
import com.hoangduc.foodproject.ui.login.RegisteredResult


interface LoginDataSource {
    fun login(username: String, password: String): Result<UserEntity>
    suspend fun register(email: String, password: String, displayName: String): Result<RegisteredResult>
}
