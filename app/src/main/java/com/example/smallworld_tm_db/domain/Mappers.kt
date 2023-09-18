package com.example.smallworld_tm_db.domain

import com.example.smallworld_tm_db.data.local.MovieEntity
import com.example.smallworld_tm_db.data.remote.model.MovieResponse
import com.example.smallworld_tm_db.domain.model.MovieList
import com.example.smallworld_tm_db.domain.model.MovieUiModel

object Mappers {


    fun List<MovieResponse>.toMovieList(): MovieList {
        val resultList = mutableListOf<MovieUiModel>()
        map { it.toDomain() }
        return MovieList(resultList)
    }

    fun List<MovieEntity>.toMovieListUi(): MovieList {
        val resultList = mutableListOf<MovieUiModel>()
        map { it.toDomain() }
        return MovieList(resultList)
    }

    fun MovieResponse.toDomain(): MovieUiModel? {
        return MovieUiModel(
            id = this.id,
            originalTitle = this.original_title,
            originalLanguage = this.original_language,
            overview = this.overview,
            popularity = this.popularity,
            posterPath = this.poster_path,
            releaseDate = this.release_date,
            title = this.title,
            movieType = this.movie_type,
            backdropImageUrl = this.backdrop_path,
            voteAverage = this.vote_average,
            voteCount = this.vote_count
        )
    }

    fun MovieUiModel.toEntity(movieType: String): MovieEntity {
        return MovieEntity(
            id = this.id,
            original_title = this.originalTitle,
            original_language = this.originalLanguage,
            overview = this.overview,
            popularity = this.popularity,
            poster_path = this.posterPath,
            release_date = this.releaseDate,
            title = this.title,
            backdrop_path = this.backdropImageUrl ?: "",
            vote_average = this.voteAverage,
            vote_count = this.voteCount,
            movie_type = movieType
        )
    }

    fun MovieEntity.toDomain(): MovieUiModel {
        return MovieUiModel(
            originalTitle = this.original_title,
            originalLanguage = this.original_language,
            overview = this.overview,
            popularity = this.popularity,
            posterPath = this.poster_path,
            releaseDate = this.release_date,
            title = this.title,
            movieType = this.movie_type,
            backdropImageUrl = this.backdrop_path,
            voteAverage = this.vote_average,
            voteCount = this.vote_count
        )
    }


    fun MovieResponse.isNull(): Boolean {
        return this.backdrop_path == null
    }

}