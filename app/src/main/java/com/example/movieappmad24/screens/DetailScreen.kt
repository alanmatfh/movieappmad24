package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.components.DetailTopBar
import com.example.movieappmad24.components.DetailViewContent
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.DetailsViewModel
import com.example.movieappmad24.viewmodels.DetailsViewModelFactory

@Composable
fun DetailScreen(navController: NavController, movieId: Long?){
    movieId ?: return
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao(), movieImageDao = db.movieImageDao())
    val factory = DetailsViewModelFactory(repository = repository, movieId = movieId)
    val viewModel: DetailsViewModel = viewModel(factory = factory)

    DetailScreenScaffold(navController, viewModel)
}

@Composable
fun DetailScreenScaffold(navController: NavController, viewModel: DetailsViewModel){
    val movieWithImages by viewModel.movie.collectAsState()
    Scaffold(
        topBar = {
            DetailTopBar(title = movieWithImages.movie.title) {
                navController.popBackStack()
            }
        }
    ) { innerPadding ->
        DetailViewContent(
            padding = innerPadding,
            movie = movieWithImages,
            viewModel = viewModel
        )
    }
}
