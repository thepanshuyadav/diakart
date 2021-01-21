package com.thepanshu.diakart.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class WishListViewModel : ViewModel() {

    private var wishList: MutableLiveData<List<ProductDetailModel>>? = null
    //private var isFetching: LiveData<Boolean>? = null

    internal fun getWishList(): MutableLiveData<List<ProductDetailModel>> {
        if (wishList == null) {
            wishList = MutableLiveData()
            loadWishList()
        }
        return wishList as MutableLiveData<List<ProductDetailModel>>
    }

    private fun loadWishList() {
        // do async operation to fetch users
        viewModelScope.launch {
            wishList?.value = FirebaseUserService.fetchWishList()
        }
    }
}