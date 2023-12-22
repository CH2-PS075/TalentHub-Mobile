package com.ch2ps075.talenthub.data.repo

import androidx.lifecycle.liveData
import com.ch2ps075.talenthub.data.network.api.retrofit.ApiService
import com.ch2ps075.talenthub.state.ResultState
import retrofit2.HttpException

class TalentRepository private constructor(
    private val apiService: ApiService
) {

    fun getTalents() = liveData {
        emit(ResultState.Loading)
        try {
            val request = apiService.getTalents()
            emit(ResultState.Success(request))
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }

    fun getTalentsByCategory(categoryName: String) = liveData {
        emit(ResultState.Loading)
        try {
            val request = apiService.getTalentsByCategory(categoryName)
            emit(ResultState.Success(request))
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }

    fun getTalentsByName(talentName: String) = liveData {
        emit(ResultState.Loading)
        try {
            val request = apiService.getTalentsByName(talentName)
            emit(ResultState.Success(request))
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }

    fun getDetailTalent(talentId: String) = liveData {
        emit(ResultState.Loading)
        try {
            val request = apiService.getDetailTalent(talentId)
            emit(ResultState.Success(request))
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }

    fun getRecommendationTalents(
        latitude: Double,
        longitude: Double,
        price: Int,
        category: String,
        quantity: String,
    ) = liveData {
        emit(ResultState.Loading)
        try {
            val request = apiService.getRecommendationTalents(latitude, longitude, price, category, quantity)
            emit(ResultState.Success(request))
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }

    companion object {
        @Volatile
        private var instance: TalentRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): TalentRepository = instance ?: synchronized(this) {
            instance ?: TalentRepository(apiService)
        }.also { instance = it }
    }
}