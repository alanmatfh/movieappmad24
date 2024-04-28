import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.components.SimpleBottomAppBar
import com.example.movieappmad24.components.SimpleTopAppBar
import com.example.movieappmad24.components.MovieLazyColumn
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.getNavItems
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MovieViewModelFactory

@Composable
fun HomeScreen(navController: NavController){
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao(), movieImageDao = db.movieImageDao())
    val factory = MovieViewModelFactory(repository = repository)
    val viewModel: MoviesViewModel = viewModel(factory = factory)

    HomeScreenScaffold(navController = navController, viewModel = viewModel)
}

@Composable
fun HomeScreenScaffold(navController: NavController, viewModel: MoviesViewModel){
    val movies by viewModel.movies.collectAsState()
    Scaffold(
        topBar = {
            SimpleTopAppBar()
        },
        bottomBar = {
            SimpleBottomAppBar(items = getNavItems(), navController = navController)
        }
    ) { innerPadding ->
        MovieLazyColumn(
            padding = innerPadding,
            navController = navController,
            movies = movies
        ) {
            movieId -> viewModel.toggleFavorite(movieId)
        }
    }
}
