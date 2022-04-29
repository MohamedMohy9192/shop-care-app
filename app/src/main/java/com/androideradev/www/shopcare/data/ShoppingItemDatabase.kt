package com.androideradev.www.shopcare.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ShoppingItem::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

    companion object {

        @Volatile
        private var INSTANCE: ShoppingItemDatabase? = null

        fun getDatabase(context: Context): ShoppingItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingItemDatabase::class.java,
                    "shopping_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }

}