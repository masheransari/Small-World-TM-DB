package com.example.smallworld_tm_db.data.remote

import com.example.smallworld_tm_db.application.AppConstants
import com.example.smallworld_tm_db.data.remote.model.MovieListResponse
import com.example.smallworld_tm_db.data.remote.model.MovieResponse
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovies(): MovieListResponse {
        return apiService.getPopulardMovies(AppConstants.API_KEY)
    }

    suspend fun getMoviesInfo(movieID: String): MovieResponse {
        return apiService.getMoviesInfo(movieID, AppConstants.API_KEY)
    }
}