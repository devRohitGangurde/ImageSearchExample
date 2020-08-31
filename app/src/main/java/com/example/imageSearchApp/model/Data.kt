package com.example.imageSearchApp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "ImageTable")
@Parcelize
data class Data(
    val id : String?,
    val title : String?,
    val description : String?,
    @SerializedName("images")
    var innerImages :  List<InnerImage>,
    @PrimaryKey
    val link : String,
    var comment : String? = "No Comment Yet!",
    var is_album : Boolean = false


):Parcelable
