package com.leoarmelin.meumercado.room.converters

import androidx.room.TypeConverter
import com.leoarmelin.meumercado.extensions.moshi.listAdapter
import com.leoarmelin.meumercado.models.Product
import com.squareup.moshi.Moshi

class ProductDataConverter {
    private val adapter = Moshi.Builder().build().listAdapter<Product>()

    @TypeConverter
    fun fromProductList(value: List<Product>): String {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun toProductList(value: String): List<Product> {
        return adapter.fromJson(value)!!
    }
}