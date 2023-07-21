package com.coooldoggy.semasearch.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.coooldoggy.semasearch.domain.CollectionInfo
import com.coooldoggy.semasearch.ui.common.AppBar
import com.coooldoggy.semasearch.ui.common.AppBarBackIcon

@Composable
fun DetailScreen() {
}

@Composable
fun DetailBarAppBar(onBackClickListener: () -> Unit, title: String) {
    AppBar(leftContent = {
        AppBarBackIcon(action = { onBackClickListener.invoke() })
    }, centerContent = {
        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
    }, rightContent = {
        // TODO STAR
    })
}

@Composable
fun DetailContent(data: CollectionInfo) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

    }
}
