package com.thepanshu.diakart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRatingModel(
        val rating: Int = 0,
        var name: String = "",
        var quantity: String = "",
        var mrp: Int = 0,
        var brand: String = "",
        var image: String = "",
        var documentId: String = "",
): Parcelable
