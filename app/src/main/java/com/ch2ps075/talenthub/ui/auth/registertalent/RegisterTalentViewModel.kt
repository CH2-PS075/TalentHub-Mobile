package com.ch2ps075.talenthub.ui.auth.registertalent

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ch2ps075.talenthub.data.repo.AuthRepository
import com.ch2ps075.talenthub.state.ResultState

class RegisterTalentViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun register(
        talentName: String,
        quantity: String,
        address: String,
        contact: String,
        price: String,
        identityCard: String,
        email: String,
        password: String,
    ): LiveData<ResultState<Any>> {
        return authRepository.registertalent(talentName, quantity, address, contact, price, identityCard, email, password)
    }
}