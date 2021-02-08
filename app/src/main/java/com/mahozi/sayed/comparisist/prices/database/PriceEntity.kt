package com.mahozi.sayed.comparisist.prices.database

import androidx.room.*
import com.mahozi.sayed.comparisist.products.database.product.ProductEntity
import com.mahozi.sayed.comparisist.products.database.store.StoreEntity


@Entity(foreignKeys = [

    ForeignKey(entity = ProductEntity::class, parentColumns = ["productId"], childColumns = ["productId"]),
    ForeignKey(entity = StoreEntity::class, parentColumns = ["storeId"], childColumns = ["storeId"])],

    //only one price per day is allowed for the same product from the same store
    indices = [Index(value = ["productId", "storeId", "dateAdded"], unique = true)] )

data class PriceEntity(

    @ColumnInfo
    val productId: Long,

    @ColumnInfo
    val price: Double,

    @ColumnInfo
    val storeId: Long,

    @ColumnInfo
    val quantity: Double,


    @ColumnInfo
    val isDeal: Boolean = false,

    @ColumnInfo
    val dateAdded: String,


    @PrimaryKey(autoGenerate = true)
    val priceId: Long = 0L


)