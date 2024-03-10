package com.example.movieappmad24.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector
)

fun getNavItems(): List<NavItem>{
    return listOf(
        NavItem(
            label = "Home",
            icon = Icons.Filled.Home,
        ),
        NavItem(
            label = "Watchlist",
            icon = Icons.Filled.Star,
        )
    )
}
