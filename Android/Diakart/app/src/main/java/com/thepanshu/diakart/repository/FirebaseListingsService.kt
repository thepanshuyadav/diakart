package com.thepanshu.diakart.repository

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.CouponModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.SliderModel
import kotlinx.coroutines.tasks.await


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
                            val prod = doc.get().await().toObject(ProductDetailModel::class.java)
                            if(prod!=null) {
                                prod.description = prod.description.replace('|', '\n', true)
                                preview.add(prod)
                            }

                        }
                        CategoryModel(icon, title, preview, products)
                    }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error getting category")
            FirebaseCrashlytics.getInstance().setCustomKey("category", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            return emptyList()
        }
    }

    suspend fun getCouponList(): List<CouponModel>? {
        return try {
            db.collection("COUPONS").get().await().documents.mapNotNull {
                it.toObject(CouponModel::class.java)
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error getting coupon list")
            FirebaseCrashlytics.getInstance().setCustomKey("coupon_list", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }


    suspend fun getProductList(path: DocumentReference): ArrayList<ProductDetailModel>? {
        return try {
            val productRefList = path.get().await().data?.get("products") as List<DocumentReference>
            val productDetailList = ArrayList<ProductDetailModel>()
            productRefList.forEach { ref ->
                val prod = ref.get().await().toObject(ProductDetailModel::class.java)
                if(prod != null) {
                    prod.description = prod.description.replace('|', '\n')
                    productDetailList.add(prod)
                }

            }
            productDetailList
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error getting product list")
            FirebaseCrashlytics.getInstance().setCustomKey("product_list", TAG)
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
            FirebaseCrashlytics.getInstance().log("Error getting banner")
            FirebaseCrashlytics.getInstance().setCustomKey("banner", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            return null
        }
    }

    suspend fun getProductDetail(reference: String): ProductDetailModel? {
        return try {
            db.collection("PRODUCTS")
                    .document(reference)
                    .get().await().toObject(ProductDetailModel::class.java)
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error getting product details")
            FirebaseCrashlytics.getInstance().setCustomKey("product_details", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }
}