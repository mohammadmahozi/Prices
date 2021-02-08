package com.mahozi.sayed.comparisist.products.database.product


data class ProductDto(

    val productId: Long,

    val storeId: Long,

    val productName: String,

    val size: Double,

    val sizeUnit: String,

    val brandName: String,

    val storeName: String,

    val price: Double,

    val quantity: Double,

    val productImagePath: String


    ) {


}