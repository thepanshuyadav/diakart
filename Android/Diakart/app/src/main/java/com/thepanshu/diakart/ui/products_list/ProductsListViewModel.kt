package com.thepanshu.diakart.ui.products_list

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.thepanshu.diakart.R
import com.thepanshu.diakart.data.Category
import com.thepanshu.diakart.data.Product
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

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
        // do async operation to fetch users
        viewModelScope.launch {
            productsList?.value = FirebaseListingsService.getProductList(path)
        }

//        val db = Firebase.firestore
//        Timer("SettingUp", false).schedule(5000) {
//            val fetchedProductsList = ArrayList<Product>()
//
//            Log.d("PROD", fetchedProductDetailList.toString())
//            // LISTING/CHOCOLATE
//            productsList!!.postValue(fetchedProductsList)
//
//        }

    }
}