package com.ch2ps075.talenthub.data.network.api.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("contact")
    val contact: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("error")
    val error: String,

    @field:SerializedName("details")
    val details: String,
)