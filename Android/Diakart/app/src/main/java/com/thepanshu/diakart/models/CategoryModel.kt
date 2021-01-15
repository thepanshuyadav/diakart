package com.thepanshu.diakart.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentReference
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

//@Parcelize
//data class CategoryModel(
//        val category: Category,
//        val productList :ArrayList<Product>
//): Parcelable

@Parcelize
data class CategoryModel(
        val icon: String,
        val title: String,
        val preview: ArrayList<ProductDetailModel>,
        val productList: @RawValue DocumentReference
): Parcelable