package app.watchMe.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.watchMe.ui.navigation.NavigationRoutes
import app.watchMe.model.Watch
import app.watchMe.model.repositories.CartRepository
import app.watchMe.model.repositories.FavoriteRepository

@Composable
fun CartScreen(
    navigator: NavHostController,
    cartRepository: CartRepository,
    favoriteRepository: FavoriteRepository
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        BoxWithConstraints {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                CartTopBar(navigator = navigator, favoriteRepository = favoriteRepository, cartRepository = cartRepository)
                CartList(navigator = navigator, cartRepository = cartRepository, favoriteRepository = favoriteRepository)
            }

            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
                    .size(80.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
fun CartList(navigator: NavHostController, cartRepository: CartRepository, favoriteRepository: FavoriteRepository) {
    LazyColumn(){
        items(cartRepository.getCartList()){watch ->
            CartElement(navigator = navigator, cartRepository = cartRepository, watch = watch, favoriteRepository = favoriteRepository)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray, MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ) {
                Text(
                    text = "AMOUNT",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "ITEM TOTAL",
                        color = Color.White
                    )
                    Text(
                        text = cartRepository.getTotalPrice().toString() + " руб.",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "DELIVERY FREE",
                        color = Color.White
                    )
                    Text(
                        text = "2000 руб.",
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(color = MaterialTheme.colorScheme.background, thickness = 2.dp)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "TOTAL",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        text = cartRepository.getTotalPrice().minus(2000).toString() + " руб.",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.clip(MaterialTheme.shapes.small)
                    )
                }
            }
        }
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartElement(navigator: NavHostController, cartRepository: CartRepository, watch: Watch, favoriteRepository: FavoriteRepository) {
    var setFavorite by remember{ mutableStateOf(favoriteRepository.checkWatchInFavoriteList(watch)) }

    ElevatedCard(
        onClick = { navigator.navigate(NavigationRoutes.DetailWatchScreen.withArgs(watch.id)) },
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.LightGray),
        ) {
            Image(
                painter = painterResource(watch.presentationImage),
                contentDescription = null,
                modifier = Modifier
                    .size(height = 130.dp, width = 100.dp)
                    .align(Alignment.CenterVertically)
                    .background(Color.White, MaterialTheme.shapes.medium)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 8.dp)
            ) {
                Text(
                    text = watch.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "Производитель - ${watch.company}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Start),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Материал - ${watch.material}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.Start),
                    color = MaterialTheme.colorScheme.onSurface
                )
//                Text(
//                    text = "Описание - ${watch.featureDescription}",
//                    textAlign = TextAlign.Center,
//                    style = MaterialTheme.typography.titleSmall,
//                    modifier = Modifier.align(Alignment.Start),
//                    color = MaterialTheme.colorScheme.onSurface
//                )
                Text(
                    text = cartRepository.getWatchPrice(watch).toString() + " руб.",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Start),
                    textAlign = TextAlign.Center
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.RemoveCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { cartRepository.changeAmount(watch, increase = false) }
                        .size(34.dp)
                )
                Text(
                    text = cartRepository.getAmount(watch),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .clickable { cartRepository.changeAmount(watch, increase = true) }
                        .size(34.dp)
                )
            }
            Row{
                Icon(
                    imageVector = Icons.Default.Favorite, contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            setFavorite = !setFavorite
                            if (setFavorite) favoriteRepository.addFavorite(watch) else favoriteRepository.removeWatch(
                                watch
                            )
                        },
                    tint = if(setFavorite) Color.Red else Color.White
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    imageVector = Icons.Default.DeleteOutline,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { cartRepository.removeWatch(watch) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(navigator: NavHostController, cartRepository: CartRepository, favoriteRepository: FavoriteRepository) {
    TopAppBar(
        title = { },
        navigationIcon = {
            Row {
                Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navigator.popBackStack() })
                Text(text = "Cart", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
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
                        } }
                    }
                ) {
                    Icon(imageVector = Icons.Default.Favorite, contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navigator.navigate(NavigationRoutes.FavoriteScreen.route) })
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
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
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