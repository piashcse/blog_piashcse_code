package com.piashcse.experiment.mvvm_hilt.ui.networkboundpaging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkBoundViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    private val _movies = MutableStateFlow<DataState<List<MovieItem>>>(DataState.Loading)
    val movies: StateFlow<DataState<List<MovieItem>>> = _movies

    init {
        refresh(false)
    }

    fun refresh(force: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.popularMovie(
                page = 3,
                force = force
            )
        }
    }

}