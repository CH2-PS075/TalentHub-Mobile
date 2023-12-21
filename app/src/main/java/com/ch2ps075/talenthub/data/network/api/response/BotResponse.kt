package com.ch2ps075.talenthub.data.network.api.response

import com.google.gson.annotations.SerializedName

data class BotResponse(
	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("status")
	val status: Status,
)

data class Status(

	@field:SerializedName("code")
	val code: Int,

	@field:SerializedName("message")
	val message: String,
)

data class Data(

	@field:SerializedName("response")
	val response: String,

	@field:SerializedName("class")
	val jsonMemberClass: String,
)

data class ReqBody(
	@SerializedName("text")
	val text: String,
)
