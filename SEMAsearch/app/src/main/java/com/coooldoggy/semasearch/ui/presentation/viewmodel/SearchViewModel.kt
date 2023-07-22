package com.coooldoggy.semasearch.ui.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.coooldoggy.semasearch.base.ResultData
import com.coooldoggy.semasearch.repository.NetworkRepository
import com.coooldoggy.semasearch.ui.common.BaseViewModel
import com.coooldoggy.semasearch.ui.presentation.contract.SearchScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(
        SearchScreenContract.State(
            searchQuery = "",
            searchResult = listOf(),
        ),
    )
    val state = _state
    private val totalPageCount = MutableLiveData(0)
    val _totalPageCount = totalPageCount
    private val startIndex = AtomicInteger(0)

    fun updateQuery(query: String) {
        state.update {
            it.copy(
                searchQuery = query,
            )
        }
        if (query.isEmpty()) {
            clearData()
        }
    }

    private fun clearData() {
        _totalPageCount.value = 0
        state.update {
            it.copy(
                searchResult = listOf(),
            )
        }
    }

    fun searchCollection() {
        if (state.value.searchQuery.isNotEmpty()) {
            queryCollection()
        }
    }

    private fun queryCollection() {
        viewModelScope.launch {
            networkRepository.queryCollection(startIdx = startIndex.get(), productName = state.value.searchQuery).loading().collectLatest { result ->
                when (result) {
                    is ResultData.Success -> {
                        if (state.value.searchResult.isEmpty()) {
                            _totalPageCount.value = result.data?.SemaPsgudInfoKorInfo?.totalCount ?: 0
                            state.update {
                                it.copy(
                                    searchResult = result.data?.SemaPsgudInfoKorInfo?.collectionList ?: emptyList(),
                                )
                            }
                        } else {
                            val original = state.value.searchResult
                            val searchResult = result.data?.SemaPsgudInfoKorInfo?.collectionList ?: emptyList()
                            state.value.searchResult = original + searchResult
                        }
                        startIndex.getAndAdd(result.data?.SemaPsgudInfoKorInfo?.collectionList?.size?.minus(1) ?: 0)
                        enableMoreToLoad(
                            (totalPageCount.value ?: 0) > state.value.searchResult.size,
                        )
                    }
                    is ResultData.Error -> {
                        enableMoreToLoad(false)
                        setErrorState(data = result)
                    }
                }
            }
        }
    }

    override fun loadData() {
        searchCollection()
    }
}
