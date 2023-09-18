package com.example.smallworld_tm_db.domain


import com.example.smallworld_tm_db.domain.model.MovieList
import com.example.smallworld_tm_db.domain.model.MovieUiModel

interface IMovieRepository {

    suspend fun getPopularMovies(): MovieList
    suspend fun getMovieInfo(movieID: String): MovieUiModel?
}
