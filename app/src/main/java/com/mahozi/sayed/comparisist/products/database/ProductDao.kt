package com.mahozi.sayed.comparisist.products.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface ProductDao: BaseDao<ProductEntity> {

    @Query("SELECT * FROM ProductEntity")
    fun selectAllProducts(): LiveData<List<ProductEntity>>

}

