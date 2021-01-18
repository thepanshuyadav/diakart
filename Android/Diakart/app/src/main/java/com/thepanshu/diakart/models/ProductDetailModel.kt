package com.thepanshu.diakart.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentId
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
        var links: ArrayList<String> = arrayListOf("www.google.com", "www.google.com"),
        var description: String = "",
        var documentId: String = "",
        var rating: ArrayList<Int> = ArrayList()
) : Parcelable