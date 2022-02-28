package com.piashcse.experiment.mvvm_hilt.ui.paging3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.piashcse.experiment.mvvm_hilt.data.datasource.remote.paging.PopularPagingDataSource
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class Paging3ViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    fun popularPagingDataSource(): Flow<PagingData<MovieItem>> {
        return repository.popularPagingDataSource().cachedIn(viewModelScope)
    }
}