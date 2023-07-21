package com.coooldoggy.semasearch.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.domain.Collection
import com.coooldoggy.semasearch.ui.common.AppBar
import com.coooldoggy.semasearch.ui.common.AppBarBackIcon
import com.coooldoggy.semasearch.ui.common.BasicTextField
import com.coooldoggy.semasearch.ui.presentation.contract.SearchScreenContract
import com.coooldoggy.semasearch.ui.presentation.viewmodel.SearchViewModel
import com.coooldoggy.semasearch.util.composableActivityViewModel
import com.coooldoggy.semasearch.util.onClick
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(mainNavHostController: NavHostController) {
    val searchViewModel = composableActivityViewModel<SearchViewModel>()
    val state = searchViewModel.state.collectAsState()
    val commonState = searchViewModel.commonState.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(modifier = Modifier.align(Alignment.TopStart)) {
            Column {
                SearchBarAppBar(onBackClickListener = {
                    mainNavHostController.navigateUp()
                })
                SearchBar(onDoneClickListener = { _query ->
                    searchViewModel.sendEvent(SearchScreenContract.Event.OnClickSearch(_query))
                }, onDeleteClickListener = {
                    searchViewModel.sendEvent(SearchScreenContract.Event.OnDeleteClick)
                }, query = state.value.searchQuery)
            }
        }
        // TODO ProgressBar & message
        CollectionSearchButton(
            onClickSearchButton = {},
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
fun SearchResult(){

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionSearchButton(onClickSearchButton: () -> Unit, modifier: Modifier) {
    Card(
        onClick = { onClickSearchButton.invoke() },
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier.height(40.dp).padding(horizontal = 15.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "검색하기",
                fontSize = 20.sp,
                color = Color.White,
            )
        }
    }
}

@Composable
fun SearchBarAppBar(onBackClickListener: () -> Unit) {
    AppBar(leftContent = {
        AppBarBackIcon(action = { onBackClickListener.invoke() })
    })
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    query: String,
    onDoneClickListener: (String) -> Unit,
    onDeleteClickListener: () -> Unit,
) {
    var isVisibleCancel by remember {
        mutableStateOf(false)
    }

    var inputText by remember {
        mutableStateOf(query)
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = inputText) {
        isVisibleCancel = inputText.isNotEmpty()
    }

    BasicTextField(
        value = inputText,
        valueChangeListener = { _newText ->
            inputText = _newText
        },
        keyboardActions = KeyboardActions(
            onDone = {
                coroutineScope.launch {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
                onDoneClickListener.invoke(inputText)
            },
        ),
        leftContent = {
            Image(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = "검색",
                modifier = Modifier.padding(10.dp),
            )
        },
        rightContent = {
            AnimatedVisibility(
                visible = isVisibleCancel,
                enter = slideInVertically(),
                exit = slideOutVertically(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_cancel_24),
                    contentDescription = "취소",
                    modifier = Modifier
                        .padding(10.dp)
                        .onClick {
                            inputText = ""
                            onDeleteClickListener.invoke()
                        },
                )
            }
        },
    )
}

@Composable
fun Collection(data: Collection) {
    Row(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(model = data.thumbImage, contentDescription = "")
        Column(modifier = Modifier.weight(1f)) {
            Text(text = data.prdctNmKorean, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = "${data.writrNm}(${data.mnfctYear})", fontSize = 13.sp)
            Text(text = data.prdctClNm, fontSize = 13.sp)
        }
    }
}
