package com.piashcse.experiment.mvvm_hilt.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.piashcse.experiment.mvvm_hilt.utils.AppConstants
import com.piashcse.experiment.mvvm_hilt.databinding.ActivityDetailBinding
import com.piashcse.experiment.mvvm_hilt.data.model.user.Address
import com.piashcse.experiment.mvvm_hilt.utils.finishActivityResult
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val data = intent.getParcelableExtra<Address>(AppConstants.DataTask.ADDRESS)
        Timber.e("address from home : $data")
        binding.result.setOnClickListener {
            finishActivityResult("data" to "result")
        }
    }
}