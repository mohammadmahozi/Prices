package com.mahozi.sayed.comparisist.products.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(foreignKeys = [

    ForeignKey(entity = SizeEntity::class, parentColumns = ["sizeId"], childColumns = ["sizeId"]),

    ForeignKey(entity = ProductEntity::class, parentColumns = ["productId"], childColumns = ["productId"]),

    ForeignKey(entity = BrandEntity::class, parentColumns = ["brandId"], childColumns = ["brandId"])


])
data class ProductEntity(

    @ColumnInfo
    val productName: String,

    @ColumnInfo
    val brandId: Long,

    @ColumnInfo
    val sizeId: Long,



    @PrimaryKey(autoGenerate = true)
    //@ColumnInfo(name = "product_id")
    val productId: Long = 0L


) {


}