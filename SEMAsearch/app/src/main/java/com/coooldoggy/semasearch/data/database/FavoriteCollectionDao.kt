package com.coooldoggy.semasearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCollectionDao {
    @Query("SELECT * FROM CollectionEntity")
    fun getAllFavorites(): Flow<List<CollectionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: CollectionEntity)

    @Query("DELETE FROM CollectionEntity WHERE thumbImage=:thumbImage")
    suspend fun deleteFavorite(thumbImage: String)

    @Query("SELECT EXISTS(SELECT * FROM CollectionEntity WHERE thumbImage = :thumbImage)")
    suspend fun isRowIsExist(thumbImage: String): Boolean
}