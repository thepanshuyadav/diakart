package com.thepanshu.diakart.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UserModel(
        val name: String,
        val profile_pic: String,
        val uuid: String,
        val email: String,
        val points: Int
): Parcelable {

    companion object {
        fun DocumentSnapshot.toUser(): UserModel? {
            return try {
                val name = getString("name")!!
                val imageUrl = getString("profile_pic")!!
                val uuid = getString("uuid")!!
                val email = getString("email")!!
                val points = getDouble("points")!!.toInt()
                UserModel(name, imageUrl, uuid, email, points)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting user profile", e)
                FirebaseCrashlytics.getInstance().log("Error converting user profile")
                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }

        private const val TAG = "User"
    }
}
