package com.piashcse.experiment.mvvm_hilt.ui.searchwithflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import com.piashcse.experiment.mvvm_hilt.utils.network.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchFlowViewModel @Inject constructor(private val repo: DataRepository) : ViewModel() {

    fun makeFlow(keyword: String) = liveData(Dispatchers.IO) {
        flowOf(keyword).debounce(300)
            .filter {
                it.trim().isEmpty().not()
            }
            .distinctUntilChanged()
            .flatMapLatest {
                repo.getRepositoryListFlow(it)
            }.collect {
                emit(it)
            }
    }

    fun getRepositoryList(since: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val response = repo.getRepositoryList(since)
            if (response.isSuccessful) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(response.errorBody()?.jsonData()))
            }

        } catch (e: Throwable) {
            emit(Resource.error(e))
        }
    }

}