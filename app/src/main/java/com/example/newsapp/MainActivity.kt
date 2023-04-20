package com.example.newsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.ui.detail.ArticleDetailScreen
import com.example.newsapp.ui.favourite.FavouriteScreen
import com.example.newsapp.ui.homescreen.BreakingNewsScreen
import com.example.newsapp.ui.search.SearchScreen
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                //Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                //}

                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                            BottomNavItem(
                                name = "Home",
                                route = "home",
                                icon = Icons.Default.Home
                            ),
                            BottomNavItem(
                                name = "Favourite",
                                route = "favourite",
                                icon = Icons.Default.Star
                            ),
                            BottomNavItem(
                                name = "Search",
                                route = "search",
                                icon = Icons.Default.Search
                            ),
                        ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ){
                    Navigation(navController = navController)
                }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home"){
        composable("home"){
            BreakingNewsScreen(
                onNavigateDetail = {article -> navController.navigate(
                    route="detail/{$article}"
                )}
            )
        }
        composable(
            route = "detail/{articleName}",
            arguments = listOf(navArgument("articleName"){ type = NavType.StringType})
        ){navBackStackEntry ->
            ArticleDetailScreen(
                articleName = navBackStackEntry.arguments?.getString("articleName").orEmpty(),
                onBack = {navController.navigate(route="home")}
            )
        }
        composable("favourite"){
            FavouriteScreen(
                onNavigateDetail = {article -> navController.navigate(
                    route="detail/{$article}"
                )},
            )
        }
        composable(
            route = "search")
            {
            SearchScreen(
                onNavigateDetail = {article -> navController.navigate(
                    route="detail/{$article}"
                )},

            )}

    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick : (BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
    ){
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = {onItemClick(item)},
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally){
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name,
                        )
                        if(selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,

                                )
                        }
                    }

                })
        }
    }
}

fun NavHostController.navigateDetail() =
    navigate("detail")
