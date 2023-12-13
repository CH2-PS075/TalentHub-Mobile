package com.ch2ps075.talenthub.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ch2ps075.talenthub.data.network.api.response.AuthResponse
import com.ch2ps075.talenthub.data.network.api.retrofit.ApiService
import com.ch2ps075.talenthub.data.preference.UserModel
import com.ch2ps075.talenthub.data.preference.UserPreference
import com.ch2ps075.talenthub.state.ResultState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AuthRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
) {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun register(
        username: String,
        fullName: String,
        address: String,
        contact: String,
        email: String,
        password: String,
    ): LiveData<ResultState<Any>> {
        return liveData {
            emit(ResultState.Loading)
            try {
                val successResponse = apiService.register(username, fullName, address, contact, email, password).message
                emit(ResultState.Success(successResponse))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, AuthResponse::class.java)
                errorBody.message.let { ResultState.Error(it) }.let { emit(it) }
            }
        }
    }

    fun login(
        email: String,
        password: String,
    ) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.login(email, password)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, AuthResponse::class.java)
            errorBody.message.let { ResultState.Error(it) }.let { emit(it) }
        }
    }


    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
        ): AuthRepository = instance ?: synchronized(this) {
            instance ?: AuthRepository(apiService, userPreference)
        }.also { instance = it }
    }
}