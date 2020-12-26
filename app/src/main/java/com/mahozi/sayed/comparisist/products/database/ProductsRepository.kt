package com.mahozi.sayed.comparisist.products.database

import androidx.lifecycle.LiveData
import com.mahozi.sayed.comparisist.products.ProductFormModel

class ProductsRepository(private val database: AppDatabase) {

    private val brandDao = database.brandDao
    private val priceDao = database.priceDao
    private val productDao = database.productDao
    private val storeDao = database.storeDao


    fun selectAllProductsAndPrices(): LiveData<List<ProductModel>>{

        return productDao.selectAllProductsAndPrices()
    }


    fun selectAllProducts(): List<ProductEntity>{

        return productDao.selectAllProducts()
    }


    fun selectAllBrands(): List<BrandEntity> {

        return brandDao.selectAllBrands()
    }

    fun selectAllStores():List<StoreEntity>{

        return storeDao.selectAllStores()
    }




    fun insertProduct(productFormModel: ProductFormModel){

        var brandId = productFormModel.brandId
        var storeId = productFormModel.storeId
        var productId = productFormModel.productId


        database.runInTransaction {

            if (brandId == -1L){

                brandId = brandDao.insert(BrandEntity(productFormModel.brandName))
            }


            if (storeId == -1L){

                storeId = storeDao.insert(StoreEntity(productFormModel.storeName))
            }

            if (productId == -1L){

                productId = productDao.insert(ProductEntity(
                    productFormModel.productName,
                    brandId,
                    productFormModel.size,
                    productFormModel.unit
                ))
            }

            if (isPriceInfoValid(productFormModel.price)){

                priceDao.insert(PriceEntity(
                    productId,
                    productFormModel.price,
                    storeId,
                    productFormModel.quantity,
                    productFormModel.isDeal,
                    productFormModel.date

                ))
            }


        }
    }

    private fun isPriceInfoValid(price: Double): Boolean{

        if (price == -1.0){
            return false
        }

        return true
    }
}