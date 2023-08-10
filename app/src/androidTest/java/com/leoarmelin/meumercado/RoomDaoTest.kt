package com.leoarmelin.meumercado

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leoarmelin.database.AppDatabase
import com.leoarmelin.database.CategoryDao
import com.leoarmelin.database.ProductDao
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product
import com.leoarmelin.sharedmodels.Unity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomDaoTest {
    private lateinit var categoryDao: CategoryDao
    private lateinit var productDao: ProductDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        productDao = db.productDao()
        categoryDao = db.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCategory() = runTest {
        insertMockCategoryOn(categoryDao)
        val roomCategory = categoryDao.getCategoryById(mockCategory.id).first()

        assertEquals(mockCategory, roomCategory)
    }

    @Test
    @Throws(Exception::class)
    fun removeCategory() = runTest {
        insertMockCategoryOn(categoryDao)
        insertMockProductOn(productDao)

        categoryDao.deleteCategoryById(mockCategory.id)

        val roomCategory = categoryDao.getCategoryById(mockCategory.id).first()
        val roomProduct = productDao.getProductById(mockProduct.id).first()

        assertNull(roomCategory)
        assertNotNull(roomProduct)
        assertNull(roomProduct?.categoryId)
    }

    @Test
    @Throws(Exception::class)
    fun updateCategory() = runTest {
        insertMockCategoryOn(categoryDao)

        categoryDao.updateCategory(mockCategory.copy(name = "Category Cool"))
        val roomCategory = categoryDao.getCategoryById(mockCategory.id).first()

        assertEquals(roomCategory?.name, "Category Cool")
    }

    @Test
    @Throws(Exception::class)
    fun insertProduct() = runTest {
        insertMockCategoryOn(categoryDao)
        insertMockProductOn(productDao)

        val roomProduct = productDao.getProductById(mockProduct.id).first()

        assertEquals(mockProduct, roomProduct)
    }

    @Test
    @Throws(Exception::class)
    fun removeProduct() = runTest {
        insertMockCategoryOn(categoryDao)
        insertMockProductOn(productDao)

        productDao.deleteProductById(mockProduct.id)

        val roomProduct = productDao.getProductById(mockProduct.id).first()

        assertNull(roomProduct)
    }

    @Test
    @Throws(Exception::class)
    fun fetchProducts() = runTest {
        insertMockCategoryOn(categoryDao)
        insertMockProductsOn(productDao)

        val roomProducts = productDao.fetchProductsFromCategory(mockCategory.id).first()

        assertTrue(roomProducts.containsAll(mockProducts))
        assertTrue(mockProducts.containsAll(roomProducts))
    }

    @Test
    @Throws(Exception::class)
    fun updateProduct() = runTest {
        insertMockCategoryOn(categoryDao)
        insertMockProductOn(productDao)

        productDao.updateProduct(mockProduct.copy(name = "Product Cool"))
        val roomProduct = productDao.getProductById(mockProduct.id).first()

        assertEquals(roomProduct?.id, mockProduct.id)
        assertEquals(roomProduct?.name, "Product Cool")
    }

    @Test
    @Throws(Exception::class)
    fun getTotalProductAmount() = runTest {
        insertMockCategoryOn(categoryDao)
        insertMockProductsOn(productDao)

        val totalAmount = productDao.getTotalAmountFromCategoryId(mockCategory.id).first()
        val expectedAmount = mockProducts.sumOf { it.totalPrice }

        assertTrue(totalAmount == expectedAmount)
    }

    companion object {
        val mockCategory = Category(
            id = "c-1",
            name = "Category One"
        )
        val mockProduct = Product(
            id = "p-1",
            name = "Product One",
            unity = Unity.UN,
            amount = 1.0,
            unityPrice = 5.0,
            totalPrice = 5.0,
            issueAt = "2023-08-10T13:16:30.0000Z",
            categoryId = "c-1",
        )
        val mockProducts = listOf(
            mockProduct,
            mockProduct.copy(id = "p-2", name = "Product Two"),
            mockProduct.copy(id = "p-3", name = "Product Three")
        )
        suspend fun insertMockCategoryOn(categoryDao: CategoryDao) {
            categoryDao.insertCategory(mockCategory)
        }

        suspend fun insertMockProductOn(productDao: ProductDao) {
            productDao.insertProduct(mockProduct)
        }

        suspend fun insertMockProductsOn(productDao: ProductDao) {
            mockProducts.forEach {
                productDao.insertProduct(it)
            }
        }
    }
}