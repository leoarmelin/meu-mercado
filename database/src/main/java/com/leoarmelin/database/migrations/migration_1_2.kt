package com.leoarmelin.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Remove previous temp table
        database.execSQL("DROP TABLE IF EXISTS products_new")

        // Create a new translation table
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `products_new` (" +
                    "id TEXT PRIMARY KEY NOT NULL, " +
                    "name TEXT NOT NULL, " +
                    "unity TEXT NOT NULL, " +
                    "amount REAL NOT NULL, " +
                    "unity_price REAL NOT NULL, " +
                    "total_price REAL NOT NULL, " +
                    "issue_at INTEGER NOT NULL, " +
                    "category_id TEXT, " +
                    "FOREIGN KEY(`category_id`) REFERENCES categories(id) ON UPDATE CASCADE ON DELETE SET NULL" +
                    ")"
        )

        // Copy the data
        database.execSQL(
            "INSERT INTO `products_new` (id, name, unity, amount, unity_price, total_price, issue_at, category_id) " +
                    "SELECT id, name, unity, amount, unity_price, total_price, issue_at, category_id " +
                    "FROM products"
        )

        // Remove old table
        database.execSQL("DROP TABLE products")


        // Rename the new table to the original table name
        database.execSQL("ALTER TABLE products_new RENAME TO products")

        database.execSQL(
            "CREATE INDEX IF NOT EXISTS index_products_category_id ON products (category_id)"
        )
    }
}