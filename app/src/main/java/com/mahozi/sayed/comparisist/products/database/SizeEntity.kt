package com.mahozi.sayed.comparisist.products.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = ProductEntity::class, parentColumns = ["productId"], childColumns = ["productId"])])
data class SizeEntity(

    @ColumnInfo
    val size: Double,

    @ColumnInfo
    val unit: String,

    @ColumnInfo
    val productId: Long,

    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "size_id")
    val sizeId: Long = 0L


) {
}