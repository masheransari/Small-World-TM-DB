package com.example.smallworld_tm_db.ui.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smallworld_tm_db.core.Resource
import com.example.smallworld_tm_db.domain.model.MovieUiModel
import com.example.smallworld_tm_db.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {
    private val _movieState = MutableLiveData<Resource<MovieUiModel>>()
    val movieStateResource: LiveData<Resource<MovieUiModel>> get() = _movieState


    fun fetchMovie(movieID: String) {
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _movieState.postValue(Resource.Loading())
            try {
                val movieInfo = getMoviesUseCase.getMovieInfo(movieID)
                _movieState.postValue(Resource.Success(movieInfo))
            } catch (e: Exception) {
                _movieState.postValue(Resource.Failure(e))
            }
        }
    }
}