package com.thepanshu.diakart.repository

import android.util.Log
import android.widget.NumberPicker
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.events.Event
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import com.thepanshu.diakart.models.*
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

    suspend fun redeemPoints(inviteCode: String): Boolean {
        return try {
            db.runTransaction {
                    val inviteeDocRef = db.collection("USERS").document(userId)
                    val inviterDocRef  = db.collection("USERS").document(inviteCode)

                    val inviteeSnapshot = it.get(inviteeDocRef)
                    val inviterSnapshot = it.get(inviterDocRef)

                    Log.d("REDEEM", inviteeSnapshot.toString())
                    Log.d("REDEEM", inviterSnapshot.toString())

                    val invitedBy = inviteeSnapshot.getString("invitedBy")
                    if(invitedBy == null) {
                        val inviteePoints = inviteeSnapshot.getDouble("points")!! + 5
                        val inviterPoints = inviterSnapshot.getDouble("points")!! + 5

                        it.update(inviterDocRef, "points", inviterPoints)
                        it.update(inviteeDocRef, "points", inviteePoints)
                        it.update(inviteeDocRef, "invitedBy", inviteCode)
                    }
            }.isSuccessful
        } catch (e: Exception) {
//            Log.e(TAG, "Error getting user's product rating", e)
//            FirebaseCrashlytics.getInstance().log("Error getting user's product rating")
//            FirebaseCrashlytics.getInstance().setCustomKey("user_rating", TAG)
//            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }

    }

    suspend fun updateRating(rating: UserRatingModel, prodDocId: String) {

        db.runTransaction { transaction->
            transaction.set(db.collection("USERS")
                    .document(userId)
                    .collection("RATINGS")
                    .document(prodDocId),
                    rating)
        }
    }
    suspend fun getRating(prodDocId: String): Int {
        return try {
            db.collection("USERS")
                .document(userId)
                .collection("RATINGS")
                .document(prodDocId).get().await()["rating"].toString().toInt()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user's product rating", e)
            FirebaseCrashlytics.getInstance().log("Error getting user's product rating")
            FirebaseCrashlytics.getInstance().setCustomKey("user_rating", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            0
        }
    }

    suspend fun getUserRatingList() : List<UserRatingModel>
    {
        return try {
            db.collection("USERS")
                .document(userId)
                .collection("RATINGS")
                .get()
                .await()
                .documents.mapNotNull {
                    it.toObject(UserRatingModel::class.java)
                }
        } catch (e: Exception) {
//            Log.e(FirebaseUserService.TAG, "Error getting user details", e)
//            FirebaseCrashlytics.getInstance().log("Error getting user details")
//            FirebaseCrashlytics.getInstance().setCustomKey("user", FirebaseUserService.TAG)
//            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
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
}