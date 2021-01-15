package com.thepanshu.diakart.repository

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import kotlinx.coroutines.tasks.await

object FirebaseListingsService {
    val db = FirebaseFirestore.getInstance()
    private const val TAG = "FirebaseListingsService"
    suspend fun getCategory(): List<CategoryModel> {

        try {
            return db.collection("CATEGORIES").get()
                .await()
                .documents.mapNotNull {
                    val title = it.data?.get("title").toString()
                    val icon = it.data?.get("icon").toString()
                    val products = it.data?.get("products") as DocumentReference
                    val previewRef= it.data?.get("preview") as List<DocumentReference>
                    val preview = ArrayList<ProductDetailModel>()
                    previewRef.forEach { doc ->
                        preview.add(doc.get().await().toObject(ProductDetailModel::class.java)!!)

                    }
                    CategoryModel(icon, title, preview, products)
                }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
            FirebaseCrashlytics.getInstance().setCustomKey("user", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            return emptyList()
        }
    }

    suspend fun getProductList(path: DocumentReference): ArrayList<ProductDetailModel>? {
        return try {
            val productRefList = path.get().await().data?.get("products") as List<DocumentReference>
            val productDetailList = ArrayList<ProductDetailModel>()
            productRefList.forEach { ref ->
                val prod = ref.get().await().toObject(ProductDetailModel::class.java)
                if(prod != null) {
                    productDetailList.add(prod)
                }

            }
            productDetailList
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
            FirebaseCrashlytics.getInstance().setCustomKey("user", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    suspend fun getProductDetail(reference: DocumentReference): ProductDetailModel? {
        return try {
            reference.get().await().toObject(ProductDetailModel::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
            FirebaseCrashlytics.getInstance().setCustomKey("user", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }

    }
}
