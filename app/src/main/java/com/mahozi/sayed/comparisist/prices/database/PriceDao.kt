package com.mahozi.sayed.comparisist.prices.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.mahozi.sayed.comparisist.BaseDao


@Dao
interface PriceDao: BaseDao<PriceEntity> {

    @Query("SELECT price, quantity, dateAdded, isDeal FROM PriceEntity WHERE productId = :productId AND storeId = :storeId ORDER BY dateAdded DESC")
    fun selectPricesByProductAndStore(productId: Long, storeId: Long): LiveData<List<PriceDto>>


}