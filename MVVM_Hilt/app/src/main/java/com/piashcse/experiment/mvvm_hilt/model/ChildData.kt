package com.piashcse.experiment.mvvm_hilt.model

data class ChildData(
    var title: String,
    var price: Double,
    var isStock: Boolean,
    val imageUrl: String
)