package com.thepanshu.diakart.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.UserModel
import com.thepanshu.diakart.models.UserModel.Companion.toUser
import kotlinx.coroutines.tasks.await

object FirebaseUserService {
    private const val TAG = "FirebaseUserService"
    private val userId = Firebase.auth.currentUser?.uid.toString()
    private val db = FirebaseFirestore.getInstance()
    suspend fun getProfileData(): UserModel? {
        return try {
            db.collection("USERS")
                    .document(userId).get().await().toUser()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user details", e)
            FirebaseCrashlytics.getInstance().log("Error getting user details")
            FirebaseCrashlytics.getInstance().setCustomKey("user", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }


    // TODO: get wish list and ratings
    suspend fun updateRating(rating: Int, prodDocId: String) {

        db.runTransaction { transaction->
            transaction.set(db.collection("USERS")
                    .document(userId)
                    .collection("RATINGS")
                    .document(prodDocId),
                    hashMapOf("rating" to rating))
        }
    }

    suspend fun addToWishList(product: ProductDetailModel): Boolean {
        return try {
            db.runTransaction { transaction ->
                transaction.set(db.collection("USERS")
                        .document(userId)
                        .collection("WISHLIST")
                        .document(product.documentId),
                        product)
            }.isSuccessful
        } catch (e: Exception) {
//            Log.e(FirebaseUserService.TAG, "Error getting wish list", e)
//            FirebaseCrashlytics.getInstance().log("Error getting wish list")
//            FirebaseCrashlytics.getInstance().setCustomKey("wishlist", FirebaseUserService.TAG)
//            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }


    }

    suspend fun removeFromWishList(prodDocId: String): Boolean {
        return try {
            db.runTransaction { transaction->
                transaction.delete(db.collection("USERS")
                        .document(userId)
                        .collection("WISHLIST")
                        .document(prodDocId))
            }.isSuccessful
        } catch (e: Exception) {
//            Log.e(FirebaseUserService.TAG, "Error getting wish list", e)
//            FirebaseCrashlytics.getInstance().log("Error getting wish list")
//            FirebaseCrashlytics.getInstance().setCustomKey("wishlist", FirebaseUserService.TAG)
//            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }
    }

    suspend fun fetchWishList(): List<ProductDetailModel> {
        return try {
            val list = ArrayList<ProductDetailModel>()
            db.collection("USERS")
                    .document(userId)
                    .collection("WISHLIST")
                    .get().await().documents.mapNotNull {
                        list.add(it.toObject(ProductDetailModel::class.java)!!)
                    }
            list
        } catch (e: Exception) {
            Log.e(FirebaseUserService.TAG, "Error fetching wish list", e)
            FirebaseCrashlytics.getInstance().log("Error fetching wish list")
            FirebaseCrashlytics.getInstance().setCustomKey("fetch_wish_list", FirebaseUserService.TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }

    suspend fun isWishListed(prodDocId: String): Boolean? {
        return try {
            db.collection("USERS")
                    .document(userId)
                    .collection("WISHLIST")
                    .document(prodDocId)
//                    .addSnapshotListener { snapshot, e->
////                        if (e != null) {
////                            Log.w(TAG, "Listen failed.", e)
////                            return@addSnapshotListener
////                        }
//
//                        snapshot != null && snapshot.exists()
//                    }
                    .get().result?.exists()
            true
        } catch (e: Exception) {
            Log.e(FirebaseUserService.TAG, "Error getting wish list", e)
            FirebaseCrashlytics.getInstance().log("Error getting wish list")
            FirebaseCrashlytics.getInstance().setCustomKey("wishlist", FirebaseUserService.TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }
}