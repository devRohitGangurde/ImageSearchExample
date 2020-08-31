package com.example.imageSearchApp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Images(
        var data :  List<Data>,
        var success : Boolean,
        var status :Int

):Parcelable