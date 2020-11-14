package com.mahozi.sayed.comparisist.products.database


import androidx.room.Dao
import androidx.room.Query


@Dao
interface SizeDao: BaseDao<SizeEntity> {


    @Query("SELECT * FROM SizeEntity")
    fun selectAllSizes(): List<SizeEntity>
}