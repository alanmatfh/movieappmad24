package com.example.movieappmad24

import HomeViewContent
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextOverflow
import com.example.movieappmad24.models.NavItem
import com.example.movieappmad24.models.getNavItems

@Composable
fun MainScaffold(){
    Scaffold(
        topBar = {
            MovieTopBar()
        },
        bottomBar = {
            MovieNavBar(items = getNavItems())
        }
    ) { innerPadding ->
        HomeViewContent(padding = innerPadding)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopBar(){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Movie App",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}

@Composable
fun MovieNavBar(items: List<NavItem>){
    var selectedItem by remember { mutableIntStateOf(0) }

    NavigationBar (
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                       Icon(
                           imageVector = item.icon,
                           contentDescription = item.label
                       )
                },
                label = { Text(item.label) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}