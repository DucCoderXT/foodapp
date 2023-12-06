package com.hoangduc.foodproject.ui.login

data class LoginResult(
    val success: LoggedInUserView? = null,
    val exception: Exception? = null
)