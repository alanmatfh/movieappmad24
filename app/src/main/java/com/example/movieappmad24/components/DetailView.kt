package com.example.movieappmad24.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie

@Composable
fun DetailViewContent(padding: PaddingValues, movie: Movie){
    Column(
        modifier = Modifier.padding(padding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MovieRow(movie = movie)

        LazyRow {
            items(movie.images) { image ->
                ImageCard(
                    imageUrl = image,
                    modifier = Modifier
                        .fillParentMaxWidth(0.95f)
                        .padding(horizontal = 5.dp)
                        .height(200.dp)
                )
            }
        }
    }
}

@Composable
fun ImageCard(imageUrl: String, modifier: Modifier){
    Card(
        modifier = modifier,
        shape = ShapeDefaults.Small,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "Image under url: $imageUrl",
            placeholder = painterResource(id = R.drawable.movie_image),
            contentScale = ContentScale.Crop
        )
    }
}
