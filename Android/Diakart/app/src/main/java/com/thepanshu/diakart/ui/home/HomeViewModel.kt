package com.thepanshu.diakart.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.ProductDetailModel
import com.thepanshu.diakart.models.UserModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private var categoryList: MutableLiveData<List<CategoryModel>>? = null
    //private var productList: MutableLiveData<List<ProductDetailModel>>? = null


    internal fun getCategoryDetail(): MutableLiveData<List<CategoryModel>> {
        if (categoryList == null) {
            categoryList = MutableLiveData()
            loadCategoryList()
        }
        return categoryList as MutableLiveData<List<CategoryModel>>
    }


    private fun loadCategoryList() {
        viewModelScope.launch {
            val fetchedList = FirebaseListingsService.getCategory()
            categoryList?.postValue(fetchedList)
        }
    }


}