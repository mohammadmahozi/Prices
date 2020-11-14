package com.mahozi.sayed.comparisist.products.database

import androidx.room.Dao
import androidx.room.Query


@Dao
interface BrandDao: BaseDao<BrandEntity> {


    @Query("SELECT * FROM BrandEntity")
    fun selectAllBrands(): List<BrandEntity>


}

