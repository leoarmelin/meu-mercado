package com.leoarmelin.meumercado.dagger

import com.leoarmelin.database.CategoryDao
import com.leoarmelin.database.ProductDao
import com.leoarmelin.meumercado.repository.RoomRepository
import com.leoarmelin.meumercado.repository.ScrapperRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRoomRepository(productDao: ProductDao, categoryDao: CategoryDao): RoomRepository =
        RoomRepository(productDao, categoryDao)

    @Provides
    @Singleton
    fun provideScrapperRepository(): ScrapperRepository = ScrapperRepository()
}