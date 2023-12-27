package app.watchMe.utils

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf


data class Watch(
    val id: Int,
    val name: String,
    val company: String,
    val price: Int,
    val material: String,
    val strap: String,
    val caseThickness: String,
    val featureDescription: String,
    val presentationImage: Int,
    val imageList: List<Int>,
    val imageCount: Int = imageList.size,
    var cartAmount: MutableState<Int> = mutableStateOf(1)
)