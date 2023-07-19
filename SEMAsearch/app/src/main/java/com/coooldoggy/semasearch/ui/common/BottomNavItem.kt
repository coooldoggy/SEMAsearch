package com.coooldoggy.semasearch.ui.common

import android.annotation.SuppressLint
import androidx.annotation.IdRes
import com.coooldoggy.semasearch.FAVORITE_VIEW
import com.coooldoggy.semasearch.HOME_VIEW
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.SEARCH_VIEW

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
