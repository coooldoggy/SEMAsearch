package com.coooldoggy.semasearch.ui.presentation.contract

import com.coooldoggy.semasearch.ui.common.BaseUiEvent
import com.coooldoggy.semasearch.ui.common.BaseUiState

class SearchScreenContract {
    data class State(
        val searchQuery: String,
    ) : BaseUiState

    sealed class Event : BaseUiEvent {
        data class OnClickSearch(val query: String) : Event()
        object OnDeleteClick : Event()
    }
}