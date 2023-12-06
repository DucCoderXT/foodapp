package com.hoangduc.foodproject.videomodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hoangduc.foodproject.R
import com.hoangduc.foodproject.locallogindata.Result
import com.hoangduc.foodproject.repository.LoginRepository
import com.hoangduc.foodproject.ui.login.LoggedInUserView
import com.hoangduc.foodproject.ui.login.LoginFormState
import com.hoangduc.foodproject.ui.login.LoginResult
import com.hoangduc.foodproject.ui.login.RegisteredResult
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _registeredResult = MutableLiveData<RegisteredResult>()
    val registeredResult: LiveData<RegisteredResult> = _registeredResult


    fun login(username: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(username, password)

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
            } else if (result is Result.Error) {
                _loginResult.value = LoginResult(exception = result.exception)
            }
        }
    }

    fun signup(email: String, password: String, displayName: String) {
        viewModelScope.launch {
            val result = loginRepository.register(email, password, displayName)
            if (result is Result.Success) {
                _registeredResult.postValue(result.data)
            } else if (result is Result.Error) {
                _registeredResult.postValue(RegisteredResult(exception = result.exception))
            }
        }
    }

    fun loginDataChanged(username: String, password: String, displayName: String? = null) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else if (displayName != null && !isDisplayNameValid(displayName)) {
            _loginForm.value = LoginFormState(displayNameError = R.string.invalid_display_name)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isDisplayNameValid(displayName: String?): Boolean {
        if (displayName == null) {
            return false
        }
        return displayName.trim().isNotEmpty()
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            false
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}