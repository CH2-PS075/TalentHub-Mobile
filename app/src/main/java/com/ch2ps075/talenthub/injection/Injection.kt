package com.ch2ps075.talenthub.injection

import android.content.Context
import com.ch2ps075.talenthub.data.network.api.retrofit.ApiConfig
import com.ch2ps075.talenthub.data.preference.UserPreference
import com.ch2ps075.talenthub.data.preference.dataStore
import com.ch2ps075.talenthub.data.repo.AuthRepository
import com.ch2ps075.talenthub.data.repo.TalentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(apiService, pref)
    }

    fun provideTalentRepository(context: Context): TalentRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService = ApiConfig.getApiService(user.token)
        return TalentRepository.getInstance(apiService)
    }
}