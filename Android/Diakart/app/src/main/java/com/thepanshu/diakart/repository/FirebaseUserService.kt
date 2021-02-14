package com.thepanshu.diakart.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
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

    fun redeemPoints(inviteCode: String): Boolean {
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
            FirebaseCrashlytics.getInstance().log("Error redeeming points")
            FirebaseCrashlytics.getInstance().setCustomKey("redeem_points", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }

    }

    fun updateRating(userRating: UserRatingModel, id: String) {

        db.runTransaction { transaction ->
            val oldRatingSnap = transaction.get(db.collection("USERS")
                    .document(userId)
                    .collection("RATINGS")
                    .document(id))
            val product = transaction.get(db.collection("PRODUCTS")
                    .document(id)).toObject(ProductDetailModel::class.java)!!
            var old = -1
            if(oldRatingSnap.exists()) {
                old = oldRatingSnap["rating"].toString().toInt()
            }
            Log.d("RATING", old.toString())
            product.rating[userRating.rating-1] = product.rating[userRating.rating-1] +1
            if(old != -1) {
                product.rating[old-1] = product.rating[old-1]-1
            }
            Log.d("RATING", product.toString())
            transaction.set(db.collection("PRODUCTS")
                    .document(id), product)
            transaction.set(db.collection("USERS")
                    .document(userId)
                    .collection("RATINGS")
                    .document(id),
                    userRating)
        }
    }
    suspend fun getRating(prodDocId: String): Int? {
        return try {
            val documentSnapshot = db.collection("USERS")
                .document(userId)
                .collection("RATINGS")
                .document(prodDocId).get().await()

            if(!documentSnapshot.exists()) {
                return -1
            }
            return documentSnapshot["rating"].toString().toInt()
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error getting user's product rating")
            FirebaseCrashlytics.getInstance().setCustomKey("user_product_rating", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
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
            FirebaseCrashlytics.getInstance().log("Error getting user ratings")
            FirebaseCrashlytics.getInstance().setCustomKey("get_user_rating", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }

    fun addToWishList(product: ProductDetailModel): Boolean {
        return try {
            db.runTransaction { transaction ->
                transaction.set(db.collection("USERS")
                        .document(userId)
                        .collection("WISHLIST")
                        .document(product.documentId),
                        product)
            }.isSuccessful
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error adding to wish list")
            FirebaseCrashlytics.getInstance().setCustomKey("add_to_wish_list", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }


    }

    fun removeFromWishList(prodDocId: String): Boolean {
        return try {
            db.runTransaction { transaction->
                transaction.delete(db.collection("USERS")
                        .document(userId)
                        .collection("WISHLIST")
                        .document(prodDocId))
            }.isSuccessful
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().log("Error removing from wish list")
            FirebaseCrashlytics.getInstance().setCustomKey("remove_wish_list", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
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
            FirebaseCrashlytics.getInstance().log("Error fetching wish list")
            FirebaseCrashlytics.getInstance().setCustomKey("fetch_wish_list", TAG)
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }
}