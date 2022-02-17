package com.piashcse.experiment.mvvm_hilt.data.model

data class ChildData(
    var title: String,
    var price: Double,
    var isStock: Boolean,
    val imageUrl: String
)