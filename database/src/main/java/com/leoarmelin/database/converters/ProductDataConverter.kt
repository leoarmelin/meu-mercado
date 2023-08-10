package com.leoarmelin.database.converters

import androidx.room.TypeConverter
import com.leoarmelin.database.extensions.listAdapter
import com.leoarmelin.sharedmodels.Product
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