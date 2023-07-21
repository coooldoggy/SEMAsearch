package com.coooldoggy.semasearch.ui.presentation.viewmodel

import com.coooldoggy.semasearch.repository.NetworkRepository
import com.coooldoggy.semasearch.ui.common.BaseMVIViewModel
import com.coooldoggy.semasearch.ui.common.BaseUiEvent
import com.coooldoggy.semasearch.ui.presentation.contract.SearchScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
) : BaseMVIViewModel() {
    private val _state = MutableStateFlow(
        SearchScreenContract.State(
            searchQuery = "",
        ),
    )
    val state = _state.asStateFlow()

    override fun handleEvents(event: BaseUiEvent) {
        when (event) {
            is SearchScreenContract.Event.OnClickSearch -> {
                _state.update {
                    it.copy(
                        searchQuery = event.query,
                    )
                }
            }
            is SearchScreenContract.Event.OnDeleteClick -> {
                _state.update {
                    it.copy(
                        searchQuery = "",
                    )
                }
            }
        }
    }

    override fun loadData() {
    }
}
