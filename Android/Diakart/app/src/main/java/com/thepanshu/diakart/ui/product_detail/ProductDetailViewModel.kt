package com.thepanshu.diakart.ui.product_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import kotlinx.coroutines.launch

class ProductDetailViewModel: ViewModel() {

    // TODO: Post rating

    private var _product: MutableLiveData<ProductDetailModel>? = null


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
}