package app.watchMe.navigation

sealed class NavigationRoutes(val route: String){
    object MainScreen: NavigationRoutes("main")
    object DetailWatchScreen: NavigationRoutes("detail")
    object CartScreen: NavigationRoutes("cart")
    object FavoriteScreen: NavigationRoutes("favorite")
    object WatchGridScreen: NavigationRoutes("watchGrid")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }

    fun withArgs(vararg args: Int): String{
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/${arg.toString()}")
            }
        }
    }
}