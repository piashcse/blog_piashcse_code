package com.piashcse.experiment.mvvm_hilt.ui.detailfragment

import androidx.lifecycle.*
import com.piashcse.experiment.mvvm_hilt.data.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.utils.network.DataState
import com.piashcse.experiment.mvvm_hilt.utils.network.Resource
import com.piashcse.experiment.mvvm_hilt.data.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repo: DataRepository) :
    ViewModel() {
    private val _repositoryResponse: MutableLiveData<DataState<RepositoriesModel>> =
        MutableLiveData()
    val repositoryResponse: LiveData<DataState<RepositoriesModel>>
        get() = _repositoryResponse


    fun githubRepositories(since: String) {
        viewModelScope.launch {
            repo.githubRepositories(since).onEach {
                _repositoryResponse.value = it
            }.launchIn(viewModelScope)
        }
    }

}