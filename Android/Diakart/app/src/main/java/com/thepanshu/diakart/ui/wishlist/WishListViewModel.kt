package com.thepanshu.diakart.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thepanshu.diakart.data.Product
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class WishListViewModel : ViewModel() {

    private var wishList: MutableLiveData<List<Product>>? = null
    private var isFetching: LiveData<Boolean>? = null

    internal fun getWishList(): MutableLiveData<List<Product>> {
        if (wishList == null) {
            wishList = MutableLiveData()
            loadWishList()
        }
        return wishList as MutableLiveData<List<Product>>
    }

    private fun loadWishList() {
        // do async operation to fetch users
        Timer("SettingUp", false).schedule(5000) {
            val fetchedWishList = ArrayList<Product>()
            wishList!!.postValue(fetchedWishList)
        }
    }
}