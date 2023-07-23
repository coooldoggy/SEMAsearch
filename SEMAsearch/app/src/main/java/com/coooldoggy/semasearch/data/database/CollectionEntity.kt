package com.coooldoggy.semasearch.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true) val itemId: Int = 0,
    @ColumnInfo(name = "prdctClNm") val prdctClNm: String,
    @ColumnInfo(name = "manageNoYear") val manageNoYear: String,
    @ColumnInfo(name = "prdctNmKorean") val prdctNmKorean: String,
    @ColumnInfo(name = "prdctNmEng") val prdctNmEng: String,
    @ColumnInfo(name = "prdctStndrd") val prdctStndrd: String,
    @ColumnInfo(name = "mnfctYear") val mnfctYear: String,
    @ColumnInfo(name = "matrlTechnic") val matrlTechnic: String,
    @ColumnInfo(name = "prdctDetail") val prdctDetail: String,
    @ColumnInfo(name = "writrNm") val writrNm: String,
    @ColumnInfo(name = "mainImage") val mainImage: String,
    @ColumnInfo(name = "thumbImage") val thumbImage: String,
)
