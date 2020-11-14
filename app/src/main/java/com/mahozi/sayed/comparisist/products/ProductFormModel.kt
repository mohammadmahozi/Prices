package com.mahozi.sayed.comparisist.products

data class ProductFormModel(

    val name: String,

    val brandId: Long

    val brandName: String,

    val size: Double,
    val unit: String,
    val store: String,
    val quantity: Int,
    val price: Double,
    val date: String,
    val isDeal: Boolean
) {
}