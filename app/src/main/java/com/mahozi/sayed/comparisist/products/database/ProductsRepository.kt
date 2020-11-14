package com.mahozi.sayed.comparisist.products.database

import androidx.lifecycle.LiveData

class ProductsRepository(private val database: AppDatabase) {

    private val brandDao = database.brandDao
    private val priceDao = database.priceDao
    private val productDao = database.productDao
    private val sizeDao = database.sizeDao
    private val storeDao = database.storeDao


    fun insertProduct(productEntity: ProductEntity){
        productDao.insert(productEntity)
    }

    fun selectAllProducts(): LiveData<List<ProductEntity>>{

        return productDao.selectAllProducts()
    }


    fun selectAllBrands(): List<BrandEntity> {

        return brandDao.selectAllBrands()
    }

    fun selectAllSizes(): List<SizeEntity>{

        return sizeDao.selectAllSizes()
    }


    fun insertProduct(){

        database.runInTransaction({


        })
    }
}