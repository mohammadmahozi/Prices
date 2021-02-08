package com.mahozi.sayed.comparisist.products


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahozi.sayed.comparisist.products.database.ProductsRepository
import com.mahozi.sayed.comparisist.products.database.product.ProductDto

class ProductsSharedViewModel(private val repository: ProductsRepository): ViewModel() {



    val productsAndPricesLiveDataList: LiveData<List<ProductDto>> by lazy {

        repository.selectAllProductsAndPrices()
    }



    class Factory (private val productsRepository: ProductsRepository): ViewModelProvider.Factory {


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return ProductsSharedViewModel(productsRepository) as T
        }


    }


}