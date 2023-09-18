package com.example.smallworld_tm_db.data.remote

import com.example.smallworld_tm_db.data.remote.model.MovieListResponse
import com.example.smallworld_tm_db.data.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query("api_key") apiKey: String): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMoviesInfo(
        @Path("movie_id") movieID: String,
        @Query("api_key") apiKey: String
    ): MovieResponse
}