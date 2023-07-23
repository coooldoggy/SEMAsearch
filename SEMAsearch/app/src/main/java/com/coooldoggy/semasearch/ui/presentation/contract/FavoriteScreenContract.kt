package com.coooldoggy.semasearch.ui.presentation.contract

import com.coooldoggy.semasearch.data.database.CollectionEntity
import com.coooldoggy.semasearch.domain.Collection
import com.coooldoggy.semasearch.ui.common.BaseUiState

class FavoriteScreenContract {

    data class State(
        val favoriteList: List<CollectionEntity>,
        val favoriteChangedState: Pair<Collection?, Boolean>,
    ) : BaseUiState
}
