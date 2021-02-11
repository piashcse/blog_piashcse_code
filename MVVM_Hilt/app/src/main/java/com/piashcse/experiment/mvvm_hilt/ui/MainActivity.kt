package com.piashcse.experiment.mvvm_hilt.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.piashcse.experiment.mvvm_hilt.R
import com.piashcse.experiment.mvvm_hilt.network.Status
import com.piashcse.experiment.mvvm_hilt.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val vm: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vm.getRepositoryList("20").observe(this, Observer {
            when(it.status){
               Status.LOADING->{

                }
                Status.SUCCESS->{

                }
                Status.ERROR->{

                }
            }
        })
    }
}