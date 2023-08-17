package com.leoarmelin.meumercado.repository

import com.leoarmelin.database.CategoryDao
import com.leoarmelin.database.ProductDao
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
) {
    val observeAllCategories: Flow<List<Category>> = categoryDao.fetchAllCategories()
    val observeAllProducts: Flow<List<Product>> = productDao.fetchAllProducts()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategoryById(id: String) {
        categoryDao.deleteCategoryById(id)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    fun fetchProductsFromCategory(
        categoryId: String?,
        dateInterval: Pair<LocalDateTime, LocalDateTime>
    ) = productDao.fetchProductsFromCategory(categoryId, dateInterval.first, dateInterval.second)

    fun fetchProductsWithoutCategory(
        dateInterval: Pair<LocalDateTime, LocalDateTime>
    ) = productDao.fetchProductsWithoutCategory(dateInterval.first, dateInterval.second)

    suspend fun deleteProductById(id: String) {
        productDao.deleteProductById(id)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    fun getTotalAmountFromCategoryId(
        categoryId: String,
        dateInterval: Pair<LocalDateTime, LocalDateTime>
    ) = productDao.getTotalAmountFromCategoryId(categoryId, dateInterval.first, dateInterval.second)

    fun getTotalAmountWithoutCategory(
        dateInterval: Pair<LocalDateTime, LocalDateTime>
    ) = productDao.getTotalAmountFromNoCategory(dateInterval.first, dateInterval.second)

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    fun getCategoryById(id: String) = categoryDao.getCategoryById(id)

    fun getProductById(id: String) = productDao.getProductById(id)
}