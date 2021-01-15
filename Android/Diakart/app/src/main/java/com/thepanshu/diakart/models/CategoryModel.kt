package com.thepanshu.diakart.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.thepanshu.diakart.data.Category
import com.thepanshu.diakart.data.Product
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