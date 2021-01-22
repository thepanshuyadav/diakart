package com.thepanshu.diakart.ui.product_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.UserRatingModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {

    private var _rating: MutableLiveData<Int>? = null
    private var _is_wishlisted: MutableLiveData<Boolean>? = null

    //private var _product_rating: MutableLiveData<List<Int>>? = null


    internal fun isWishListed(prodDocId: String): MutableLiveData<Boolean> {
        if (_is_wishlisted == null) {
            _is_wishlisted = MutableLiveData()
            loadWishListedInfo(prodDocId)
        }
        return _is_wishlisted as MutableLiveData<Boolean>
    }


    private fun loadWishListedInfo(prodDocId: String) {
        viewModelScope.launch {
            _is_wishlisted?.postValue(FirebaseUserService.isWishListed(prodDocId))
            Log.d("LOAD", _is_wishlisted.toString())
        }
    }

    internal  fun addWishList(productDetailModel: ProductDetailModel):MutableLiveData<Boolean> {
        if (_is_wishlisted == null) {
            _is_wishlisted = MutableLiveData()
        }
        addToWishListFB(productDetailModel)
        return _is_wishlisted as MutableLiveData<Boolean>
    }

    private fun addToWishListFB(productDetailModel: ProductDetailModel) {
        viewModelScope.launch {
            _is_wishlisted?.value = FirebaseUserService.addToWishList(productDetailModel)
            Log.d("ADD", _is_wishlisted.toString())
        }
    }

    internal fun removeFromWishList(prodDocId: String):MutableLiveData<Boolean> {
        if (_is_wishlisted == null) {
            _is_wishlisted = MutableLiveData()
        }
        removeFromWishListFB(prodDocId)
        return _is_wishlisted as MutableLiveData<Boolean>
    }

    private fun removeFromWishListFB(prodDocId: String) {
        viewModelScope.launch {
            _is_wishlisted?.value = FirebaseUserService.removeFromWishList(prodDocId).not()
            Log.d("REMOVE", _is_wishlisted.toString())
        }
    }

    //TODO: Get current rating
    internal fun getRating(id: String): MutableLiveData<Int> {
        if (_rating == null) {
            _rating = MutableLiveData()
            fetchRating(id)
        }
        return _rating as MutableLiveData<Int>
    }
    private fun fetchRating(prodDocId: String) {
        viewModelScope.launch {
            _rating?.postValue(FirebaseUserService.getRating(prodDocId))
        }
    }

    fun setProductRating(rating: UserRatingModel, id: String) {
        viewModelScope.launch {
            FirebaseUserService.updateRating(rating, id)
        }
    }

}