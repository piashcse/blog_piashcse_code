package com.piashcse.experiment.mvvm_hilt.ui.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.piashcse.experiment.mvvm_hilt.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var bind: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        initView()
    }

    private fun initView() {
        bind.result.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}