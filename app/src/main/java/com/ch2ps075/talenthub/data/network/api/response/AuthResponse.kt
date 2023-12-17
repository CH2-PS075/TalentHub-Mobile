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

    @field:SerializedName("talentName")
    val talentName: String,

    @field:SerializedName("quantity")
    val quantity: String,

    @field:SerializedName("quantity")
    val price: String,

    @field:SerializedName("identityCard")
    val identityCard: String,

    @field:SerializedName("isVerified")
    val isVerified: Boolean,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double,
)
