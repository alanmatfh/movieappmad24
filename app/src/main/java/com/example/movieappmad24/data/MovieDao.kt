package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun add(movie: Movie): Long

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Query("SELECT * from movie where movieId=:id")
    fun get(id: Long): Flow<Movie>

    @Transaction
    @Query("SELECT * from movie where movieId=:id")
    fun getWithImages(id: Long): Flow<MovieWithImages>

    @Transaction
    @Query("SELECT * from movie")
    fun getAll(): Flow<List<MovieWithImages>>

    @Transaction
    @Query("SELECT * from movie where isFavorite= 1")
    fun getFavorites(): Flow<List<MovieWithImages>>
}