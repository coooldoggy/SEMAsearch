package com.coooldoggy.semasearch.repository

import com.coooldoggy.semasearch.data.network.SEMAServiceImpl
import com.coooldoggy.semasearch.domain.CollectionInfo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

@ViewModelScoped
class NetworkRepository @Inject constructor(
    private val semaServiceImpl: SEMAServiceImpl,
) {
    suspend fun queryCollection(
        startIdx: Int,
        productName: String,
    ): Flow<Response<CollectionInfo>> = semaServiceImpl.queryCollection(
        startIdx = startIdx,
        productName = productName,
    )
}
