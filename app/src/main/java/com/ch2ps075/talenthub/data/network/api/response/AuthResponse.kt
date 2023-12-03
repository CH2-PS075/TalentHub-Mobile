package com.ch2ps075.talenthub.data.network.api.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("loginResult")
    val loginResult: LoginResult,
)

data class LoginResult(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("token")
    val token: String,
)