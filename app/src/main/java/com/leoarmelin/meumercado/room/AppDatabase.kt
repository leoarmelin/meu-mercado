package com.leoarmelin.meumercado.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leoarmelin.meumercado.models.Ticket
import com.leoarmelin.meumercado.utils.ProductDataConverter

@Database(entities = [Ticket::class], version = 1, exportSchema = false)
@TypeConverters(ProductDataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao

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
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}