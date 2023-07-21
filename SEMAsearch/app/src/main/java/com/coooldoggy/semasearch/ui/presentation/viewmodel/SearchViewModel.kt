package com.coooldoggy.semasearch.ui.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.coooldoggy.semasearch.repository.NetworkRepository
import com.coooldoggy.semasearch.ui.common.BaseMVIViewModel
import com.coooldoggy.semasearch.ui.common.BaseUiEvent
import com.coooldoggy.semasearch.ui.presentation.contract.SearchScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO MVVVM으로 변환하기
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
                queryCollection()
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
//        if (state.value.searchQuery.isNotEmpty()) {
//            queryCollection()
//        }
    }

    private fun queryCollection(){
        viewModelScope.launch {
            networkRepository.queryCollection(startIdx = 0, productName = state.value.searchQuery).collectLatest { result ->
                if (result.isSuccessful){
                    Log.d("!!!!!!!!!", "${result.body()}")
                }
            }
        }
    }
}
