package com.piashcse.experiment.mvvm_hilt.model.user


import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("address")
    val address: Address,
    @SerializedName("company")
    val company: Company,
    @SerializedName("email")
    val email: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: String
)