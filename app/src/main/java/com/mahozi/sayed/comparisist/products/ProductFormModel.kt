package com.mahozi.sayed.comparisist.products

data class ProductFormModel(

    val productId: Long,

    val productName: String,

    val brandId: Long,

    val brandName: String,

    val size: Double,

    val unit: String,

    val storeId: Long,

    val storeName: String,

    val quantity: Double,

    val price: Double,

    val date: String,

    val isDeal: Boolean
) {
}