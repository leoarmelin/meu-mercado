package com.leoarmelin.meumercado.room.converters

import androidx.room.TypeConverter
import com.leoarmelin.meumercado.models.Store
import com.squareup.moshi.Moshi

class StoreDataConverter {
    private val adapter = Moshi.Builder().build().adapter(Store::class.java)

    @TypeConverter
    fun fromStoreList(value: Store): String {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toStoreList(value: String): Store {
        return adapter.fromJson(value)!!
    }
}