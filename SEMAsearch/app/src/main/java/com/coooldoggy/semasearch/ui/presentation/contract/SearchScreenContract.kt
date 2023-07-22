package com.coooldoggy.semasearch.ui.presentation.contract

import com.coooldoggy.semasearch.domain.Collection
import com.coooldoggy.semasearch.ui.common.BaseUiState

class SearchScreenContract {
    data class State(
        var searchQuery: String,
        var searchResult: List<Collection>
    ) : BaseUiState
}