package com.coooldoggy.semasearch.ui.common

import com.coooldoggy.imagesearchcompose.ui.common.ErrorData

class BaseUiContract {

    enum class UiState {
        PROGRESS,
        ERROR,
        COMPLETE,
        NONE,
    }

    data class BaseUiLoadingState(
        val uiState: UiState,
        val apiError: ErrorData,
    ) : BaseUiState

    sealed class BaseUiLoadingEvent : BaseUiEvent {
        object LoadMore : BaseUiLoadingEvent()
        object Progress : BaseUiLoadingEvent()
        object Complete : BaseUiLoadingEvent()
        data class Error(val errorMsg: String) : BaseUiLoadingEvent()
    }
}
