package com.mahozi.sayed.comparisist.products.create

import androidx.lifecycle.*
import com.mahozi.sayed.comparisist.Resource
import com.mahozi.sayed.comparisist.products.database.ProductsRepository
import com.mahozi.sayed.comparisist.products.database.product.ProductDto
import com.mahozi.sayed.comparisist.products.database.product.ProductWithBrand
import kotlinx.coroutines.launch

class CreateProductViewModel(private val productsRepository: ProductsRepository, private val productDtoList: List<ProductDto>): ViewModel() {


    fun insertProduct(productFormModel: ProductFormModel){


         viewModelScope.launch {

             productsRepository.insertProduct(productFormModel)

         }
    }

    fun selectProductWithBrand(barcode: String): LiveData<Resource<ProductWithBrand>>{

        val productWithBrand = MutableLiveData<Resource<ProductWithBrand>>()

        viewModelScope.launch {

             productWithBrand.postValue(productsRepository.selectProductBy(barcode))

        }

        return productWithBrand
    }



    fun getProductNames(): List<String>{

        return productDtoList.map { it.productName }
    }


    fun getProductUnits(): List<String>{

        return productDtoList.map { it.sizeUnit }
    }



    fun getBrandNames(): List<String>{

        return productDtoList.map { it.brandName }
    }


    fun getStoreNames(): List<String>{

        return productDtoList.map{ it.storeName }
    }




    class Factory (private val productsRepository: ProductsRepository, private val productDtoList: List<ProductDto>): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return CreateProductViewModel(productsRepository, productDtoList) as T
        }
    }




}