package app.sample.navigation

sealed class NavigationRoutes(val route: String){
    object MainScreen: NavigationRoutes("main")

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