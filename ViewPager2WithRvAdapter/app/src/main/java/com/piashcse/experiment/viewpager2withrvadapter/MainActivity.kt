package com.piashcse.experiment.viewpager2withrvadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.piashcse.experiment.viewpager2withrvadapter.adapter.ImageSliderAdapter
import com.piashcse.experiment.viewpager2withrvadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding
    private lateinit var imageSliderAdapter: ImageSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewPager2()
    }

    private fun viewPager2() {
        imageSliderAdapter = ImageSliderAdapter()
        bind.viewPager.adapter = imageSliderAdapter
        TabLayoutMediator(bind.intoTabLayout, bind.viewPager)
        { tab, position ->
        }.attach()
        imageSliderAdapter.addItem(
            listOf(
                "https://cdn.pixabay.com/photo/2021/01/09/02/13/manhattan-5901178_960_720.jpg",
                "https://cdn.pixabay.com/photo/2020/11/22/20/45/venice-5767937_960_720.jpg",
                "https://cdn.pixabay.com/photo/2021/01/10/12/00/road-5904909_640.jpg"
            )
        )
    }
}