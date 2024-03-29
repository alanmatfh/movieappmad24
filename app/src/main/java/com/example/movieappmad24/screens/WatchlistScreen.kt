package com.example.movieappmad24.screens

import com.example.movieappmad24.components.MovieLazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.components.MovieNavBar
import com.example.movieappmad24.components.MovieTopBar
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.models.getNavItems

@Composable
fun WatchlistScreen(navController: NavController){
    Scaffold(
        topBar = {
            MovieTopBar(text = "Your Watchlist")
        },
        bottomBar = {
            MovieNavBar(items = getNavItems(), navController = navController)
        }
    ) { innerPadding ->
        val wlMovies = getMovies().slice(3..5)
        MovieLazyColumn(
            padding = innerPadding,
            navController = navController,
            movies = wlMovies
        )
    }
}
