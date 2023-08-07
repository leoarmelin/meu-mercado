package com.leoarmelin.meumercado.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.leoarmelin.sharedmodels.Ticket
import com.leoarmelin.meumercado.room.converters.ProductDataConverter
import com.leoarmelin.meumercado.room.converters.StoreDataConverter

@Database(entities = [Ticket::class], version = 3, exportSchema = false)
@TypeConverters(
    ProductDataConverter::class,
    StoreDataConverter::class,
)
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
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}