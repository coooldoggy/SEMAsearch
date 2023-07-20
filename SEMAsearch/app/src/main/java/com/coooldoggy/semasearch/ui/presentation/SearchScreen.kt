package com.coooldoggy.semasearch.ui.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.coooldoggy.semasearch.R
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

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarAppBar(onBackClickListener = {
            mainNavHostController.navigateUp()
        })
        Box(modifier = Modifier.padding(10.dp)){
            SearchBar(onDoneClickListener = { _query ->
                searchViewModel.sendEvent(SearchScreenContract.Event.OnClickSearch(_query))
            }, onDeleteClickListener = {
                searchViewModel.sendEvent(SearchScreenContract.Event.OnDeleteClick)
            }, query = state.value.searchQuery)
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
