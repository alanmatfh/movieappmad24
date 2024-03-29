import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.components.MovieNavBar
import com.example.movieappmad24.components.MovieTopBar
import com.example.movieappmad24.components.MovieLazyColumn
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.models.getNavItems

@Composable
fun HomeScreen(navController: NavController){
    HomeScreenScaffold(navController = navController)
}

@Composable
fun HomeScreenScaffold(navController: NavController){
    Scaffold(
        topBar = {
            MovieTopBar()
        },
        bottomBar = {
            MovieNavBar(items = getNavItems(), navController = navController)
        }
    ) { innerPadding ->
        MovieLazyColumn(
            padding = innerPadding,
            navController = navController,
            movies = getMovies()
        )
    }
}
