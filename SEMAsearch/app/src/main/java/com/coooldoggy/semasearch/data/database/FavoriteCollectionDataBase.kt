package com.coooldoggy.semasearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CollectionEntity::class], version = 1)
abstract class FavoriteCollectionDataBase : RoomDatabase() {
    abstract fun favoriteCollectionDao(): FavoriteCollectionDao
}