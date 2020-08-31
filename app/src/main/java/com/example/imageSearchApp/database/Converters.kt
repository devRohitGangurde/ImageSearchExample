package com.example.imageSearchApp.database

import androidx.room.TypeConverter
import com.example.imageSearchApp.model.InnerImage
import com.google.gson.Gson

/**
 * Converters class for database to convert custom data-types
 */
class Converters {

    @TypeConverter
    fun listToJson(value: List<InnerImage>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<InnerImage>::class.java).toList()
}