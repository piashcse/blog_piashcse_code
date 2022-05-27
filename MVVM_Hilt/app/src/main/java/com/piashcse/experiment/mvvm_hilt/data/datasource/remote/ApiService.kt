package com.piashcse.experiment.mvvm_hilt.data.datasource.remote

import com.piashcse.experiment.mvvm_hilt.data.model.RepoSearchResponse
import com.piashcse.experiment.mvvm_hilt.data.model.RepositoriesModel
import com.piashcse.experiment.mvvm_hilt.data.model.movie.BaseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(ApiUrls.URL_REPOSITORIES)
    suspend fun getGitHubRepositories(
        @Query("since") since: String
    ): Response<RepositoriesModel>

    @GET(ApiUrls.URL_REPOSITORIES)
    suspend fun githubRepositories(
        @Query("since") since: String
    ): RepositoriesModel

    @GET(ApiUrls.URL_SEARCH_REPOSITORIES)
    suspend fun searchRepos(@Query("q") query: String): Response<RepoSearchResponse>

    @GET(ApiUrls.MOVIE_LIST)
    suspend fun nowPlayingMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiUrls.POPULAR_MOVIE_LIST)
    suspend fun popularMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiUrls.TOP_RATED_MOVIE_LIST)
    suspend fun topRatedMovieList(@Query("page") page: Int): BaseModel

    @GET(ApiUrls.SEARCH_MOVIE)
    suspend fun search(@Query("query") searchKey: String): BaseModel

}