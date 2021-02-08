package com.mahozi.sayed.comparisist.products.database.product

data class ProductWithBrand(

    val productId: Long,

    val productName: String,

    val brandName: String,

    val size: Double,

    val sizeUnit: String,

    val productImagePath: String,

    val barcode: String,

)
