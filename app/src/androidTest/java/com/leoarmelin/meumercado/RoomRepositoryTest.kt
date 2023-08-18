package com.leoarmelin.meumercado

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.leoarmelin.database.AppDatabase
import com.leoarmelin.meumercado.repository.RoomRepository
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
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class RoomRepositoryTest {
    private lateinit var db: AppDatabase
    private lateinit var roomRepository: RoomRepository

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        val productDao = db.productDao()
        val categoryDao = db.categoryDao()
        roomRepository = RoomRepository(productDao, categoryDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertCategory() = runTest {
        roomRepository.insertCategory(mockCategory)
        roomRepository.insertCategory(mockCategory)
        val roomCategory = roomRepository.getCategoryById(mockCategory.id).first()

        assertEquals(mockCategory, roomCategory)
    }

    @Test
    @Throws(Exception::class)
    fun removeCategory() = runTest {
        roomRepository.insertCategory(mockCategory)
        roomRepository.insertProduct(mockProduct)

        roomRepository.deleteCategoryById(mockCategory.id)

        val roomCategory = roomRepository.getCategoryById(mockCategory.id).first()
        val roomProduct = roomRepository.getProductById(mockProduct.id).first()

        assertNull(roomCategory)
        assertNotNull(roomProduct)
        assertNull(roomProduct?.categoryId)
    }

    @Test
    @Throws(Exception::class)
    fun updateCategory() = runTest {
        roomRepository.insertCategory(mockCategory)

        roomRepository.updateCategory(mockCategory.copy(name = "Category Cool"))
        val roomCategory = roomRepository.getCategoryById(mockCategory.id).first()

        assertEquals(roomCategory?.name, "Category Cool")
    }

    @Test
    @Throws(Exception::class)
    fun insertProduct() = runTest {
        roomRepository.insertCategory(mockCategory)
        roomRepository.insertProduct(mockProduct)

        val roomProduct = roomRepository.getProductById(mockProduct.id).first()

        assertEquals(mockProduct.id, roomProduct?.id)
    }

    @Test
    @Throws(Exception::class)
    fun removeProduct() = runTest {
        roomRepository.insertCategory(mockCategory)
        roomRepository.insertProduct(mockProduct)

        roomRepository.deleteProductById(mockProduct.id)

        val roomProduct = roomRepository.getProductById(mockProduct.id).first()

        assertNull(roomProduct)
    }

    @Test
    @Throws(Exception::class)
    fun fetchProducts() = runTest {
        roomRepository.insertCategory(mockCategory)
        mockProducts.forEach {
            roomRepository.insertProduct(it)
        }

        val roomProducts = roomRepository.fetchProductsFromCategory(
            mockCategory.id,
            dateInterval
        ).first()

        val mockProductsMap = mockProducts.map { it.id }
        val roomProductsMap = roomProducts.map { it.id }

        assertTrue(roomProductsMap.containsAll(mockProductsMap))
        assertTrue(mockProductsMap.containsAll(roomProductsMap))
    }

    @Test
    @Throws(Exception::class)
    fun updateProduct() = runTest {
        roomRepository.insertCategory(mockCategory)
        roomRepository.insertProduct(mockProduct)

        roomRepository.updateProduct(mockProduct.copy(name = "Product Cool"))
        val roomProduct = roomRepository.getProductById(mockProduct.id).first()

        assertEquals(roomProduct?.id, mockProduct.id)
        assertEquals(roomProduct?.name, "Product Cool")
    }

    @Test
    @Throws(Exception::class)
    fun getTotalProductAmount() = runTest {
        roomRepository.insertCategory(mockCategory)
        mockProducts.forEach {
            roomRepository.insertProduct(it)
        }

        val totalAmount = roomRepository.getTotalAmountFromCategoryId(
            mockCategory.id,
            dateInterval
        ).first()
        val expectedAmount = mockProducts.sumOf { it.totalPrice }

        assertTrue(totalAmount == expectedAmount)
    }

    @Test
    @Throws(Exception::class)
    fun getTotalProductAmountWithoutCategory() = runTest {
        roomRepository.insertCategory(mockCategory)
        mockProductsNoCategory.forEach {
            roomRepository.insertProduct(it)
        }

        val totalAmount = roomRepository.getTotalAmountWithoutCategory(
            dateInterval
        ).first()
        val expectedAmount = mockProductsNoCategory
            .filter { it.categoryId == null }
            .sumOf { it.totalPrice }

        assertTrue(totalAmount == expectedAmount)
    }

    @Test
    @Throws(Exception::class)
    fun fetchProductsOutsideOfDateRange() = runTest {
        roomRepository.insertCategory(mockCategory)
        mockProductsOutsideOfDateRange.forEach {
            roomRepository.insertProduct(it)
        }

        val roomProducts = roomRepository.fetchProductsFromCategory(
            mockCategory.id,
            dateInterval
        ).first()

        assertTrue(roomProducts.isEmpty())
    }

    companion object {
        private val dateNow = LocalDateTime.now()
        private val firstDayOfMonth = LocalDateTime.of(dateNow.year, dateNow.monthValue, 1, 0, 0, 1)
        private val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusSeconds(2)
        private val dateInterval = Pair(firstDayOfMonth, lastDayOfMonth)
        private val mockCategory = Category(
            id = "c-1",
            name = "Category One",
            emoji = "🍙"
        )
        private val mockProduct = Product(
            id = "p-1",
            name = "Product One",
            unity = Unity.UN,
            amount = 1.0,
            unityPrice = 5.0,
            totalPrice = 5.0,
            issueAt = dateNow,
            categoryId = "c-1",
        )
        private val mockProducts = listOf(
            mockProduct,
            mockProduct.copy(id = "p-2", name = "Product Two"),
            mockProduct.copy(id = "p-3", name = "Product Three")
        )
        private val mockProductsNoCategory = listOf(
            mockProduct.copy(categoryId = null),
            mockProduct.copy(id = "p-2", name = "Product Two"),
            mockProduct.copy(id = "p-3", name = "Product Three")
        )
        private val mockProductsOutsideOfDateRange = listOf(
            mockProduct.copy(issueAt = LocalDateTime.now().minusMonths(2)),
            mockProduct.copy(id = "p-2", name = "Product Two", issueAt = LocalDateTime.now().minusMonths(2)),
            mockProduct.copy(id = "p-3", name = "Product Three", issueAt = LocalDateTime.now().minusMonths(2))
        )
    }
}