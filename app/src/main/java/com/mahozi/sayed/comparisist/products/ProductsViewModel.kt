package com.mahozi.sayed.comparisist.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mahozi.sayed.comparisist.products.database.*

class ProductsViewModel(application: Application): AndroidViewModel(application){

    val productsAndPricesLiveDataList: LiveData<List<ProductModel>> by lazy {

        selectAllProductsAndPrices()

    }

    private val productsRepository: ProductsRepository =
        ProductsRepository(
            AppDatabase.getInstance(application)
        )


    private val productsList = productsRepository.selectAllProducts()


    fun getProductNames(): List<String>{

        return productsList.map { it.productName }
    }

    fun getProductUnits(): List<String>{

        return productsList.map { it.sizeUnit }
    }




    private val brandsList = productsRepository.selectAllBrands()




    fun insertProduct(productFormModel: ProductFormModel){


        productsRepository.insertProduct(productFormModel)

    }





     fun selectAllProductsAndPrices(): LiveData<List<ProductModel>> {

        return productsRepository.selectAllProductsAndPrices()
    }


    fun getBrandNames(): List<String>{

        return brandsList.map { it.brandName }
    }



    fun getStoreNames(): List<String>{

        return productsRepository.selectAllStores().map{ it.storeName }
    }



}