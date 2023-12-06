package com.hoangduc.foodproject.videomodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hoangduc.foodproject.R
import com.hoangduc.foodproject.repository.LoginRepository

class MainActivityViewModel(private val repository: LoginRepository) : ViewModel() {
    private val _logoutState = MutableLiveData<Int>()
    val logoutState: LiveData<Int> = _logoutState

    private val _loggedInState = MutableLiveData<Boolean>()
    val loggedInState: LiveData<Boolean> = _loggedInState

    init {
        refreshLoggedInState()
    }

    fun logout() {
        repository.logout()
        _logoutState.postValue(R.string.txt_prompt)
        refreshLoggedInState()
    }

    fun refreshLoggedInState() {
        _loggedInState.postValue(LoginRepository.isLoggedIn)
    }
}