package com.mahozi.sayed.comparisist.products.database.store

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Query
import com.mahozi.sayed.comparisist.BaseDao


@Dao
interface StoreDao: BaseDao<StoreEntity> {


    @Query("SELECT * FROM StoreEntity")
    fun selectAllStores():List<StoreEntity>

    @Nullable
    @Query("SELECT storeId FROM StoreEntity WHERE storeName= :storeName")
    fun selectIdBy(storeName: String): Long?
}

