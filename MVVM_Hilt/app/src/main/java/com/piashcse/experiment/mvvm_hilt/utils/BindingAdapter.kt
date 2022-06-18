package com.piashcse.experiment.mvvm_hilt.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun ImageView.bindImage(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}

@BindingAdapter("imageUrl")
fun imageUrl(imag : AppCompatImageView, url: String) {
    Glide.with(imag.context)
        .load(url)
        .into(imag)
}