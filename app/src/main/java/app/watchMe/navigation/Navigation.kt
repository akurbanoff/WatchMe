package app.watchMe.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.watchMe.ui.composables.CartScreen
import app.watchMe.ui.composables.DetailWatchScreen
import app.watchMe.ui.composables.FavoriteScreen
import app.watchMe.ui.composables.MainScreen
import app.watchMe.ui.composables.WatchGreenScreen
import app.watchMe.utils.Cart
import app.watchMe.utils.Repository

@Composable
fun Navigation() {
    val navigator = rememberNavController()
    val cart by remember {
        mutableStateOf(Cart())
    }
    val repository by remember {
        mutableStateOf(Repository())
    }

    NavHost(navController = navigator, startDestination = NavigationRoutes.MainScreen.route){
        composable(NavigationRoutes.MainScreen.route){ MainScreen(navigator = navigator, repository = repository, cart = cart)}
        composable(NavigationRoutes.DetailWatchScreen.route + "/{index}", arguments = listOf(
            navArgument("index"){type = NavType.IntType}
        )){navBackStackEntry -> navBackStackEntry.arguments?.let {
          val index = it.getInt("index")
          DetailWatchScreen(index = index, navigator = navigator, repository = repository, cart = cart)
        } }
        composable(NavigationRoutes.CartScreen.route){ CartScreen(navigator = navigator, cart = cart, repository = repository)}
        composable(NavigationRoutes.FavoriteScreen.route){ FavoriteScreen(navigator = navigator, repository = repository, cart = cart)}
        composable(NavigationRoutes.WatchGridScreen.route){ WatchGreenScreen(navigator = navigator, repository = repository, cart = cart)}
    }
}