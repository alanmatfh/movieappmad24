package com.example.movieappmad24.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.WatchlistScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = Screen.HomeScreen.route) {

        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = "movieId") {type = NavType.StringType})
        ) { backStackEntry ->
            DetailScreen(
                navController = navController,
                movieId = backStackEntry.arguments?.getString("movieId")
            )
        }

        composable(route = Screen.WatchlistScreen.route) {
            WatchlistScreen(navController = navController)
        }
    }
}