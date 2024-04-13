import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.components.SimpleBottomAppBar
import com.example.movieappmad24.components.SimpleTopAppBar
import com.example.movieappmad24.components.MovieLazyColumn
import com.example.movieappmad24.models.getNavItems
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun HomeScreen(navController: NavController, viewModel: MoviesViewModel){
    HomeScreenScaffold(navController = navController, viewModel)
}

@Composable
fun HomeScreenScaffold(navController: NavController, viewModel: MoviesViewModel){
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
            movies = viewModel.movies,
            viewModel = viewModel
        )
    }
}
