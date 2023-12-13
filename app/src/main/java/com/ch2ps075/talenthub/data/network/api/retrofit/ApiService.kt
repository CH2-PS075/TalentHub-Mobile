package com.ch2ps075.talenthub.data.network.api.retrofit

import com.ch2ps075.talenthub.data.network.api.response.AuthResponse
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("users")
    suspend fun register(
        @Field("username") name: String,
        @Field("fullName") fullName: String,
        @Field("address") address: String,
        @Field("contact") contact: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @FormUrlEncoded
    @POST("auth/user/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse
}