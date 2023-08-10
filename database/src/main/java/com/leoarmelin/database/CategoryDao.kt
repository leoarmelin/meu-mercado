package com.leoarmelin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.leoarmelin.sharedmodels.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    fun fetchAllCategories(): Flow<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category)

    @Query("DELETE FROM categories WHERE `id` = :id")
    suspend fun deleteCategoryById(id: String)

    @Update(Category::class)
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM categories WHERE `id` = :id")
    fun getCategoryById(id: String): Flow<Category?>
}