package com.coooldoggy.semasearch.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SEMAService {
    @GET("{key}/json/SemaPsgudInfoKorInfo/{startIdx}/{endIdx}/{prdct_cl_nm}")
    suspend fun queryCollection(
        @Path("key")key: String,
        @Path("startIdx")startIdx: Int,
        @Path("endIdx")endIdx: Int,
        @Path("prdct_cl_nm")productName: String,
    ): Response<String>

    @GET("{key}/json/SemaPsgudInfoKorInfo/{startIdx}/{endIdx}/{prdct_cl_nm}/{manage_no_year}/{prodct_nm_korean}/{prodct_nm_eng}")
    suspend fun queryCollectionWithFilter(
        @Query("Authorization")key: String,
    )
}
