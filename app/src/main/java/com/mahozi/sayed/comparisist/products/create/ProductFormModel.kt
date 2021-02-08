package com.mahozi.sayed.comparisist.products.create


data class ProductFormModel(

    val productImagePath: String,

    val productName: String,
    val barcode: String,

    val brandName: String,

    val size: Double,

    val sizeUnit: String,

    val storeName: String,

    val quantity: Double,

    val price: Double,

    val dateAdded: String,

    val isDeal: Boolean
) {

}


