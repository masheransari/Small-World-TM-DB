package com.example.smallworld_tm_db.domain.usecase


import com.example.smallworld_tm_db.domain.IMovieRepository
import com.example.smallworld_tm_db.domain.model.MovieList
import com.example.smallworld_tm_db.domain.model.MovieUiModel
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: IMovieRepository) {
    suspend operator fun invoke(): MovieList {
        return moviesRepository.getPopularMovies()
    }

    suspend fun getMovieInfo(movieID: String): MovieUiModel {
        return moviesRepository.getMovieInfo(movieID)!!
    }
}