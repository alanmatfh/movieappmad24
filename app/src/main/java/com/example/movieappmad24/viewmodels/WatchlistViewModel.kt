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

class WatchlistViewModel(
    private val repository: MovieRepository
) : ViewModel() {
    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().distinctUntilChanged().collect{
                favoriteMovies -> _movies.value = favoriteMovies
            }
        }
    }

    fun toggleFavorite(movieId: Long) {
        viewModelScope.launch {
            val movieList = _movies.value
            val foundMovie = movieList.find { it.movie.movieId == movieId } ?: return@launch
            val movie = foundMovie.movie
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