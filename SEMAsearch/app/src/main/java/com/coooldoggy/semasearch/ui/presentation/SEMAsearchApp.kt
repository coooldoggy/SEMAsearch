package com.coooldoggy.semasearch.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coooldoggy.semasearch.ui.common.BottomNavItem
import com.coooldoggy.semasearch.ui.common.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SEMASearchApp() {
    val mainNavController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(true) }
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()

    showBottomBar = when (navBackStackEntry?.destination?.route) {
        ScreenRoute.SearchScreen.name, ScreenRoute.SplashScreen.name -> false
        else -> true
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            Box(modifier = Modifier.fillMaxWidth().background(Color.White), contentAlignment = Alignment.BottomStart) {
                if (showBottomBar) {
                    BottomNavigationGraph(onClickSearch = {
                        navigateToSearchScreen(mainNavController = mainNavController)
                    })
                }
            }
        },
    ) { _innerPadding ->
        Box(modifier = Modifier.padding(_innerPadding)) {
            SetUpNavHost(mainNavController = mainNavController)
        }
    }
}

private fun navigateToSearchScreen(mainNavController: NavHostController) {
    mainNavController.navigate(route = ScreenRoute.SearchScreen.name)
}

private fun navigateToHomeScreen(mainNavController: NavHostController) {
    mainNavController.navigate(route = ScreenRoute.HomeScreen.name) {
        popUpTo(ScreenRoute.SplashScreen.name) { inclusive = true }
        launchSingleTop = true
    }
}

@Composable
fun SetUpNavHost(mainNavController: NavHostController) {
    NavHost(navController = mainNavController, startDestination = ScreenRoute.SplashScreen.name) {
        composable(route = ScreenRoute.SplashScreen.name) {
            SplashScreen(onDoneSplash = {
                navigateToHomeScreen(mainNavController = mainNavController)
            })
        }
        composable(route = ScreenRoute.SearchScreen.name) {
            SearchScreen(mainNavHostController = mainNavController)
        }
        composable(route = ScreenRoute.HomeScreen.name) {}
    }
}

@Composable
fun BottomNavigationGraph(onClickSearch: () -> Unit) {
    val bottomNavController = rememberNavController()
    NavHost(navController = bottomNavController, startDestination = BottomNavItem.Home.screenRoute) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen(onClickSearchButton = {
                onClickSearch.invoke()
            })
        }
        composable(BottomNavItem.Favorite.screenRoute) {
            FavoriteScreen()
        }
    }
    BottomNavigationBar(navController = bottomNavController)
}

@SuppressLint("ResourceType")
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(BottomNavItem.Home, BottomNavItem.Favorite)
    NavigationBar(
        containerColor = Color.DarkGray,
        contentColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navItems.forEach { _item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                selected = currentRoute == _item.screenRoute,
                onClick = {
                    navController.navigate(_item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = _item.icon),
                        contentDescription = stringResource(
                            id = _item.title,
                        ),
                        modifier = Modifier.size(26.dp),
                    )
                },
                label = { Text(stringResource(id = _item.title), fontSize = 9.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.LightGray,
                    selectedTextColor = Color.White,
                    unselectedTextColor = Color.LightGray,
                    indicatorColor = Color.LightGray,
                ),
            )
        }
    }
}
