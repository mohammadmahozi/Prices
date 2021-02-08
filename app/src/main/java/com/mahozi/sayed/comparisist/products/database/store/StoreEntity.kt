package com.mahozi.sayed.comparisist.products.database.store

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["storeName"], unique = true)])
data class StoreEntity(

    @ColumnInfo
    val storeName: String,


    @PrimaryKey(autoGenerate = true)
    val storeId: Long = 0L


) {


}