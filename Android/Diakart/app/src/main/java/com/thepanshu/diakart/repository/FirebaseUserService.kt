package com.thepanshu.diakart.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
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
        val userRatingCollRef = db.collection("USERS")
                .document(userId)
                .collection("RATINGS")


        val doc = userRatingCollRef
                .whereEqualTo("productDocRef", prodDocId)
                .get()
                .result?.documents?.get(0)?.reference
        val productDoc = db.collection("PRODUCTS")
                .document(prodDocId.toString())
                .get().result?.reference

        if(doc != null && productDoc != null) {
            db.runTransaction {
                it.update(doc, "rating", rating)

                val snapshot = it.get(productDoc)
                val ratingList = snapshot.get("rating") as Array<Double>
                val prev = ratingList[rating]
                ratingList.set(rating, prev.toDouble())
                it.update(productDoc, "rating", ratingList)
            }
        }
        else {
            db.runTransaction {
                val map = hashMapOf("rating" to rating, "productDocRef" to prodDocId.toString())
                userRatingCollRef.add(map)

                val snapshot = it.get(productDoc!!)
                val ratingList = snapshot.get("rating") as Array<Double>
                val prev = ratingList[rating]
                ratingList.set(rating, prev.toDouble())
                it.update(productDoc, "rating", ratingList)
            }

        }
    }
}