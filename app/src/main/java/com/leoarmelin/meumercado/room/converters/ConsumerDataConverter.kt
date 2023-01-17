package com.leoarmelin.meumercado.room.converters

import androidx.room.TypeConverter
import com.leoarmelin.meumercado.models.Consumer
import com.squareup.moshi.Moshi

class ConsumerDataConverter {
    private val adapter = Moshi.Builder().build().adapter(Consumer::class.java)

    @TypeConverter
    fun fromConsumerList(value: Consumer?): String? {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toConsumerList(value: String?): Consumer? {
        return value?.let { adapter.fromJson(it) }
    }
}