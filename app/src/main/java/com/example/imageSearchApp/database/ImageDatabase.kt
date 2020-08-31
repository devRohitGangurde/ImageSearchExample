package com.example.imageSearchApp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.imageSearchApp.model.Data

/**
 * Room database class for access the database instance
 */
@Database(entities = [(Data::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract  class ImageDatabase : RoomDatabase(){

    abstract fun imageDao(): ImageDAO?

    companion object {
        private const val DATABASE_NAME = "imageDb"
        private var sInstance: ImageDatabase? = null
        @Synchronized
        fun getInstance(context: Context): ImageDatabase {
            if (sInstance == null) {
                sInstance = Room
                        .databaseBuilder(context.applicationContext, ImageDatabase::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return sInstance!!
        }
    }

}