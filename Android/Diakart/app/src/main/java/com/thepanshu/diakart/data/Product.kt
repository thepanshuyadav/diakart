package com.thepanshu.diakart.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var product_id: Int = -1,
    var product_name: String = "",
    var quantity: String = "",
    var price: Int = 0,
    var brand_name: String = "",
    var category: String = "",
    //var product_images: ArrayList<String> = ArrayList(),
    var product_images: ArrayList<Int> = ArrayList(),
    var link: String = "",
    var product_desc: String = ""
) : Parcelable
