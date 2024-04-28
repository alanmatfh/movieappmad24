package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao,
                      private val movieImageDao: MovieImageDao) {

    suspend fun addMovie(movie: Movie) = movieDao.add(movie)

    suspend fun addMovieWithImages(movieWithImages: MovieWithImages) {
        val movieId = movieDao.add(movieWithImages.movie)
        val images = movieWithImages.movieImages
        images.forEach { img ->
            img.movieId = movieId
        }
        movieImageDao.addAll(images)
    }

    suspend fun updateMovie(movie: Movie) = movieDao.update(movie)

    suspend fun deleteMovie(movie: Movie) = movieDao.delete(movie)

    fun getAllMovies(): Flow<List<MovieWithImages>> = movieDao.getAll()

    fun getFavoriteMovies(): Flow<List<MovieWithImages>> = movieDao.getFavorites()

    fun getById(id: Long): Flow<Movie?> = movieDao.get(id)

    fun getByIdWithImages(id: Long): Flow<MovieWithImages?> = movieDao.getWithImages(id)
}
