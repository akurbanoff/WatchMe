package app.watchMe.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import app.watchMe.model.repositories.CartRepository
import app.watchMe.model.repositories.FavoriteRepository

@Composable
fun Navigation(activity: Activity) {
    val navigator = rememberNavController()
    val cartRepository by remember {
        mutableStateOf(CartRepository(activity))
    }
    val favoriteRepository by remember {
        mutableStateOf(FavoriteRepository(activity))
    }

    NavHost(navController = navigator, startDestination = NavigationRoutes.MainScreen.route){
        composable(NavigationRoutes.MainScreen.route){ MainScreen(navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)}
        composable(
            NavigationRoutes.DetailWatchScreen.route + "/{index}", arguments = listOf(
            navArgument("index"){type = NavType.IntType}
        )){navBackStackEntry -> navBackStackEntry.arguments?.let {
          val index = it.getInt("index")
          DetailWatchScreen(index = index, navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)
        } }
        composable(NavigationRoutes.CartScreen.route){ CartScreen(navigator = navigator, cartRepository = cartRepository, favoriteRepository = favoriteRepository)}
        composable(NavigationRoutes.FavoriteScreen.route){ FavoriteScreen(navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)}
        composable(NavigationRoutes.WatchGridScreen.route){ WatchGreenScreen(navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)}
    }
}