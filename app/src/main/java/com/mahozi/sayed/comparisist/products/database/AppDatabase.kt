package com.mahozi.sayed.comparisist.products.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.*


@Database(entities = [BrandEntity::class, PriceEntity::class, ProductEntity::class,  SizeEntity::class, StoreEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val brandDao: BrandDao
    abstract val priceDao: PriceDao
    abstract val productDao: ProductDao
    abstract val sizeDao: SizeDao
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
                        }).allowMainThreadQueries().build()
                }

                return INSTANCE as AppDatabase
            }
        }
    }

}