package com.mahozi.sayed.comparisist.products.create

import com.mahozi.sayed.comparisist.products.database.product.ProductResponse
import retrofit2.http.*

interface ProductService {


    //either generic_name or product_name. not sure which is better. sometimes product_name has the brand too
    @Headers("User-Agent: MyFoodPrices(Test) - Android - Version 1.0")
    @GET("api/v0/product/{barcode}.json?fields=image_small_url,generic_name,brands,quantity")
    suspend fun selectProductByBarcode(
        @Path("barcode") barcode: String,
    ): ProductResponse
}