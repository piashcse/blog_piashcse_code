package com.piashcse.experiment.mvvm_hilt.constants

object AppConstants {
    const val BASE_URL =  "https://api.github.com/"
    const val URL_REPOSITORIES = "repositories"
    const val URL_SEARCH_REPOSITORIES = "search/repositories"
    object DataTask{
        const val ADDRESS = "address"
        const val DATA = "data"
    }
    object DefaultData{
        const val STRING = ""
    }
    object ViewType{
        const val LIST_VIEW_TYPE  = 0
        const val GRID_VIEW_TYPE = 1
    }
    object Dialog{
       const val DIALOG_TAG = "dialog"
    }
}