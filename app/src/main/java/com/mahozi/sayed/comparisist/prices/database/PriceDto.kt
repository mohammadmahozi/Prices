package com.mahozi.sayed.comparisist.prices.database



data class PriceDto(

    val price: Double,

    val quantity: Double,

    val isDeal: Boolean = false,

    val dateAdded: String

)


    {
}