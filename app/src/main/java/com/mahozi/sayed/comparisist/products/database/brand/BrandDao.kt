package com.mahozi.sayed.comparisist.products.database.brand

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Query
import com.mahozi.sayed.comparisist.BaseDao


@Dao
interface BrandDao: BaseDao<BrandEntity> {


    @Query("SELECT * FROM BrandEntity")
    fun selectAllBrands(): List<BrandEntity>


    @Nullable
    @Query("SELECT brandId FROM BrandEntity WHERE brandName= :brandName")
    suspend fun selectIdBy(brandName: String): Long?
}

