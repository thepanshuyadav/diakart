package com.thepanshu.diakart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserRatingModel(
        val rating: Int = 0,
        val product: ProductDetailModel = ProductDetailModel()
): Parcelable
