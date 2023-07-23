package com.coooldoggy.semasearch.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.coooldoggy.imagesearchcompose.ui.common.ErrorData
import com.coooldoggy.semasearch.R
import com.coooldoggy.semasearch.base.ResultData
import com.coooldoggy.semasearch.domain.Collection
import com.coooldoggy.semasearch.ui.common.AppBar
import com.coooldoggy.semasearch.ui.common.AppBarBackIcon
import com.coooldoggy.semasearch.ui.common.BasicTextField
import com.coooldoggy.semasearch.ui.common.ErrorItem
import com.coooldoggy.semasearch.ui.common.ProgressBarItem
import com.coooldoggy.semasearch.ui.presentation.viewmodel.SearchViewModel
import com.coooldoggy.semasearch.util.onClick
import com.coooldoggy.semasearch.util.recomposeHighlighter
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    mainNavHostController: NavHostController,
    navigateToDetailScreenListener: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val state = searchViewModel.state.collectAsState()
    val loadingState = searchViewModel.loadingState.observeAsState().value ?: false
    val errorState = searchViewModel.errorState.observeAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
    ) {
        Box(modifier = Modifier.align(Alignment.TopStart)) {
            Column {
                SearchBarAppBar(onBackClickListener = {
                    mainNavHostController.navigateUp()
                })
                SearchBar(onDoneClickListener = { _query ->
                    updateQuery(query = _query, searchViewModel = searchViewModel)
                    onClickSearch(searchViewModel = searchViewModel)
                }, onDeleteClickListener = {
                    updateQuery(query = "", searchViewModel = searchViewModel)
                }, query = state.value.searchQuery, onNewInput = { _query ->
                    updateQuery(query = _query, searchViewModel = searchViewModel)
                })
                SearchResult(
                    data = state.value.searchResult,
                    loadMore = { loadMore(searchViewModel = searchViewModel) },
                    loadingState = loadingState,
                    errorState = errorState,
                    onClickCollection = { _collection ->
                        goToDetail(
                            navHostController = mainNavHostController,
                            collection = _collection,
                            navigateToDetailScreenListener = { navigateToDetailScreenListener.invoke() },
                        )
                    },
                )
            }
        }
        if (state.value.searchResult.isEmpty()) {
            CollectionSearchButton(
                onClickSearchButton = { onClickSearch(searchViewModel = searchViewModel) },
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@Composable
fun LazyGridState.OnBottomReached(
    loadMore: () -> Unit,
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .collect {
                if (it) loadMore()
            }
    }
}

private fun onClickSearch(searchViewModel: SearchViewModel) {
    searchViewModel.searchCollection()
}

private fun loadMore(searchViewModel: SearchViewModel) {
    searchViewModel.loadMore()
}

private fun updateQuery(query: String, searchViewModel: SearchViewModel) {
    searchViewModel.updateQuery(query)
}

fun goToDetail(
    collection: Collection,
    navigateToDetailScreenListener: () -> Unit,
    navHostController: NavHostController,
) {
    navHostController.currentBackStackEntry?.savedStateHandle?.apply {
        set("collection", collection)
    }
    navigateToDetailScreenListener.invoke()
}

@Composable
fun SearchResult(
    data: List<Collection>,
    loadMore: () -> Unit,
    loadingState: Boolean,
    errorState: ResultData.Error? = null,
    onClickCollection: (Collection) -> Unit,
) {
    val gridState = rememberLazyGridState()

    if (data.isNotEmpty()) {
        gridState.OnBottomReached {
            loadMore.invoke()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        modifier = Modifier
            .fillMaxWidth()
            .recomposeHighlighter(),
        contentPadding = PaddingValues(10.dp),
    ) {
        if (errorState != null) {
            item {
                ErrorItem(
                    errorItemData = ErrorData(
                        title = errorState.getMessage(),
                    ),
                )
            }
        } else {
            data.onEach { _collection ->
                item {
                    Collection(
                        data = _collection,
                        onClickCollection = { onClickCollection.invoke(_collection) },
                    )
                }
            }
            if (loadingState) {
                item {
                    ProgressBarItem()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun CollectionSearchButton(onClickSearchButton: () -> Unit, modifier: Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    Card(
        onClick = {
            onClickSearchButton.invoke()
            coroutineScope.launch {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        },
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = modifier
            .height(40.dp)
            .padding(horizontal = 15.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
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
    onNewInput: (String) -> Unit,
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
            onNewInput.invoke(inputText)
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
fun Collection(data: Collection, onClickCollection: (Collection) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().onClick {
            onClickCollection.invoke(data)
        },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = data.thumbImage,
            contentDescription = "",
            modifier = Modifier
                .size(80.dp)
                .padding(10.dp),
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = data.prdctNmKorean,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = "${data.writrNm}(${data.mnfctYear})",
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(text = data.prdctClNm, fontSize = 13.sp)
        }
    }
}
