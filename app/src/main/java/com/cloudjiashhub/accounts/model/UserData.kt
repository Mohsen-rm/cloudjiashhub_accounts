package com.cloudjiashhub.accounts.model

data class UserData(
    val code: Int,
    val id: String,
    val fullname: String,
    val username: String,
    val email: String,
    val msg: String,
    val data: Int,
    val user_key: String
)
