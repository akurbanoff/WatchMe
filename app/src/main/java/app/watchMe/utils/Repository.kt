package app.watchMe.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList

class Repository {
    var hideBottomSheet by mutableStateOf(false)
    var selectedSize by mutableStateOf("")
    var currentPagerIndex by mutableStateOf(0)

    private var favoriteWatches by mutableStateOf(emptyList<Watch>())

    fun getFavoriteList(): List<Watch>{
        return favoriteWatches
    }

    fun addFavorite(watch: Watch){
        if(!favoriteWatches.contains(watch)) {
            favoriteWatches = favoriteWatches.toMutableStateList().also { it.add(watch) }
        }
    }

    fun removeWatch(watch: Watch){
        favoriteWatches = favoriteWatches.toMutableStateList().also { it.remove(watch) }
    }

    fun checkWatchInFavoriteList(watch: Watch): Boolean{
        return favoriteWatches.contains(watch)
    }
}