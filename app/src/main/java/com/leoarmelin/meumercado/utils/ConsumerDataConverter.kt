package com.leoarmelin.meumercado.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leoarmelin.meumercado.models.Consumer

class ConsumerDataConverter {
    @TypeConverter
    fun fromConsumerList(value: Consumer): String {
        val gson = Gson()
        val type = object : TypeToken<Consumer>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toConsumerList(value: String): Consumer {
        val gson = Gson()
        val type = object : TypeToken<Consumer>() {}.type
        return gson.fromJson(value, type)
    }
}