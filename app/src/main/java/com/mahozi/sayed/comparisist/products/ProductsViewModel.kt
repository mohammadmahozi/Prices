package com.mahozi.sayed.comparisist.products

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mahozi.sayed.comparisist.products.database.*
import com.mahozi.sayed.comparisist.products.domain.ProductModel

class ProductsViewModel(application: Application): AndroidViewModel(application){

    private val brandsList = selectAllBrands()

    private val sizesList = selectAllSizes()


    private val productsRepository: ProductsRepository =
        ProductsRepository(
            AppDatabase.getInstance(application)
        )



    private fun insertProduct(productEntity: ProductEntity){
        productsRepository.insertProduct(productEntity)
    }


    fun onConfirmCreateProduct(name: String, brand: String, size: Double, unit: String, store: String, quantity: Int, price: Double, date: String, isDeal: Boolean){


    }

    fun onConfirmCreateProduct(name: String, brand: String, sizeEntity: SizeEntity, store: String, quantity: Int, price: Double, date: String, isDeal: Boolean){


    }

    fun selectAllProducts(): LiveData<List<ProductEntity>> {

        return productsRepository.selectAllProducts()
    }


    private fun selectAllBrands(): List<BrandEntity> {

        return productsRepository.selectAllBrands()
    }

    fun getBrandNames(): List<String>{

        return brandsList.map { it.brandName }
    }

    private fun selectAllSizes(): List<SizeEntity>{

        return productsRepository.selectAllSizes()
    }

    fun getSizeNumbers(): List<Double>{

        return sizesList.map { it.size }
    }

    fun getSizeUnits(): List<String>{

        return sizesList.map { it.unit }
    }

}