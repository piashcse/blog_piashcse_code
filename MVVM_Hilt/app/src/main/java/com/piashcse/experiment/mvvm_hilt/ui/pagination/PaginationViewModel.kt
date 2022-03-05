package com.piashcse.experiment.mvvm_hilt.ui.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.experiment.mvvm_hilt.data.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaginationViewModel @Inject constructor(private val repository: DataRepository) :
    ViewModel() {
    private val _popularMovieResponse: MutableLiveData<DataState<List<MovieItem>>> =
        MutableLiveData()
    val popularMovieResponse: LiveData<DataState<List<MovieItem>>>
        get() = _popularMovieResponse


    fun popularMovie(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.popularMovieList(page).onEach {
                _popularMovieResponse.value = it
            }.launchIn(viewModelScope)
        }
    }
}
