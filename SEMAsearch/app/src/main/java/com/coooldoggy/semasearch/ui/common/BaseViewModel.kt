package com.coooldoggy.semasearch.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coooldoggy.semasearch.base.ResultData
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState

    private val _errorState = MutableLiveData<ResultData.Error?>()
    val errorState: LiveData<ResultData.Error?>
        get() = _errorState

    private var enableMoreToLoad: Boolean = true

    open fun enableMoreToLoad(enableMoreToLoad: Boolean) {
        this.enableMoreToLoad = enableMoreToLoad
    }

    protected var activeFlowJob: Job = SupervisorJob(viewModelScope.coroutineContext[Job])

    protected fun cancelActiveFlowJob() {
        activeFlowJob.cancel()
        activeFlowJob = SupervisorJob(viewModelScope.coroutineContext[Job])
    }

    open fun loadMore() {
        if (loadingState.value != true) {
            loadData()
        }
    }

    protected abstract fun loadData()

    fun setErrorState(data: ResultData.Error) {
        _errorState.value = data
    }

    fun <T> Flow<T>.loading(): Flow<T> {
        return onStart { _loadingState.value = true }
            .onCompletion { _loadingState.value = false }
    }

    open fun isEmpty(totalItemsCount: Int): Boolean {
        return totalItemsCount == 0
    }

    override fun onCleared() {
        activeFlowJob.cancel()
        super.onCleared()
    }
}

interface BaseUiState // data class
interface BaseUiEvent
