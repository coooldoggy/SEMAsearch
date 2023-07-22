package com.coooldoggy.semasearch.data.network

import com.coooldoggy.semasearch.Application
import com.coooldoggy.semasearch.COMMON_PAGE_COUNT
import com.coooldoggy.semasearch.R
import retrofit2.Response
import javax.inject.Inject

class SEMAServiceImpl @Inject constructor(private val semaService: SEMAService) {
    suspend fun queryCollection(startIdx: Int, productName: String): Response<String> =
        semaService.queryCollection(
            key = Application.getContext()?.getString(R.string.sema_api_key) ?: "",
            startIdx = startIdx,
            endIdx = startIdx + COMMON_PAGE_COUNT,
            productName = productName,
        )
}

