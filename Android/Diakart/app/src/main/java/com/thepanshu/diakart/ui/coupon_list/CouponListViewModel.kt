package com.thepanshu.diakart.ui.coupon_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepanshu.diakart.models.CouponModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import kotlinx.coroutines.launch

class CouponListViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var couponsList: MutableLiveData<List<CouponModel>>? = null

    internal fun getCouponsList(): MutableLiveData<List<CouponModel>> {
        if (couponsList == null) {
            couponsList = MutableLiveData()
            loadProductsList()
        }
        return couponsList as MutableLiveData<List<CouponModel>>
    }

    private fun loadProductsList() {
        viewModelScope.launch {
            couponsList?.value = FirebaseListingsService.getCouponList()
            Log.d("COUPON", couponsList.toString())
        }

    }
}