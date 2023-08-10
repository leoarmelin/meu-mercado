package com.leoarmelin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.leoarmelin.sharedmodels.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products WHERE `category_id` = :categoryId")
    fun fetchProductsFromCategory(categoryId: String): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)

    @Query("DELETE FROM products WHERE `id` = :id")
    suspend fun deleteProductById(id: String)

    @Update(Product::class)
    suspend fun updateProduct(product: Product)

    @Query("SELECT SUM(total_price) FROM products WHERE `category_id` = :categoryId")
    fun getTotalAmountFromCategoryId(categoryId: String): Flow<Double>

    @Query("SELECT * FROM products WHERE `id` = :id")
    fun getProductById(id: String): Flow<Product?>
}