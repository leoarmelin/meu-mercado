package com.leoarmelin.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leoarmelin.database.converters.ProductDataConverter
import com.leoarmelin.database.converters.StoreDataConverter
import com.leoarmelin.database.migrations.MIGRATION_1_2
import com.leoarmelin.sharedmodels.Category
import com.leoarmelin.sharedmodels.Product

@Database(entities = [Category::class, Product::class], version = 2, exportSchema = false)
@TypeConverters(
    ProductDataConverter::class,
    StoreDataConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "jetpack"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}