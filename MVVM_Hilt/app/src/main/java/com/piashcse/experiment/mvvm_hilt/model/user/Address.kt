package com.piashcse.experiment.mvvm_hilt.model.user


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("zipcode")
    val zipcode: String
) : Parcelable