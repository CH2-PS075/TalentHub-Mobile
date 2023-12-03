package com.ch2ps075.talenthub.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ch2ps075.talenthub.data.repo.AuthRepository
import com.ch2ps075.talenthub.state.ResultState

class RegisterViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun register(
        username: String,
        email: String,
        password: String,
    ): LiveData<ResultState<Any>> {
        return authRepository.register(username, email, password)
    }
}