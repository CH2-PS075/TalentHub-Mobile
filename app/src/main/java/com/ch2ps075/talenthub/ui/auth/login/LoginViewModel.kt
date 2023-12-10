package com.ch2ps075.talenthub.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.data.repo.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun login(email: String, password: String) = authRepository.login(email, password)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            authRepository.saveSession(user)
        }
    }
}