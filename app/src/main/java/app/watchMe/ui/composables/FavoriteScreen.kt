package app.watchMe.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.watchMe.ui.navigation.NavigationRoutes
import app.watchMe.model.Watch
import app.watchMe.model.repositories.CartRepository
import app.watchMe.model.repositories.FavoriteRepository

@Composable
fun FavoriteScreen(navigator: NavHostController, favoriteRepository: FavoriteRepository, cartRepository: CartRepository) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        FavoriteTopBar(navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)
        FavoriteSearchBar()
        Spacer(modifier = Modifier.height(16.dp))
        FavoriteList(navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)
    }
}

@Composable
fun FavoriteList(navigator: NavHostController, favoriteRepository: FavoriteRepository, cartRepository: CartRepository) {
    LazyColumn(){
        items(favoriteRepository.getFavoriteList()){watch ->
            FavoriteElement(navigator = navigator, watch = watch, favoriteRepository = favoriteRepository, cartRepository = cartRepository)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun FavoriteElement(navigator: NavHostController, watch: Watch, favoriteRepository: FavoriteRepository, cartRepository: CartRepository) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.LightGray)
            .clickable { navigator.navigate(NavigationRoutes.DetailWatchScreen.withArgs(watch.id)) }
    ) {
        Image(
            painter = painterResource(watch.presentationImage), contentDescription = null,
            modifier = Modifier
                .size(width = 100.dp, height = 150.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(Color.White)
                .align(Alignment.CenterVertically)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(
                text = watch.name,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "${watch.price} руб.",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge
            )
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { favoriteRepository.removeWatch(watch) }
                )
                Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { cartRepository.addWatch(watch) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteSearchBar() {
    var activeState by remember{ mutableStateOf(false) }

    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = activeState,
        onActiveChange = {activeState = !activeState},
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null)},
        shape = MaterialTheme.shapes.large,
        colors = SearchBarDefaults.colors(
            containerColor = Color.White
        ),
        placeholder = { Text(text = "Search")}
    ) {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteTopBar(navigator: NavHostController, favoriteRepository: FavoriteRepository, cartRepository: CartRepository) {
    TopAppBar(
        title = { },
        navigationIcon = {
            Row {
                Icon(
                    imageVector = Icons.Default.ChevronLeft, contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navigator.popBackStack() }
                )
                Text(text = "Favorite", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
            }
        },
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                BadgedBox(
                    badge = { Badge(
                        containerColor = if(favoriteRepository.getFavoriteList().isNotEmpty()) Color.Red else Color.Transparent,
                        contentColor = Color.Black
                    ) {
                        if(favoriteRepository.getFavoriteList().isNotEmpty()) {
                            Text(text = favoriteRepository.getFavoriteList().size.toString())
                        }
                    }}
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null, modifier = Modifier.size(40.dp), tint = Color.Red)
                }
                Spacer(modifier = Modifier.width(12.dp))
                BadgedBox(
                    badge = { Badge(
                        containerColor = if(cartRepository.getCartList().isNotEmpty()) Color.Red else Color.Transparent,
                        contentColor = Color.Black
                    ) {
                        if(cartRepository.getCartList().isNotEmpty()) {
                            Text(text = cartRepository.getCartList().size.toString())
                        }
                    }}
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navigator.navigate(NavigationRoutes.CartScreen.route) })
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}