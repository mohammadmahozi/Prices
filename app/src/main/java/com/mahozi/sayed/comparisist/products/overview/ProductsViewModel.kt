package com.mahozi.sayed.comparisist.products.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahozi.sayed.comparisist.products.database.*
import com.mahozi.sayed.comparisist.products.database.product.ProductDto

class ProductsViewModel(private val productsRepository: ProductsRepository): ViewModel(){





    fun filter(dataList: List<ProductDto>, allQueryWords: String): List<ProductDto> {

        if (allQueryWords.isEmpty()){

            return dataList
        }

        else{

            return dataList.filter {

                var isContained = true


                val filterableRow = "${it.productName} ${it.brandName}  ${it.storeName}"

                for (queryWord in allQueryWords.split(" ")){
                    isContained = isContained && filterableRow.contains(queryWord, true)
                }

                isContained
            }
        }

    }

//    fun filter(allQueryWords: String) {
//
//        if (allQueryWords.isEmpty()){
//
//            filteredDataList = dataList
//        }
//
//        else{
//
//            filteredDataList = dataList.filter {
//
//                var isContained = true
//
//
//                val filterableRow = "${it.productName} ${it.brandName}  ${it.storeName}"
//
//                for (queryWord in allQueryWords.split(" ")){
//                    isContained = isContained && filterableRow.contains(queryWord, true)
//                }
//
//                isContained
//            }
//        }
//
//    }


    class Factory (private val productsRepository: ProductsRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return ProductsViewModel(productsRepository) as T
        }
    }




}