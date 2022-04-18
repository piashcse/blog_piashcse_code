package com.piashcse.experiment.mvvm_hilt.ui.pagin3caching

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class Paging3CachingViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    fun popularPagingMediatorDataSource(): Flow<PagingData<MovieItem>> {
        return repository.getMovieFromMediator().cachedIn(viewModelScope)
    }
}