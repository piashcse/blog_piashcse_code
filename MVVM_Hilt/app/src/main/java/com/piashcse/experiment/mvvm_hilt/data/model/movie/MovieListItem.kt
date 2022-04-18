package com.piashcse.experiment.mvvm_hilt.data.model.movie

sealed class MovieListItem {
    data class MovieItemFromList(val movie: MovieItem) : MovieListItem()
    data class SeparatorItem(val letter: String) : MovieListItem()
}