package com.ch2ps075.talenthub.data.preference

data class UserModel(
    val username: String,
    val email: String,
    val token: String,
    val contact: String,
    val address: String,
    val isLogin: Boolean = false,
)
