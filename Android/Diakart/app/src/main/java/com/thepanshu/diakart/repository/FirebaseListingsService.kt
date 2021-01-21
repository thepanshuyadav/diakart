package com.thepanshu.diakart.repository

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseError
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Transaction
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.SliderModel
import kotlinx.coroutines.tasks.await
import kotlin.Result.Companion.success


object FirebaseListingsService {
    private val db = FirebaseFirestore.getInstance()
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

    suspend fun getBannerList(): ArrayList<SliderModel>? {
        try {
            val bannerDocList = db.collection("HOME_BANNER").get().await().documents

            val bannerList = ArrayList<SliderModel>()
            bannerDocList.forEach { doc ->
                val banner = doc.toObject(SliderModel::class.java)
                if(banner != null) {
                    bannerList.add(banner)
                }

            }
            return bannerList
        } catch (e: Exception) {
            Log.e(TAG, "Error getting banner", e)
            FirebaseCrashlytics.getInstance().log("Error getting banner")
            FirebaseCrashlytics.getInstance().setCustomKey("banner", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            return null
        }
    }

    suspend fun getProductDetail(reference: DocumentReference): ProductDetailModel? {
        return try {
            reference.get().await().toObject(ProductDetailModel::class.java)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting product details", e)
            FirebaseCrashlytics.getInstance().log("Error getting product details")
            FirebaseCrashlytics.getInstance().setCustomKey("PRODUCT", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }
}