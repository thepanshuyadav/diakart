package com.thepanshu.diakart.ui.product_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {

    //private var _product: MutableLiveData<ProductDetailModel>? = null
    private var _is_wishlisted: MutableLiveData<Boolean>? = null

    //private var _product_rating: MutableLiveData<List<Int>>? = null


    internal fun isWishListed(prodDocId: String): MutableLiveData<Boolean> {
        if (_is_wishlisted == null) {
            _is_wishlisted = MutableLiveData()
            loadWishListedInfo(prodDocId)
        }
        return _is_wishlisted as MutableLiveData<Boolean>
    }


    internal fun loadWishListedInfo(prodDocId: String) {
        viewModelScope.launch {
            val isPresent = FirebaseUserService.isWishListed(prodDocId)
            _is_wishlisted?.postValue(isPresent)
        }
    }

    internal fun addToWishList(productDetailModel: ProductDetailModel) {
        viewModelScope.launch {
            _is_wishlisted?.postValue(FirebaseUserService.addToWishList(productDetailModel))
        }
    }

    internal fun removeFromWishList(prodDocId: String) {
        viewModelScope.launch {
            _is_wishlisted?.postValue(FirebaseUserService.removeFromWishList(prodDocId))
        }
    }

    //TODO: Get current rating
//    internal fun postRating(rating: Int, id: String): MutableLiveData<ProductDetailModel> {
//        if (_product == null) {
//            _product = MutableLiveData()
//            loadCategoryList(path)
//        }
//        return _product as MutableLiveData<ProductDetailModel>
//    }

    fun setProductRating(rating: Int, id: String) {
        viewModelScope.launch {
            FirebaseUserService.updateRating(rating, id)
        }
    }

}