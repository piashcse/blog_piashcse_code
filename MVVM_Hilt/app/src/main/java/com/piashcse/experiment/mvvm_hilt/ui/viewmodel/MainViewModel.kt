package com.piashcse.experiment.mvvm_hilt.ui.viewmodel

import androidx.lifecycle.*
import com.piashcse.experiment.mvvm_hilt.datasource.local.User
import com.piashcse.experiment.mvvm_hilt.datasource.local.UserDao
import com.piashcse.experiment.mvvm_hilt.network.Resource
import com.piashcse.experiment.mvvm_hilt.repository.DataRepository
import com.piashcse.experiment.mvvm_hilt.utils.jsonData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: DataRepository, private val db: UserDao) : ViewModel() {
    var userLiveData =  MutableLiveData<List<User>>()
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


    val getAllUser = db.getAll().asLiveData()



    fun insertUser(user: User) {
        viewModelScope.launch {
            db.insertAll(user)
        }
    }

}