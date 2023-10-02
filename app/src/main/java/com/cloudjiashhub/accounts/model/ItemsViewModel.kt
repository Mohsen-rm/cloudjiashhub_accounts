package com.cloudjiashhub.accounts.model

data class ItemsViewModel(
    val image: Int,
    val title: String,
    val id: String,
    val username: String,
    val email: String,
    val pass: String,
    val return_email:String,
    val phone:String,
    val note:String,
    val details:String,
    val date_create:String,
    val date_update:String,
    val icon_account:String,
    val link_account: String) {
}
