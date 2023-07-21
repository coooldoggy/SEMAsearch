package com.coooldoggy.semasearch.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.ui.common.AppBarWithText

@SuppressLint("ResourceType")
@Composable
fun HomeScreen(onClickSearchButton: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        AppBarWithText(textId = R.string.app_name)
        SearchButton(onClickSearchButton = { onClickSearchButton.invoke() }, modifier = Modifier.align(Alignment.Center))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchButton(onClickSearchButton: () -> Unit, modifier: Modifier) {
    Card(
        onClick = { onClickSearchButton.invoke() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier.size(height = 60.dp, width = 200.dp),
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = "소장품 검색")
            Text(text = "소장품 검색", fontSize = 15.sp, color = Color.Black, modifier = Modifier.padding(start = 10.dp))
        }
    }
}
