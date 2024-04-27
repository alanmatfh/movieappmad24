package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: MovieRepository,
    private val movieId: Long
) : ViewModel() {
    private val _movie = MutableStateFlow(MovieWithImages())
    val movie: StateFlow<MovieWithImages> = _movie.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getByIdWithImages(movieId).distinctUntilChanged().collect {
                movWithImg ->
                // maybe handle errors
                if (movWithImg != null) {
                    _movie.value = movWithImg
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val movie = _movie.value.movie
            val updatedMovie = Movie(
                movie.movieId,
                movie.id,
                movie.title,
                movie.year,
                movie.genre,
                movie.director,
                movie.actors,
                movie.plot,
                movie.trailer,
                movie.rating,
                !movie.isFavorite
            )
            repository.updateMovie(updatedMovie)
        }
    }
}