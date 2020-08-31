package com.example.imageSearchApp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InnerImage(
        var link : String
):Parcelable