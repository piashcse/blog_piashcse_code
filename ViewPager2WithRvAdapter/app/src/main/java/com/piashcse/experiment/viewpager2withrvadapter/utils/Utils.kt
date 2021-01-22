package com.piashcse.experiment.viewpager2withrvadapter.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadSliderImage")
fun loadSliderImage(imageView: ImageView, image: String?) {
    image?.let {
        Glide.with(imageView.context.applicationContext)
            .load(it)
            .centerCrop()
            .into(imageView)
    }
}
