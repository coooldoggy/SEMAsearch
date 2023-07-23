package com.coooldoggy.semasearch.domain

import com.coooldoggy.semasearch.data.database.CollectionEntity
import com.coooldoggy.semasearch.domain.Collection

fun Collection.toCollectionEntity(): CollectionEntity {
    return CollectionEntity(
        prdctClNm = prdctClNm,
        manageNoYear = manageNoYear,
        prdctNmKorean = prdctNmKorean,
        prdctNmEng = prdctNmEng,
        prdctStndrd = prdctStndrd,
        mnfctYear = mnfctYear,
        matrlTechnic = matrlTechnic,
        prdctDetail = prdctDetail,
        writrNm = writrNm,
        mainImage = mainImage,
        thumbImage = thumbImage
    )
}

fun CollectionEntity.toCollection(): Collection {
    return Collection(
        prdctClNm = prdctClNm,
        manageNoYear = manageNoYear,
        prdctNmKorean = prdctNmKorean,
        prdctNmEng = prdctNmEng,
        prdctStndrd = prdctStndrd,
        mnfctYear = mnfctYear,
        matrlTechnic = matrlTechnic,
        prdctDetail = prdctDetail,
        writrNm = writrNm,
        mainImage = mainImage,
        thumbImage = thumbImage
    )
}