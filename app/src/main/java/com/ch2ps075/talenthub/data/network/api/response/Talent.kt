package com.ch2ps075.talenthub.data.network.api.response

import com.google.gson.annotations.SerializedName

data class Talent(

    @field:SerializedName("talentId")
    val talentId: Int,

    @field:SerializedName("talentName")
    val talentName: String,

    @field:SerializedName("quantity")
    val quantity: String,

    @field:SerializedName("address")
    val address: String,

    @field:SerializedName("category")
    val category: String,

    @field:SerializedName("contact")
    val contact: String,

    @field:SerializedName("price")
    val price: Long,

    @field:SerializedName("picture")
    val picture: String,

    @field:SerializedName("portfolio")
    val portfolio: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double
)
