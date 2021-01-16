package com.thepanshu.diakart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SliderModel(
    val image: String = "https://firebasestorage.googleapis.com/v0/b/diakart.appspot.com/o/res%2Fundraw_page_not_found.png?alt=media&token=0dabbab9-d212-46b0-8cb1-18ccf5ba92b6",
    val gif: String = "https://firebasestorage.googleapis.com/v0/b/diakart.appspot.com/o/res%2Fundraw_page_not_found.png?alt=media&token=0dabbab9-d212-46b0-8cb1-18ccf5ba92b6",
    val description: String = "Banner"
): Parcelable