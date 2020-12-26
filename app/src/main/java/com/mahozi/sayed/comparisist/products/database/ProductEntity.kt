package com.mahozi.sayed.comparisist.products.database

import androidx.room.*

//

@Entity(foreignKeys = [

    ForeignKey(entity = ProductEntity::class, parentColumns = ["productId"], childColumns = ["productId"]),

    ForeignKey(entity = BrandEntity::class, parentColumns = ["brandId"], childColumns = ["brandId"])


], indices = [Index(value = ["productName", "brandId", "size", "sizeUnit"], unique = true)]
)
data class ProductEntity(

    @ColumnInfo
    val productName: String,

    @ColumnInfo
    val brandId: Long,

    @ColumnInfo
    val size: Double,

    @ColumnInfo
    val sizeUnit: String,



    @PrimaryKey(autoGenerate = true)
    val productId: Long = 0L


) {


}