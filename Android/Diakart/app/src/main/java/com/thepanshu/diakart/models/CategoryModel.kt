package com.thepanshu.diakart.models

import android.os.Parcelable
import com.google.firebase.firestore.DocumentSnapshot
import com.thepanshu.diakart.data.Category
import com.thepanshu.diakart.data.Product
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryModel(
        val category: Category,
        val productList :ArrayList<Product>
): Parcelable