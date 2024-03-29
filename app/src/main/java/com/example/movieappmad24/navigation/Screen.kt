package com.example.movieappmad24.navigation

sealed class Screen (val route: String){
    data object HomeScreen : Screen("homescreen")
    data object DetailScreen : Screen("detailscreen/{movieId}") {
        fun createRoute(movieId: String) = "detailscreen/$movieId"
    }
    data object WatchlistScreen : Screen("watchlist")
}