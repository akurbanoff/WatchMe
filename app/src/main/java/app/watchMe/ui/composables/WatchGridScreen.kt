package app.watchMe.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.watchMe.navigation.NavigationRoutes
import app.watchMe.utils.Cart
import app.watchMe.utils.Repository
import app.watchMe.utils.Watch
import app.watchMe.utils.watchList

@Composable
fun WatchGreenScreen(navigator: NavHostController, cart: Cart, repository: Repository) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        DefaultTopBar(isMainScreen = false, navigator = navigator, cart = cart, repository = repository)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Watch List",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        GridList(navigator = navigator)
    }
}

@Composable
fun GridList(navigator: NavHostController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ){
        items(watchList){watch ->
            GridItem(navigator = navigator, watch = watch)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridItem(navigator: NavHostController, watch: Watch) {
    ElevatedCard(
        onClick = { navigator.navigate(NavigationRoutes.DetailWatchScreen.withArgs(watch.id)) },
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        ),
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(watch.presentationImage),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(height = 180.dp, width = 160.dp)
        )
        Text(
            text = "${watch.price} руб.",
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = watch.name,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}