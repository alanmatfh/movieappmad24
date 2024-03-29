package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.components.DetailTopBar
import com.example.movieappmad24.components.DetailViewContent
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

@Composable
fun DetailScreen(navController: NavController, movieId: String?){
    val movie = getMovies().find { it.id == movieId } ?: return
    DetailScreenScaffold(navController = navController, movie)
}

@Composable
fun DetailScreenScaffold(navController: NavController, movie: Movie){
    Scaffold(
        topBar = {
            DetailTopBar(title = movie.title) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        DetailViewContent(padding = innerPadding, movie = movie)
    }
}
