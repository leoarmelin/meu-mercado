package com.leoarmelin.sharedmodels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@Entity(
    tableName = "products",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.SET_NULL,
        onUpdate = ForeignKey.CASCADE
    )]
)
@JsonClass(generateAdapter = true)
data class Product(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @Json(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    @Json(name = "name")
    val name: String,
    @ColumnInfo(name = "unity")
    @Json(name = "unity")
    val unity: Unity,
    @ColumnInfo(name = "amount")
    @Json(name = "amount")
    val amount: Double,
    @ColumnInfo(name = "unity_price")
    @Json(name = "unityPrice")
    val unityPrice: Double,
    @ColumnInfo(name = "total_price")
    @Json(name = "totalPrice")
    val totalPrice: Double,
    @ColumnInfo(name = "issue_at")
    @Json(name = "issueAt")
    val issueAt: LocalDateTime,
    @ColumnInfo(name = "category_id", index = true)
    @Json(name = "categoryId")
    val categoryId: String? = null,
) {
    companion object
}

val Product.Companion.mock
    get() = Product(
        id = "p-1",
        name = "ProductTest",
        unity = Unity.UN,
        amount = 2.0,
        unityPrice = 6.0,
        totalPrice = 12.0,
        issueAt = LocalDateTime.now()
    )
