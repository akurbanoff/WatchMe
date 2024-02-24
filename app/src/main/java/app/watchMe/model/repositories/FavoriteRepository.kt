package app.watchMe.model.repositories

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import app.watchMe.model.Watch
import app.watchMe.model.watchList

class FavoriteRepository(activity: Activity) {
    var hideBottomSheet by mutableStateOf(false)
    var selectedSize by mutableStateOf("")
    var currentPagerIndex by mutableStateOf(0)

    private var favoriteWatches by mutableStateOf(emptyList<Watch>())
    private val prefs = activity.getPreferences(Context.MODE_PRIVATE)

    init {
        fillFavorite()
    }

    private fun fillFavorite(){
        for (watch in watchList){
            val watchId = prefs.getInt("fav${watch.id}", 0)
            if(watchId != 0){
                favoriteWatches = favoriteWatches.toMutableStateList().also { it.add(watch) }
            }
        }
    }

    fun getFavoriteList(): List<Watch>{
        return favoriteWatches
    }

    fun addFavorite(watch: Watch){
        if(!favoriteWatches.contains(watch)) {
            favoriteWatches = favoriteWatches.toMutableStateList().also { it.add(watch) }
            with(prefs.edit()){
                putInt("fav${watch.id}", watch.id)
                apply()
            }
        }
    }

    fun removeWatch(watch: Watch){
        favoriteWatches = favoriteWatches.toMutableStateList().also { it.remove(watch) }
        with(prefs.edit()){
            remove("fav${watch.id}")
            apply()
        }
    }

    fun checkWatchInFavoriteList(watch: Watch): Boolean{
        return favoriteWatches.contains(watch)
    }
}