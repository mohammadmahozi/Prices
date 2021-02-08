package com.mahozi.sayed.comparisist.products.database.product

import com.google.gson.annotations.SerializedName


 data class ProductResponse(

     @SerializedName("code")
     val code: String,

     @SerializedName("status_verbose")
     val statusVerbose: String,

     @SerializedName("status")
     val status: String,

     @SerializedName("product")
     val productInfo: ProductInfo


){

    data class ProductInfo(

        @SerializedName("generic_name")
        val productName: String,

        @SerializedName("image_small_url")
        val imageUrl: String,

        @SerializedName("brands")
        val brandName: String,

        @SerializedName("quantity")
        val sizeAndUnit: String
    )

}
