package com.leoarmelin.sharedmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "categories")
@JsonClass(generateAdapter = true)
data class Category(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    @Json(name = "name")
    val name: String,
    @ColumnInfo(name = "emoji")
    @Json(name = "emoji")
    val emoji: String
) {
    companion object
}

val Category.Companion.mock get() = Category(
    id = "id-1",
    name = "TestCategory",
    emoji = "\uD83C\uDF63"
)