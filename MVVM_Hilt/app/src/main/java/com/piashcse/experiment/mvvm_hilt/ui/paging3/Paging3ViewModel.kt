package com.piashcse.experiment.mvvm_hilt.ui.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.piashcse.experiment.mvvm_hilt.datasource.remote.paging.PopularPagingDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Paging3ViewModel @Inject constructor(private val repoPaging: PopularPagingDataSource) :
    ViewModel() {
    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 2)
    ) {
        repoPaging
    }.flow.cachedIn(viewModelScope)
}