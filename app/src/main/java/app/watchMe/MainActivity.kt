package app.watchMe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.watchMe.navigation.Navigation
import app.watchMe.ui.theme.WatchMeTheme
import app.watchMe.utils.Cart
import app.watchMe.utils.Repository

class MainActivity : ComponentActivity() {

//    private val db by lazy {
//        Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "watchMe_db"
//        ).build()
//    }
//
//    private val watchViewModel by viewModels<WatchViewModel> (
//        factoryProducer = {
//            object : ViewModelProvider.Factory{
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return WatchViewModel(db.watchDao) as T
//                }
//            }
//        }
//    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //watchViewModel.addWatch()
                    Navigation()
                }
            }
        }
    }

}

