package com.mahozi.sayed.comparisist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mahozi.sayed.comparisist.products.database.brand.BrandDao
import com.mahozi.sayed.comparisist.products.database.brand.BrandEntity
import com.mahozi.sayed.comparisist.prices.database.PriceDao
import com.mahozi.sayed.comparisist.prices.database.PriceEntity
import com.mahozi.sayed.comparisist.products.database.product.ProductDao
import com.mahozi.sayed.comparisist.products.database.product.ProductEntity
import com.mahozi.sayed.comparisist.products.database.store.StoreDao
import com.mahozi.sayed.comparisist.products.database.store.StoreEntity


@Database(entities = [BrandEntity::class, PriceEntity::class, ProductEntity::class, StoreEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val brandDao: BrandDao
    abstract val priceDao: PriceDao
    abstract val productDao: ProductDao
    abstract val storeDao: StoreDao


    companion object{

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            synchronized(this){

                if (INSTANCE == null){

                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "AppDatabase")
                        .addCallback(object: Callback(){

                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                            }
                        }).build()
                }

                return INSTANCE as AppDatabase
            }
        }
    }

}