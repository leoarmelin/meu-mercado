package com.leoarmelin.meumercado.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leoarmelin.meumercado.models.Store

class StoreDataConverter {
    @TypeConverter
    fun fromStoreList(value: Store): String {
        val gson = Gson()
        val type = object : TypeToken<Store>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStoreList(value: String): Store {
        val gson = Gson()
        val type = object : TypeToken<Store>() {}.type
        return gson.fromJson(value, type)
    }
}