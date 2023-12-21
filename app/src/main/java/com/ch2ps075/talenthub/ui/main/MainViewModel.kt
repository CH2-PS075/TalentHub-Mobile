package com.ch2ps075.talenthub.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.data.repo.AuthRepository
import com.ch2ps075.talenthub.helper.Event
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return authRepository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }

    private val _toastText = MutableLiveData<Event<String>>()
    val toastText: LiveData<Event<String>> = _toastText

    private fun showSnackBar() {
        _toastText.value = Event("")
    }

    init { showSnackBar() }
}