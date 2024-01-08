package app.watchMe.model.repositories

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import app.watchMe.model.Watch

class CartRepository {
    private var cart by mutableStateOf(emptyList<Watch>())

    fun addWatch(watch: Watch){
        if(!cart.contains(watch)) {
            cart = cart.toMutableStateList().also { it.add(watch) }
        }
    }

    fun removeWatch(watch: Watch){
        cart = cart.toMutableStateList().also { it.remove(watch) }
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