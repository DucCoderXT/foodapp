package com.hoangduc.foodproject.ui.login

data class LoginFormState (
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val displayNameError: Int? = null,
    val isDataValid: Boolean = false
)

