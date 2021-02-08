package com.mahozi.sayed.comparisist.prices.main

import android.app.Application
import androidx.lifecycle.*
import com.mahozi.sayed.comparisist.products.database.ProductsRepository
import com.mahozi.sayed.comparisist.AppDatabase
import com.mahozi.sayed.comparisist.prices.database.PriceDto
import com.mahozi.sayed.comparisist.prices.database.PriceEntity
import com.mahozi.sayed.comparisist.prices.database.PricesRepository
import kotlinx.coroutines.launch

class PricesViewModel(private val pricesRepository: PricesRepository, private val productId: Long, private val storeId: Long): ViewModel(){





    class Factory(private val pricesRepository: PricesRepository, private val productId: Long, private val storeId: Long)
        : ViewModelProvider.Factory{


        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            return PricesViewModel(pricesRepository, productId, storeId) as T
        }
    }


}