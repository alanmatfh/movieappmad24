package com.example.movieappmad24.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun MovieLazyColumn(padding: PaddingValues, navController: NavController, movies: List<Movie>,
                    viewModel: MoviesViewModel){
    Column(
        modifier = Modifier
            .padding(padding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        LazyColumn {
            items(movies) { movie ->
                MovieRow(
                    movie = movie,
                    onFavoriteClick = {
                        movieId -> viewModel.toggleFavorite(movieId)
                    }
                ) { movieId ->
                    navController.navigate(Screen.DetailScreen.createRoute(movieId))
                }
            }
        }
    }
}

@Composable
fun MovieRow(movie: Movie, onFavoriteClick: (String) -> Unit = {}, onItemClick: (String) -> Unit = {}){
    var showDetails by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                onItemClick(movie.id)
            },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = movie.images.first(),
                    contentDescription = "Image for the movie ${movie.title}",
                    placeholder = painterResource(id = R.drawable.movie_image),
                    contentScale = ContentScale.Crop
                )
                FavoriteIcon(
                    isFavorite = movie.isFavorite
                ) {
                    onFavoriteClick(movie.id)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .clickable {
                        showDetails = !showDetails
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title)
                Icon(
                    modifier = Modifier
                        .clickable {
                            showDetails = !showDetails
                        },
                    imageVector =
                    if (showDetails) Icons.Filled.KeyboardArrowDown
                    else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
                )
            }
        }
        AnimatedVisibility(showDetails) {
            MovieDetails(movie = movie)
        }
    }
}

@Composable
fun MovieDetails(movie: Movie){
    Column(
        modifier = Modifier.padding(10.dp)
    ) {
        Text(text = "Director: ${movie.director}")
        Text(text = "Released: ${movie.year}")
        Text(text = "Genre: ${movie.genre}")
        Text(text = "Actors: ${movie.actors}")
        Text(text = "Rating: ${movie.rating}")
        Divider(
            modifier = Modifier.padding(vertical = 6.dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "Plot: ${movie.plot}")
    }
}

@Composable
fun FavoriteIcon(isFavorite: Boolean, onFavoriteClick: () -> Unit = {}){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Icon(
            modifier = Modifier.clickable { onFavoriteClick() },
            tint = MaterialTheme.colorScheme.tertiary,
            imageVector =
            if (isFavorite) {
                Icons.Filled.Favorite
            }else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = "Add to favorites"
        )
    }
}
