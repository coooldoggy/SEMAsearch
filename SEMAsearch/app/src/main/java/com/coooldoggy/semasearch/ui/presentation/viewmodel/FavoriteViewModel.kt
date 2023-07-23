package com.coooldoggy.semasearch.ui.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.coooldoggy.semasearch.domain.Collection
import com.coooldoggy.semasearch.domain.toCollectionEntity
import com.coooldoggy.semasearch.repository.DataBaseRepository
import com.coooldoggy.semasearch.ui.common.BaseViewModel
import com.coooldoggy.semasearch.ui.presentation.contract.FavoriteScreenContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        FavoriteScreenContract.State(
            favoriteList = emptyList(),
            favoriteChangedState = Pair(null, false),
        ),
    )
    val state = _state.asStateFlow()

    init {
        enableMoreToLoad(false)
        fetchFavoriteList()
    }

    private fun fetchFavoriteList() {
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseRepository.getAllFavorites().distinctUntilChanged().collect { _favoritesInDB ->
                _state.update {
                    it.copy(
                        favoriteList = _favoritesInDB,
                    )
                }
            }
        }
    }

    fun onClickFavorite(data: Collection) {
        val isInFavorite = state.value.favoriteList.find { it.thumbImage == data.toCollectionEntity().thumbImage } == null
        if (isInFavorite) {
            addToFavorite(data)
        } else {
            removeFromFavorite(data)
        }
        _state.update {
            it.copy(
                favoriteChangedState = Pair(data, isInFavorite),
            )
        }
    }

    private fun addToFavorite(data: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseRepository.addToFavorite(data.toCollectionEntity())
        }
    }

    private fun removeFromFavorite(data: Collection) {
        viewModelScope.launch(Dispatchers.IO) {
            dataBaseRepository.deleteFavorite(data.thumbImage)
        }
    }

    override fun loadData() {
    }
}
