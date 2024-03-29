package com.example.movieappmad24.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieappmad24.navigation.Screen

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

fun getNavItems(): List<NavItem>{
    return listOf(
        NavItem(
            label = "Home",
            icon = Icons.Filled.Home,
            route = Screen.HomeScreen.route,
        ),
        NavItem(
            label = "Watchlist",
            icon = Icons.Filled.Star,
            route = Screen.WatchlistScreen.route,
        )
    )
}
