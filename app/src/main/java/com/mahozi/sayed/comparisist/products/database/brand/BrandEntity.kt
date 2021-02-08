package com.mahozi.sayed.comparisist.products.database.brand

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["brandName"], unique = true)])
data class BrandEntity(

    @ColumnInfo
    val brandName: String,


    @PrimaryKey(autoGenerate = true)
    val brandId: Long = 0L

) {
}