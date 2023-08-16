package com.leoarmelin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.leoarmelin.sharedmodels.Product
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun fetchAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE `category_id` = :categoryId AND `issue_at` BETWEEN :startDate AND :endDate")
    fun fetchProductsFromCategory(
        categoryId: String?,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE `category_id` IS NULL AND `issue_at` BETWEEN :startDate AND :endDate")
    fun fetchProductsWithoutCategory(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM products WHERE `id` = :id")
    suspend fun deleteProductById(id: String)

    @Update(Product::class)
    suspend fun updateProduct(product: Product)

    @Query("SELECT SUM(total_price) FROM products WHERE `category_id` = :categoryId AND `issue_at` BETWEEN :startDate AND :endDate")
    fun getTotalAmountFromCategoryId(
        categoryId: String,
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Double?>

    @Query("SELECT SUM(total_price) FROM products WHERE `category_id` IS NULL AND `issue_at` BETWEEN :startDate AND :endDate")
    fun getTotalAmountFromNoCategory(
        startDate: LocalDateTime,
        endDate: LocalDateTime
    ): Flow<Double?>

    @Query("SELECT * FROM products WHERE `id` = :id")
    fun getProductById(id: String): Flow<Product?>
}