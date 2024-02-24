package app.watchMe.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.IosShare
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.watchMe.domain.sendWatchBroadcast
import com.skydoves.flexible.core.FlexibleSheetState
import com.skydoves.flexible.core.rememberFlexibleBottomSheetState
import app.watchMe.ui.navigation.NavigationRoutes
import app.watchMe.ui.theme.WatchMeTheme
import app.watchMe.model.Watch
import app.watchMe.model.repositories.CartRepository
import app.watchMe.model.repositories.FavoriteRepository
import app.watchMe.model.watchList
import com.skydoves.flexible.bottomsheet.material3.FlexibleBottomSheet
import com.skydoves.flexible.core.FlexibleSheetSize
import com.skydoves.flexible.core.FlexibleSheetValue
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailWatchScreen(index: Int, favoriteRepository: FavoriteRepository, navigator: NavHostController, cartRepository: CartRepository) {
    val watch = watchList[index]

    val sheetState = rememberFlexibleBottomSheetState(
        flexibleSheetSize = FlexibleSheetSize(
            fullyExpanded = 0.93f,
            intermediatelyExpanded = 0.45f,
            slightlyExpanded = 0.45f
        ),
        allowNestedScroll = true,
        isModal = false
    )
    val scope = rememberCoroutineScope()

    if(favoriteRepository.hideBottomSheet){
        scope.launch { sheetState.show(target = FlexibleSheetValue.SlightlyExpanded) }
        favoriteRepository.hideBottomSheet = false
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DefaultTopBar(isMainScreen = false, navigator = navigator, cartRepository = cartRepository, favoriteRepository = favoriteRepository)
        HorizontalPager(
            state = rememberPagerState { watch.imageCount }
        ) { imageNumber ->
            favoriteRepository.currentPagerIndex = imageNumber
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(watch.imageList[favoriteRepository.currentPagerIndex]),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(0.4f)
                        //.size(height = 300.dp, width = 250.dp)
                        .background(MaterialTheme.colorScheme.background)
                )
            }
        }
        ImageDotsIndicator(
            pageCount = watch.imageCount,
            favoriteRepository = favoriteRepository,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        BottomSheet(sheetState = sheetState, watch = watch, favoriteRepository = favoriteRepository, navigator = navigator, cartRepository = cartRepository)
    }
}

@Composable
fun ImageDotsIndicator(pageCount: Int, favoriteRepository: FavoriteRepository, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount){index ->
            val color = if(index == favoriteRepository.currentPagerIndex) Color.Black else Color.Gray
            val isCurrent = index == favoriteRepository.currentPagerIndex
            Icon(
                imageVector = if(isCurrent) Icons.Default.Circle else Icons.Outlined.Circle,
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .size(25.dp)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun BottomSheet(sheetState: FlexibleSheetState, favoriteRepository: FavoriteRepository, watch: Watch, navigator: NavHostController, cartRepository: CartRepository) {
    var isFullyExp by remember {
        mutableStateOf(false)
    }
    
    FlexibleBottomSheet(
        onDismissRequest = { favoriteRepository.hideBottomSheet = true },
        containerColor = MaterialTheme.colorScheme.onSurface,
        shape = MaterialTheme.shapes.large,
        sheetState = sheetState,
        onTargetChanges = {
            if(it == FlexibleSheetValue.Hidden){
                isFullyExp = false
                favoriteRepository.hideBottomSheet = true
            } else if (it == FlexibleSheetValue.FullyExpanded){
                isFullyExp = true
            } else {
                isFullyExp = false
            }
        }
    ) {
        if(isFullyExp){
            FullyExpandedContent(watch = watch, favoriteRepository = favoriteRepository, navigator = navigator, cartRepository = cartRepository)
        } else {
            SlightExpandedContent(favoriteRepository = favoriteRepository, watch = watch, cartRepository = cartRepository)
        }
    }
}

@Composable
fun FullyExpandedContent(watch: Watch, favoriteRepository: FavoriteRepository, navigator: NavHostController, cartRepository: CartRepository) {
    SlightExpandedContent(favoriteRepository = favoriteRepository, watch = watch, cartRepository = cartRepository)
    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(text = "Additional Information", color = MaterialTheme.colorScheme.secondary)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "Case thickness", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = watch.caseThickness, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "Material", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = watch.material, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "Strap", color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = watch.strap, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.secondary)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Similar watches", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(){
            items(watchList){ item ->
                FullyExpandedWatchLazyRow(watch = item, navigator = navigator)
            }
        }
    }
}

@Composable
fun FullyExpandedWatchLazyRow(watch: Watch, navigator: NavHostController) {
    Column(
        modifier = Modifier
            .padding(end = 10.dp)
            .clickable { navigator.navigate(NavigationRoutes.DetailWatchScreen.withArgs(watch.id)) }
    ) {
        Box(
            modifier = Modifier
                .size(height = 120.dp, width = 100.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
        ){
            Image(
                painter = painterResource(watch.presentationImage),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(width = 80.dp, height = 100.dp)

            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "${watch.price} руб.", color = MaterialTheme.colorScheme.secondary)
        Text(text = watch.name, color = Color.Gray)
    }
}

@Composable
fun SlightExpandedContent(watch: Watch, favoriteRepository: FavoriteRepository, cartRepository: CartRepository) {
    val testSizeList = listOf("28 mm", "32 mm", "36 mm", "40 mm")
    var setFavorite by remember{mutableStateOf(favoriteRepository.checkWatchInFavoriteList(watch))}
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Text(
            text = "${watch.price} рублей",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = watch.name,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Choose your size",
                color = Color.Gray
            )
            Text(
                text = "Size guide",
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(testSizeList.size){item ->
                SizeSelector(size = testSizeList[item], favoriteRepository = favoriteRepository)
                Spacer(modifier = Modifier.width(16.dp))
                //вставить элемент, который можно выбирать, но только 1 из всех, нельхя выбрать 2
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                cartRepository.addWatch(watch)
                 },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Text(text = "ADD TO CART")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Row(
                modifier = Modifier.clickable {
                    setFavorite = !setFavorite
                    if(setFavorite) favoriteRepository.addFavorite(watch) else favoriteRepository.removeWatch(watch)
                }
            ) {
                Icon(
                    imageVector = if(setFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = if(setFavorite) Color.Red else Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Save", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
            }
            Row(
                modifier = Modifier.clickable { sendWatchBroadcast(context = context, watch = watch) }
            ) {
                Icon(imageVector = Icons.Default.IosShare, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Share", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Composable
fun SizeSelector(size: String, favoriteRepository: FavoriteRepository) {
    var isElementChosen = favoriteRepository.selectedSize == size
    Box(
        modifier = Modifier
            .size(50.dp)
            .clickable {
                favoriteRepository.selectedSize = size
                isElementChosen = !isElementChosen
            }
            .clip(MaterialTheme.shapes.medium)
            .background(
                if (isElementChosen) Color.Gray else Color.DarkGray
            ),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = size,
            color = Color.White,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar(isMainScreen: Boolean, navigator: NavHostController, favoriteRepository: FavoriteRepository, cartRepository: CartRepository) {
    TopAppBar(
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
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
                    }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
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
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { navigator.navigate(NavigationRoutes.CartScreen.route) })
                }
                Spacer(modifier = Modifier.width(8.dp))
            }
        },
        navigationIcon = {
            if(isMainScreen){
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navigator.popBackStack() }
                )
            }
        }
    )
}