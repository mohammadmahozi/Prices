package com.mahozi.sayed.comparisist.products.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface ProductDao: BaseDao<ProductEntity> {


    @Query("select productName, size, sizeUnit, brandName, storeName, price, quantity FROM ProductEntity LEFT JOIN BrandEntity ON ProductEntity.brandId = BrandEntity.brandId INNER JOIN PriceEntity ON ProductEntity.productId = PriceEntity.productId LEFT JOIN  StoreEntity ON PriceEntity.storeId = StoreEntity.storeId")
    fun selectAllProductsAndPrices(): LiveData<List<ProductModel>>


    @Query("SELECT * FROM ProductEntity")
    fun selectAllProducts(): List<ProductEntity>

}

