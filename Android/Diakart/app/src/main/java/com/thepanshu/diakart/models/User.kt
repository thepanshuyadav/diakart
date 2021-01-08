package com.thepanshu.diakart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
        var user_id: String = "",
        var user_name: String = "",
        var user_email: String = "",
        var wishlist: ArrayList<Product>

) : Parcelable