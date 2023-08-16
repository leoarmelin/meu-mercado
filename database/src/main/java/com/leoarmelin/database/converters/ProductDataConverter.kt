package com.leoarmelin.database.converters

import androidx.room.TypeConverter
import com.leoarmelin.database.extensions.listAdapter
import com.leoarmelin.sharedmodels.Product
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ProductDataConverter {
    private val adapter = Moshi.Builder().add(DateAdapter()).build().listAdapter<Product>()

    @TypeConverter
    fun fromProductList(value: List<Product>): String {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toProductList(value: String): List<Product> {
        return adapter.fromJson(value)!!
    }

    @TypeConverter
    fun fromDate(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneOffset.UTC)
        }
    }

    @TypeConverter
    fun toDate(date: LocalDateTime?): Long? {
        return date?.toInstant(ZoneOffset.UTC)?.toEpochMilli()
    }
}

class DateAdapter {
    @FromJson
    fun fromJson(json: String): LocalDateTime {
        return LocalDateTime.parse(json)
    }

    @ToJson
    fun toJson(value: LocalDateTime): String {
        return value.format(DateTimeFormatter.ISO_DATE_TIME)
    }

}