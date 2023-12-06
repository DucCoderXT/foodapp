package com.hoangduc.foodproject.repository

import com.hoangduc.foodproject.locallogindata.LoginDataSource
import com.hoangduc.foodproject.locallogindata.Result
import com.hoangduc.foodproject.locallogindata.model.UserEntity
import com.hoangduc.foodproject.ui.login.RegisteredResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//dùng để đẩy LogindataSource lên các tầng bên trên
class LoginRepository(val dataSource: LoginDataSource) {
    init {
        user = null
    }

    fun logout() {
        user = null
        //dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<UserEntity> {
        return withContext(Dispatchers.IO) {
            val result = dataSource.login(username, password)
            if (result is Result.Success) {
                setLoggedInUser(result.data)
            }
            result
        }
    }

    suspend fun register(
        email: String,
        password: String,
        displayName: String
    ): Result<RegisteredResult> {
        return withContext(Dispatchers.IO) {
            dataSource.register(email, password, displayName)
        }
    }

    private fun setLoggedInUser(userEntity: UserEntity) {
        user = userEntity
    }

    companion object {
        var user: UserEntity? = null
            private set
        val isLoggedIn: Boolean
            get() = user != null
    }
}