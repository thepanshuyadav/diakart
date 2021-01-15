package com.thepanshu.diakart.ui.products_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import kotlinx.coroutines.launch

class ProductsListViewModel : ViewModel() {

    private var productsList: MutableLiveData<List<ProductDetailModel>>? = null

    internal fun getProductsList(path: DocumentReference): MutableLiveData<List<ProductDetailModel>> {
        if (productsList == null) {
            productsList = MutableLiveData()
            loadProductsList(path)
        }
        return productsList as MutableLiveData<List<ProductDetailModel>>
    }

    private fun loadProductsList(path: DocumentReference) {
        viewModelScope.launch {
            productsList?.value = FirebaseListingsService.getProductList(path)
        }

    }
}