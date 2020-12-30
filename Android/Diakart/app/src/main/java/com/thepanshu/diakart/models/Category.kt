package com.thepanshu.diakart.models
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val categoryName: String = "",
    val categoryImageLink: String = ""

):Parcelable
