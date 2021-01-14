package com.thepanshu.diakart.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductDetailModel(
        var id: String = "0",
        var name: String = "",
        var quantity: String = "",
        var mrp: Int = 0,
        var prices: ArrayList<Int> = ArrayList(),
        var brand: String = "",
        var images: ArrayList<String> = ArrayList(),
        //var product_images: ArrayList<Int> = ArrayList(),
        var links: ArrayList<String> = arrayListOf("www.google.com", "www.google.com"),
        var description: String = "",
        var ratings: ArrayList<Int> = ArrayList()
) : Parcelable