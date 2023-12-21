package com.ch2ps075.talenthub.data.network.api.retrofit

import com.ch2ps075.talenthub.data.network.api.response.AuthResponse
import com.ch2ps075.talenthub.data.network.api.response.BotResponse
import com.ch2ps075.talenthub.data.network.api.response.ReqBody
import com.ch2ps075.talenthub.data.network.api.response.Talent
import retrofit2.Call
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
    @POST("auth/users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): AuthResponse

    @GET("talents")
    suspend fun getTalents(): List<Talent>

    @GET("talents/search")
    suspend fun getTalentsByCategory(
        @Query("category") categoryName: String
    ): List<Talent>

    @GET("talents/search")
    suspend fun getTalentsByName(
        @Query("talentName") talentName: String
    ): List<Talent>

    @GET("talents/{id}")
    suspend fun getDetailTalent(
        @Path("id") id: String
    ): Talent

    @Headers("Content-Type: application/json")
    @POST("users/send-message")
    fun getBotResponse(
        @Body request: ReqBody,
    ): Call<BotResponse>
}