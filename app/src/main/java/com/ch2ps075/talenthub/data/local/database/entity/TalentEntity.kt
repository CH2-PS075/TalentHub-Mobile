package com.ch2ps075.talenthub.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "talent")
data class TalentEntity(

    @PrimaryKey
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

    @field:SerializedName("latitude")
    val latitude: Double,

    @field:SerializedName("longitude")
    val longitude: Double
)
