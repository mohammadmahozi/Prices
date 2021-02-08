package com.mahozi.sayed.comparisist.prices.database

import android.util.Log
import androidx.lifecycle.LiveData
import com.mahozi.sayed.comparisist.AppDatabase
import java.lang.Exception

class PricesRepository(private val database: AppDatabase) {


    private val priceDao = database.priceDao

    fun selectPricesByProductAndStore(productId: Long, storeId: Long): LiveData<List<PriceDto>> {

        return priceDao.selectPricesByProductAndStore(productId, storeId)
    }


    suspend fun insertPrice(priceEntity: PriceEntity){

        try {

            priceDao.insert(priceEntity)
        }

        catch (e: Exception){

            Log.d("gggg", "insertPrice: ${e.message}")
        }
    }
}