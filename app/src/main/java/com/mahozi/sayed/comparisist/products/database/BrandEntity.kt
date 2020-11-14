package com.mahozi.sayed.comparisist.products.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BrandEntity(

    @ColumnInfo
    val brandName: String,


    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "brand_id")
    val brandId: Long = 0L

) {
}