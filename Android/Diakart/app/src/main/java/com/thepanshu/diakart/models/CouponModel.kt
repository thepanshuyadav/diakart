package com.thepanshu.diakart.models

import android.os.Parcelable
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CouponModel(
    var title: String = "",
    var desc: String = "",
    var isCoupon: Boolean = true,
    var couponValue: String = "",
    var link: String = ""
):Parcelable {
    companion object {
        fun DocumentSnapshot.toCoupon(): CouponModel? {

            return try {
                val title = getString("title")!!
                val desc = getString("desc")!!
                val isCoupon = getBoolean("isCoupon")!!
                val link = getString("link")!!
                val couponValue  = getString("couponValue")!!
                CouponModel(title, desc, isCoupon, couponValue, link)
            } catch (e: Exception) {
//                Log.e(TAG, "Error converting user profile", e)
//                FirebaseCrashlytics.getInstance().log("Error converting user profile")
//                FirebaseCrashlytics.getInstance().setCustomKey("userId", id)
//                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }

        private const val TAG = "User"
    }
}
