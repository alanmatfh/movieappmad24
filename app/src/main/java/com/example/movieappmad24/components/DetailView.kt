package com.example.movieappmad24.components

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.viewmodels.DetailsViewModel

@SuppressLint("DiscouragedApi")
@Composable
fun DetailViewContent(padding: PaddingValues, movie: MovieWithImages, viewModel: DetailsViewModel){
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(
        movie.movie.trailer, "raw", context.packageName
    )
    Column(
        modifier = Modifier.padding(padding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MovieRow(
            movieWithImages = movie,
            onFavoriteClick = { _ -> viewModel.toggleFavorite() }
        )

        Text(
            text = "Trailer",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        if(resId != 0) {
            SmallVideoPlayer(
                context = context,
                resId = resId
            )
        }

        LazyRow {
            items(movie.movieImages) { image ->
                ImageCard(
                    imageUrl = image.imgUrl,
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

@Composable
fun SmallVideoPlayer(context: Context, resId: Int){
    val mediaSrc = remember(resId) {
        MediaItem.fromUri(Uri.parse(
            "android.resource://${context.packageName}/$resId"))
    }
    val exoPlayer = ExoPlayer.Builder(context).build()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(mediaSrc) {
        exoPlayer.setMediaItem(mediaSrc)
        exoPlayer.playWhenReady = true
        exoPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    val playerView = remember {
        val playerView = PlayerView(context)
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                playerView.onResume()
                exoPlayer.playWhenReady = true
            }

            override fun onStop(owner: LifecycleOwner) {
                playerView.onPause()
                exoPlayer.playWhenReady = false
            }
        })
        playerView
    }

    AndroidView(
        factory = { _ ->
              playerView.apply {
                  player = exoPlayer
              }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
