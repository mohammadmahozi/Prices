package com.mahozi.sayed.comparisist.products.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface StoreDao: BaseDao<StoreEntity> {


    @Query("SELECT * FROM StoreEntity")
    fun selectAllStores():List<StoreEntity>
}

