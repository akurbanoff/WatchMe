package app.watchMe.ui.composables

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Remove
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import app.watchMe.navigation.NavigationRoutes
import app.watchMe.utils.Cart
import app.watchMe.utils.Repository
import app.watchMe.utils.Watch

@Composable
fun CartScreen(navigator: NavHostController, cart: Cart, repository: Repository) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        CartTopBar(navigator = navigator, repository = repository, cart = cart)
        CartList(navigator = navigator, cart = cart)
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "BUY", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CartList(navigator: NavHostController, cart: Cart) {
    LazyColumn(){
        items(cart.getCartList()){watch ->
            CartElement(navigator = navigator, cart = cart, watch = watch)
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
                        text = cart.getTotalPrice().toString() + " руб.",
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
                        text = cart.getTotalPrice().minus(2000).toString() + " руб.",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(Color.White)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartElement(navigator: NavHostController, cart: Cart, watch: Watch) {
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
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .background(Color.White, shape = CircleShape)
                        .size(30.dp)
                        .align(Alignment.End)
                        .clickable { cart.removeWatch(watch) }
                )
                Text(
                    text = watch.name,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 8.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = cart.getWatchPrice(watch).toString() + " руб.",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.RemoveCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { cart.changeAmount(watch, increase = false) }
                                .size(40.dp)
                        )
                        Text(
                            text = cart.getAmount(watch),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White
                        )
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clickable { cart.changeAmount(watch, increase = true) }
                                .size(40.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartTopBar(navigator: NavHostController, cart: Cart, repository: Repository) {
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
                        containerColor = if(repository.getFavoriteList().isNotEmpty()) Color.Red else Color.Transparent,
                        contentColor = Color.Black
                    ) {
                        if(repository.getFavoriteList().isNotEmpty()) {
                            Text(text = repository.getFavoriteList().size.toString())
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
                        containerColor = if(cart.getCartList().isNotEmpty()) Color.Red else Color.Transparent,
                        contentColor = Color.Black
                    ) {
                        if(cart.getCartList().isNotEmpty()) {
                            Text(text = cart.getCartList().size.toString())
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