package com.coooldoggy.semasearch.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.ui.common.AppBarWithText

@SuppressLint("ResourceType")
@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AppBarWithText(textId = R.string.favorite_screen)
    }
}
