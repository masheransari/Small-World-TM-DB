package com.example.smallworld_tm_db.data

import com.example.smallworld_tm_db.core.InternetCheck
import com.example.smallworld_tm_db.data.local.LocalMovieDataSource
import com.example.smallworld_tm_db.data.remote.RemoteMovieDataSource
import com.example.smallworld_tm_db.domain.IMovieRepository
import com.example.smallworld_tm_db.domain.Mappers.toDomain
import com.example.smallworld_tm_db.domain.Mappers.toEntity
import com.example.smallworld_tm_db.domain.model.MovieList
import com.example.smallworld_tm_db.domain.model.MovieUiModel

class IMovieRepositoryImpl constructor(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource,
) : IMovieRepository {


    override suspend fun getPopularMovies(): MovieList {
        if (InternetCheck.isNetworkAvailable()) {
            val remoteData = getCharactersRemote()
            dataSourceLocal.insertMovies(remoteData.map { it.toEntity("popular") })
            return MovieList(remoteData)
        } else {
            val cacheData = getCharactersCache()
            return MovieList(cacheData)
        }

    }

    override suspend fun getMovieInfo(movieID: String): MovieUiModel? {
        return try {
            val data = dataSourceRemote.getMoviesInfo(movieID)
            data.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    private suspend fun getCharactersRemote(): List<MovieUiModel> {
        return try {
            val characters = dataSourceRemote.getPopularMovies()
            characters.results.mapNotNull { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getCharactersCache(): List<MovieUiModel> {
        return try {
            val characters = dataSourceLocal.getPopularMovies()
            characters.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}