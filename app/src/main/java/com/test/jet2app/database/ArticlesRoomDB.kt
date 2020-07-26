package com.test.jet2app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class ArticlesRoomDB : RoomDatabase() {

    abstract fun articleDao(): ArticlesDao

    companion object {
        @Volatile
        private var INSTANCE: ArticlesRoomDB? = null

        fun getDatabase(context: Context): ArticlesRoomDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticlesRoomDB::class.java,
                    "articles_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}