package com.coooldoggy.semasearch.ui.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.domain.Collection
import com.coooldoggy.semasearch.domain.toCollectionEntity
import com.coooldoggy.semasearch.ui.common.AppBar
import com.coooldoggy.semasearch.ui.common.AppBarBackIcon
import com.coooldoggy.semasearch.ui.presentation.viewmodel.FavoriteViewModel
import com.coooldoggy.semasearch.util.onClick

@Composable
fun DetailScreen(
    mainNavHostController: NavHostController,
    favoriteViewModel: FavoriteViewModel,
) {
    val favoriteState = favoriteViewModel.state.collectAsState()
    val listState = rememberLazyListState()

    val collectionData =
        mainNavHostController.previousBackStackEntry?.savedStateHandle?.get<Collection>(
            "collection",
        )

    val isInFavorite = favoriteState.value.favoriteList.find { it.thumbImage == collectionData?.toCollectionEntity()?.thumbImage } != null
    val isFavoriteStatus = remember {
        mutableStateOf(isInFavorite)
    }

    LaunchedEffect(favoriteState.value.favoriteChangedState) {
        val thumbImage = favoriteState.value.favoriteChangedState.first?.thumbImage
        val isFavorite = favoriteState.value.favoriteChangedState.second

        if (collectionData?.thumbImage == thumbImage) {
            isFavoriteStatus.value = isFavorite
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.White), state = listState) {
        item {
            DetailBarAppBar(
                onBackClickListener = {
                    onBackClickListener(mainNavHostController = mainNavHostController)
                },
                title = collectionData?.prdctNmKorean ?: "",
                onClickStarListener = {
                    onClickStarListener(
                        favoriteViewModel = favoriteViewModel,
                        collection = collectionData,
                    )
                },
                isFavorite = isFavoriteStatus.value,
            )
        }
        item {
            if (collectionData != null) {
                DetailContent(collectionData)
            }
        }
    }
}

private fun onClickStarListener(favoriteViewModel: FavoriteViewModel, collection: Collection?) {
    collection?.let {
        favoriteViewModel.onClickFavorite(it)
    }
}

private fun onBackClickListener(mainNavHostController: NavHostController) {
    mainNavHostController.navigateUp()
}

@Composable
fun DetailBarAppBar(
    onBackClickListener: () -> Unit,
    onClickStarListener: () -> Unit,
    isFavorite: Boolean,
    title: String,
) {
    AppBar(leftContent = {
        AppBarBackIcon(action = { onBackClickListener.invoke() })
    }, centerContent = {
        val cutTitle = if (title.length > 12) title.substring(0, 12) + "..." else title
        Text(text = cutTitle, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color.Black)
    }, rightContent = {
        Image(
            modifier = Modifier.padding(end = 10.dp).onClick {
                onClickStarListener.invoke()
            },
            painter = if (isFavorite) {
                painterResource(id = R.drawable.baseline_star_rate_24_yellow)
            } else {
                painterResource(
                    id = R.drawable.baseline_star_border_24_yellow,
                )
            },
            contentDescription = "",
        )
    })
}

@Composable
fun DetailContent(data: Collection) {
    Column(
        modifier = Modifier.fillMaxHeight().background(Color.White).padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        AsyncImage(
            model = data.mainImage,
            contentDescription = data.prdctNmKorean,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = data.prdctNmKorean,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(horizontal = 10.dp),
        )
        Text(
            text = data.prdctNmEng,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            modifier = Modifier.padding(horizontal = 10.dp),
        )
        DetailRow(title = "작가명", content = data.writrNm)
        DetailRow(title = "제작년도", content = data.mnfctYear)
        DetailRow(title = "부문", content = data.prdctClNm)
        DetailRow(title = "규격", content = data.prdctStndrd)
        DetailRow(title = "수집년도", content = data.manageNoYear)
        DetailRow(title = "재료 및 기법", content = data.matrlTechnic)
    }
}

@Composable
fun DetailRow(title: String, content: String) {
    Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)) {
        Text(
            text = title,
            color = Color.LightGray,
            fontSize = 15.sp,
            modifier = Modifier.align(
                Alignment.CenterStart,
            ),
        )
        Text(
            text = content,
            color = Color.LightGray,
            fontSize = 15.sp,
            modifier = Modifier.align(
                Alignment.CenterEnd,
            ),
        )
    }
}
