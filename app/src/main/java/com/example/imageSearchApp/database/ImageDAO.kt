package com.example.imageSearchApp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.imageSearchApp.model.Data

/**
 * DAO class for managing database Queries
 */
@Dao
interface ImageDAO {

    @Insert(onConflict = REPLACE)
    fun insertImageData(image: Data)

    @Query("SELECT * FROM ImageTable WHERE link = :link")
    fun getImageDataByLink(link: String): Data?
}