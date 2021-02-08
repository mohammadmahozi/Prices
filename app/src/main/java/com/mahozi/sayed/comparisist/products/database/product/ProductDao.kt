package com.mahozi.sayed.comparisist.products.database.product

import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mahozi.sayed.comparisist.BaseDao


@Dao
interface ProductDao: BaseDao<ProductEntity> {


    @Query("SELECT ProductEntity.productId, p.storeId, productName, size, sizeUnit, brandName, storeName, p.price, p.quantity, productImagePath FROM ProductEntity  LEFT JOIN BrandEntity ON ProductEntity.brandId = BrandEntity.brandId  INNER JOIN (select  productId, storeId, price, quantity, max(dateAdded) as maxDate From PriceEntity group by productId, storeId) p ON ProductEntity.productId = p.productId LEFT JOIN  StoreEntity ON p.storeId = StoreEntity.storeId")
    fun selectAllProductsAndPrices(): LiveData<List<ProductDto>>


    @Query("SELECT * FROM ProductEntity")
    fun selectAllProducts(): List<ProductEntity>

    @Nullable
    @Query("SELECT productId FROM ProductEntity WHERE productName= :productName AND brandId = :brandId AND size = :size AND sizeUnit = :unit")
    suspend fun selectIdBy(productName: String, brandId: Long, size: Double, unit: String): Long?

    @Nullable
    @Query("SELECT productId, productName, brandName, size, sizeUnit, productImagePath, barcode FROM ProductEntity LEFT JOIN BrandEntity ON ProductEntity.brandId = BrandEntity.brandId WHERE barcode = :barcode")
    suspend fun selectProductWithBrandBy(barcode: String): ProductWithBrand?
}

