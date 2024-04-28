package com.example.movieappmad24.screens

import com.example.movieappmad24.components.MovieLazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.components.SimpleBottomAppBar
import com.example.movieappmad24.components.SimpleTopAppBar
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.getNavItems
import com.example.movieappmad24.viewmodels.DetailsViewModel
import com.example.movieappmad24.viewmodels.DetailsViewModelFactory
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.WatchlistViewModel
import com.example.movieappmad24.viewmodels.WatchlistViewModelFactory

@Composable
fun WatchlistScreen(navController: NavController){
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao(), movieImageDao = db.movieImageDao())
    val factory = WatchlistViewModelFactory(repository = repository)
    val viewModel: WatchlistViewModel = viewModel(factory = factory)

    val favoriteMovies by viewModel.movies.collectAsState()

    Scaffold(
        topBar = {
            SimpleTopAppBar(text = "Your Watchlist")
        },
        bottomBar = {
            SimpleBottomAppBar(items = getNavItems(), navController = navController)
        }
    ) { innerPadding ->
        MovieLazyColumn(
            padding = innerPadding,
            navController = navController,
            movies = favoriteMovies
        ) {
            movieId -> viewModel.toggleFavorite(movieId)
        }
    }
}
