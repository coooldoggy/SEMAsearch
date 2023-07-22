package com.coooldoggy.semasearch.domain

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionResult(
    @SerializedName(value = "SemaPsgudInfoKorInfo")
    val SemaPsgudInfoKorInfo: CollectionInfo,
) : java.io.Serializable

@Serializable
data class Result(
    @SerializedName("CODE")
    val code: String,
    @SerializedName("MESSAGE")
    val msg: String,
) : java.io.Serializable

@Serializable
data class CollectionInfo(
    @SerializedName("list_total_count")
    val totalCount: Int,
    @SerializedName("RESULT")
    val result: Result,
    @SerializedName("row")
    val collectionList: List<Collection>,
) : java.io.Serializable

@Serializable
data class Collection(
    @SerializedName("prdct_cl_nm")
    val prdctClNm: String,
    @SerializedName("manage_no_year")
    val manageNoYear: String,
    @SerializedName("prdct_nm_korean")
    val prdctNmKorean: String,
    @SerializedName("prdct_nm_eng")
    val prdctNmEng: String,
    @SerializedName("prdct_stndrd")
    val prdctStndrd: String,
    @SerializedName("mnfct_year")
    val mnfctYear: String,
    @SerializedName("matrl_technic")
    val matrlTechnic: String,
    @SerializedName("prdct_detail")
    val prdctDetail: String,
    @SerializedName("writr_nm")
    val writrNm: String,
    @SerializedName("main_image")
    val mainImage: String,
    @SerializedName("thumb_image")
    val thumbImage: String,
) : java.io.Serializable
