package com.mahozi.sayed.comparisist.products.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [

    ForeignKey(entity = ProductEntity::class, parentColumns = ["productId"], childColumns = ["productId"]),
    ForeignKey(entity = StoreEntity::class, parentColumns = ["storeId"], childColumns = ["storeId"])

])
data class PriceEntity(

    @ColumnInfo
    val productId: Long,

    @ColumnInfo
    val price: Double,

    @ColumnInfo
    val storeId: Long,

    @ColumnInfo
    val quantity: Double,


    @ColumnInfo
    val isDeal: Boolean = false,

    @ColumnInfo
    val dateAdded: String,


    @PrimaryKey(autoGenerate = true)
    val priceId: Long = 0L


) {


}