package com.piashcse.experiment.mvvm_hilt.ui.parallelapicall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.piashcse.experiment.mvvm_hilt.datasource.model.movie.MovieItem
import com.piashcse.experiment.mvvm_hilt.network.DataState
import com.piashcse.experiment.mvvm_hilt.datasource.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ParallelApiCallViewModel @Inject constructor(private val repo: DataRepository) : ViewModel() {
    private var _popularMovie: MutableLiveData<DataState<List<MovieItem>>> = MutableLiveData()
    val popularMovie: LiveData<DataState<List<MovieItem>>> = _popularMovie

    private var _topRatedMovie: MutableLiveData<DataState<List<MovieItem>>> = MutableLiveData()
    val topRatedMovie: LiveData<DataState<List<MovieItem>>> = _topRatedMovie

    // Get api response at time
    fun popularAndTopRatedMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.popularMovieList(1).zip(repo.topRatedMovieList(1)) { popularMovie, topRatedMovie ->
                _popularMovie.value = popularMovie
                _topRatedMovie.value = topRatedMovie
            }.launchIn(viewModelScope)
        }
    }
}