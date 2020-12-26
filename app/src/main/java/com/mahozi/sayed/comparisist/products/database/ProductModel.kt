package com.mahozi.sayed.comparisist.products.database

import androidx.room.ColumnInfo

data class ProductModel(
    val productName: String,

    val size: Double,

    val sizeUnit: String,

    val brandName: String,

    val storeName: String,

    val price: Double,

    val quantity: Double






    ) {
}