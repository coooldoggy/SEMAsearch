package com.coooldoggy.semasearch.repository

import com.coooldoggy.semasearch.data.database.CollectionEntity
import com.coooldoggy.semasearch.data.database.FavoriteCollectionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DataBaseRepository @Inject constructor(private val favoriteCollectionDao: FavoriteCollectionDao) {
    suspend fun addToFavorite(data: CollectionEntity) = favoriteCollectionDao.insertFavorite(data)

    fun getAllFavorites(): Flow<List<CollectionEntity>> = favoriteCollectionDao.getAllFavorites().flowOn(Dispatchers.IO).conflate()

    suspend fun deleteFavorite(productName: String) = favoriteCollectionDao.deleteFavorite(productName)

    suspend fun isRowExist(productName: String) = favoriteCollectionDao.isRowIsExist(productName)
}