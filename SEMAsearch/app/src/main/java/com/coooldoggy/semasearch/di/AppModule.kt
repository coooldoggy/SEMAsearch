package com.coooldoggy.semasearch.di

import android.content.Context
import androidx.room.Room
import com.coooldoggy.semasearch.BuildConfig
import com.coooldoggy.semasearch.SEMA_URL
import com.coooldoggy.semasearch.data.database.FavoriteCollectionDao
import com.coooldoggy.semasearch.data.database.FavoriteCollectionDataBase
import com.coooldoggy.semasearch.data.network.SEMAService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SEMA_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): SEMAService {
        return retrofit.create(SEMAService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideFavoriteDao(favoriteCollectionDataBase: FavoriteCollectionDataBase): FavoriteCollectionDao = favoriteCollectionDataBase.favoriteCollectionDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): FavoriteCollectionDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = FavoriteCollectionDataBase::class.java,
            name = "favorite_collection_db",
        ).fallbackToDestructiveMigration().build()
    }
}