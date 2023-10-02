package com.cloudjiashhub.accounts.model

import com.google.gson.Gson
val gson = Gson()

// Define a data class that matches the JSON structure
data class AccountData(
    val id: String,
    val title: String,
    val details: String,
    val id_account: String,
    val username: String,
    val email: String,
    val pass: String,
    val return_email: String,
    val phone: String,
    val info: String,
    val date_create: String,
    val date_update: String,
    val icon_account: String,
    val url_account: String
)