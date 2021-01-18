package com.thepanshu.diakart.ui.product_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {

    private var _product: MutableLiveData<ProductDetailModel>? = null
    private var _product_rating: MutableLiveData<List<Int>>? = null


    internal fun getProductDetail(path: DocumentReference): MutableLiveData<ProductDetailModel> {
        if (_product == null) {
            _product = MutableLiveData()
            loadCategoryList(path)
        }
        return _product as MutableLiveData<ProductDetailModel>
    }


    private fun loadCategoryList(path: DocumentReference) {
        viewModelScope.launch {
            val fetchedProduct = FirebaseListingsService.getProductDetail(path)
            _product?.postValue(fetchedProduct)
        }
    }

    internal fun getProductRating(docId: String): MutableLiveData<List<Int>> {
        if ( _product_rating == null) {
            _product_rating = MutableLiveData()
            loadProductRatings(docId)
        }
        return  _product_rating as MutableLiveData<List<Int>>
    }


    private fun loadProductRatings(docId: String) {
        viewModelScope.launch {
//            val fetchedProductRating = FirebaseListingsService.getProductRating(docId)
//            _product_rating?.postValue(fetchedProductRating)
        }
    }
//    TODO: Post rating
//    fun setProductRating(rating: Int, id: String) {
//        viewModelScope.launch {
//            FirebaseUserService.updateRating(rating, id)
//        }
//    }

}