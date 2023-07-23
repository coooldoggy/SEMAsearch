package com.coooldoggy.semasearch.ui.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.domain.toCollection
import com.coooldoggy.semasearch.ui.common.AppBarWithText
import com.coooldoggy.semasearch.ui.presentation.viewmodel.FavoriteViewModel

@SuppressLint("ResourceType")
@Composable
fun FavoriteScreen(
    mainNavHostController: NavHostController,
    navigateToDetailScreenListener: () -> Unit,
    favoriteViewModel: FavoriteViewModel,
) {
    val state = favoriteViewModel.state.collectAsState()
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
        AppBarWithText(textId = R.string.favorite_screen)
        Text(
            text = "소장품",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(start = 15.dp),
        )
        LazyColumn(state = listState) {
            state.value.favoriteList.onEach { _collection ->
                item {
                    Collection(
                        data = _collection.toCollection(),
                        onClickCollection = {
                            goToDetail(
                                navHostController = mainNavHostController,
                                collection = it,
                                navigateToDetailScreenListener = { navigateToDetailScreenListener.invoke() },
                            )
                        },
                    )
                }
            }
        }
    }
}
