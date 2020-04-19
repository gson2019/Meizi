package com.example.bubble.meizi.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bubble.meizi.model.Hit
import com.example.bubble.meizi.network.MeiziNetwork.Companion.instance

@Database(entities = [Hit::class], version = 1)
abstract class FavImgDatabase: RoomDatabase() {
    abstract fun favImgDao(): FavImageDao
    companion object {
        @Volatile
        private var INSTANCE: FavImgDatabase? = null

        fun getDatabase(
            context: Context
        ): FavImgDatabase? {
            return INSTANCE ?: synchronized(FavImgDatabase::class.java) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavImgDatabase::class.java,
                    "favorite"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

