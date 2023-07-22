package com.coooldoggy.semasearch.repository

import com.coooldoggy.semasearch.base.ResultData
import com.coooldoggy.semasearch.base.apiResultFlow
import com.coooldoggy.semasearch.data.network.SEMAServiceImpl
import com.coooldoggy.semasearch.domain.CollectionResult
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class NetworkRepository @Inject constructor(
    private val semaServiceImpl: SEMAServiceImpl,
) {
    suspend fun queryCollection(
        startIdx: Int,
        productName: String,
    ): Flow<ResultData<CollectionResult>> =
        flow {
            emit(
                apiResultFlow {
                    semaServiceImpl.queryCollection(
                        startIdx = startIdx,
                        productName = productName,
                    )
                },
            )
        }
}
