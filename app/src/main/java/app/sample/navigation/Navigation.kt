package app.sample.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.sample.navigation.NavigationRoutes
import app.sample.ui.composables.MainScreen
import app.sample.viewModels.SampleViewModel

@Composable
fun Navigation(sampleViewModel: SampleViewModel) {
    val navigator = rememberNavController()

    NavHost(navController = navigator, startDestination = NavigationRoutes.MainScreen.route){
        composable(NavigationRoutes.MainScreen.route){ MainScreen(sampleViewModel = sampleViewModel)}
    }
}