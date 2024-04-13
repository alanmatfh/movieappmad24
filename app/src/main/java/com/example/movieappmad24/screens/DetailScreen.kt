package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.components.DetailTopBar
import com.example.movieappmad24.components.DetailViewContent
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun DetailScreen(navController: NavController, movieId: String?, viewModel: MoviesViewModel){
    val movie = viewModel.movies.find { it.id == movieId } ?: return
    DetailScreenScaffold(navController, movie, viewModel)
}

@Composable
fun DetailScreenScaffold(navController: NavController, movie: Movie, viewModel: MoviesViewModel){
    Scaffold(
        topBar = {
            DetailTopBar(title = movie.title) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        DetailViewContent(
            padding = innerPadding,
            movie = movie,
            viewModel = viewModel
        )
    }
}
