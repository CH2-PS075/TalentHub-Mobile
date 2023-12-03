package com.ch2ps075.talenthub.data.preference

data class UserModel(
    val username: String,
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
)
