package com.coooldoggy.semasearch.ui.common

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import com.coooldoggy.semasearch.FAVORITE_VIEW
import com.coooldoggy.semasearch.HOME_VIEW
import com.coooldoggy.semasearch.R

sealed class BottomNavItem(
    @IdRes val title: Int,
    @IdRes val icon: Int,
    val screenRoute: String,
) {
    @SuppressLint("ResourceType")
    object Home : BottomNavItem(title = R.string.home_screen, icon = R.drawable.baseline_home_24, screenRoute = HOME_VIEW)

    @SuppressLint("ResourceType")
    object Favorite : BottomNavItem(title = R.string.favorite_screen, icon = R.drawable.baseline_star_border_24, screenRoute = FAVORITE_VIEW)
}

enum class ScreenRoute {
    SplashScreen,
    HomeScreen,
    SearchScreen,
    FavoriteScreen
    ;

    companion object {
        fun fromRoute(route: String?): String =
            when (route?.substringBefore("/")) {
                SplashScreen.name -> SplashScreen.name
                HomeScreen.name -> HomeScreen.name
                SearchScreen.name -> SearchScreen.name
                BottomNavItem.Home.screenRoute -> HomeScreen.name
                null -> SearchScreen.name
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
