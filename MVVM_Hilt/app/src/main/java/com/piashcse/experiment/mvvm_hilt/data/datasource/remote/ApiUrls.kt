package com.piashcse.experiment.mvvm_hilt.data.datasource.remote

object ApiUrls {
    // const val BASE_URL =  "https://api.github.com/"
    const val URL_REPOSITORIES = "repositories"
    const val URL_SEARCH_REPOSITORIES = "search/repositories"

    // movie db
    private const val API_KEY = "59cd6896d8432f9c69aed9b86b9c2931"
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w342"
    const val MOVIE_LIST = "movie/now_playing?api_key=$API_KEY&language=en-US"
    const val POPULAR_MOVIE_LIST = "movie/popular?api_key=$API_KEY&language=en-US"
    const val TOP_RATED_MOVIE_LIST = "movie/top_rated?api_key=$API_KEY&language=en-US"
}