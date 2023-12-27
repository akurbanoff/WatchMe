package app.watchMe.ui.composables

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.watchMe.navigation.NavigationRoutes
import app.watchMe.R
import app.watchMe.utils.Cart
import app.watchMe.utils.Repository
import app.watchMe.utils.Watch
import app.watchMe.utils.watchList
import java.util.Random

@Composable
fun MainScreen(navigator: NavHostController, repository: Repository, cart: Cart) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        DefaultTopBar(isMainScreen = true, navigator = navigator, repository = repository, cart = cart)
        FeaturedWatchPart(navigator = navigator, repository = repository)
        Spacer(modifier = Modifier.height(22.dp))
        ProductsWatchPart(navigator = navigator, repository)
    }
}

//@Preview(showBackground = true)
@Composable
fun FeaturedWatchPart(navigator: NavHostController, repository: Repository) {
    val featuredWatch = watchList.random()

    Column {
        Text(
            text = "FEATURED",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(Color.LightGray),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).width(150.dp)
                ) {
                    Text(
                        text = featuredWatch.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                    Text(
                        text = if(featuredWatch.featureDescription.length < 65) featuredWatch.featureDescription else featuredWatch.featureDescription.take(65) + "...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                    Button(
                        onClick = { navigator.navigate(NavigationRoutes.DetailWatchScreen.withArgs(featuredWatch.id)) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Row {
                            Text(text = "Buy Now")
                            Icon(
                                imageVector = Icons.Default.ArrowRightAlt,
                                contentDescription = null
                            )
                        }
                    }
                }
                Image(
                    painter = painterResource(featuredWatch.presentationImage),
                    contentDescription = null,
                    modifier = Modifier.size(height = 190.dp, width = 150.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ProductsWatchPart(navigator: NavHostController, repository: Repository) {
    val randomWatchList = watchList.subList(fromIndex = 0, toIndex = 5)

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { navigator.navigate(NavigationRoutes.WatchGridScreen.route) }
        ) {
            Text(
                text = "PRODUCTS",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.headlineLarge
            )
            Row{
                Text(
                    text = "All",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineLarge
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(){
            items(randomWatchList){item ->
                WatchElement(navigator = navigator, watch = item, repository = repository)
            }
        }
    }
}

@Composable
fun WatchElement(navigator: NavHostController, watch: Watch, repository: Repository) {
    var setFavorite by remember{mutableStateOf(repository.checkWatchInFavoriteList(watch))}

    Column(
        modifier = Modifier
            .padding(end = 16.dp)
            .clickable {
                navigator.navigate(
                    NavigationRoutes.DetailWatchScreen.withArgs(watch.id)
                )
            }
    ) {
        Box(modifier = Modifier
            .height(230.dp).width(155.dp)
            .clip(MaterialTheme.shapes.large)
            .background(Color.LightGray)
            .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite, contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(30.dp)
                    .clickable {
                        setFavorite = !setFavorite
                        if(setFavorite) repository.addFavorite(watch) else repository.removeWatch(watch)
                               },
                tint = if(setFavorite) Color.Red else Color.White
            )
            Image(
                painter = painterResource(watch.presentationImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(height = 180.dp, width = 160.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = watch.company,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
        Text(
            text = watch.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
        )
    }
}