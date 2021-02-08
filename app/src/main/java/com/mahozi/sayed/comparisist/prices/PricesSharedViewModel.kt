package com.mahozi.sayed.comparisist.prices

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mahozi.sayed.comparisist.prices.database.PriceDto
import com.mahozi.sayed.comparisist.prices.database.PriceEntity
import com.mahozi.sayed.comparisist.prices.database.PricesRepository
import kotlinx.coroutines.launch

class PricesSharedViewModel(private val pricesRepository: PricesRepository , val productId: Long, val storeId: Long): ViewModel() {


    val pricesDtoLiveDataList: LiveData<List<PriceDto>> by lazy {

        pricesRepository.selectPricesByProductAndStore(productId, storeId)
    }


    fun insertPrice(priceEntity: PriceEntity){

        viewModelScope.launch {

            pricesRepository.insertPrice(priceEntity)
        }
    }


    class Factory(private val pricesRepository: PricesRepository, private val productId: Long, private val storeId: Long): ViewModelProvider.Factory{

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return PricesSharedViewModel(pricesRepository, productId, storeId) as T
        }
    }
}