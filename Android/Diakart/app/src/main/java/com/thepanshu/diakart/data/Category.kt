package com.thepanshu.diakart.data
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val icon: String = "",
    val title: String = ""

):Parcelable
