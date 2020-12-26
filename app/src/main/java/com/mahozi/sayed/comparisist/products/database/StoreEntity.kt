package com.mahozi.sayed.comparisist.products.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StoreEntity(

    @ColumnInfo
    val storeName: String,


    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "store_id")
    val storeId: Long = 0L


) {


}