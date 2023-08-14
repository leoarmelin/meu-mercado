package com.leoarmelin.meumercado.repository

import com.leoarmelin.database.CategoryDao
import com.leoarmelin.database.ProductDao
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
) {
    val readAllCategories: Flow<List<Category>> = categoryDao.fetchAllCategories()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun deleteCategoryById(id: String) {
        categoryDao.deleteCategoryById(id)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    fun fetchProductsFromCategory(categoryId: String) =
        productDao.fetchProductsFromCategory(categoryId)

    suspend fun deleteProductById(id: String) {
        productDao.deleteProductById(id)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    fun getTotalAmountFromCategoryId(categoryId: String) =
        productDao.getTotalAmountFromCategoryId(categoryId)

    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }

    fun getCategoryById(id: String) = categoryDao.getCategoryById(id)
}