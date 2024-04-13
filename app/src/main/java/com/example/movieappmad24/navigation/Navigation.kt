package com.example.movieappmad24.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val moviesViewModel: MoviesViewModel = viewModel()

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController, viewModel = moviesViewModel)
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = "movieId") {type = NavType.StringType})
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString("movieId"),
                viewModel = moviesViewModel
            )
        }

        composable(route = Screen.WatchlistScreen.route) {
            WatchlistScreen(navController = navController, viewModel = moviesViewModel)
        }
    }
}
