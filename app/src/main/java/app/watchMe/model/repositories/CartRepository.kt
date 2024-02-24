package app.watchMe.model.repositories

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import app.watchMe.model.Watch
import app.watchMe.model.watchList

class CartRepository(activity: Activity) {
    private var cart by mutableStateOf(emptyList<Watch>())
    private val prefs = activity.getPreferences(Context.MODE_PRIVATE)

    init {
        fillCart()
    }

    private fun fillCart(){
        for (watch in watchList){
            val watchId = prefs.getInt("id${watch.id}", 0)
            if(watchId != 0){
                cart = cart.toMutableStateList().also { it.add(watch) }
            }
        }
    }

    fun addWatch(watch: Watch){
        if(!cart.contains(watch)) {
            cart = cart.toMutableStateList().also { it.add(watch) }
            with(prefs.edit()){
                putInt("id${watch.id}", watch.id)
                apply()
            }
        }
    }

    fun removeWatch(watch: Watch){
        cart = cart.toMutableStateList().also { it.remove(watch) }
        with(prefs.edit()){
            remove("id${watch.id}")
            apply()
        }
    }

    fun getCartList() : List<Watch>{
        return cart
    }

    fun getTotalPrice(): Int{
        var totalPrice = 0
        cart.forEach { totalPrice += it.price * it.cartAmount.value }
        return totalPrice
    }

    fun changeAmount(watch: Watch, increase: Boolean){
        if(increase){
            watch.cartAmount.value += 1
        } else {
            watch.cartAmount.value -= 1
            if(watch.cartAmount.value < 1){
                watch.cartAmount.value = 1
                removeWatch(watch)
            }
        }
    }

    fun getAmount(watch: Watch): String{
        return watch.cartAmount.value.toString()
    }

    fun getWatchPrice(watch: Watch): Int{
        return watch.price * watch.cartAmount.value
    }
}