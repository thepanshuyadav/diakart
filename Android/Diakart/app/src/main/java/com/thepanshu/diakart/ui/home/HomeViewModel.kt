package com.thepanshu.diakart.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepanshu.diakart.models.CategoryModel
import com.thepanshu.diakart.models.SliderModel
import com.thepanshu.diakart.repository.FirebaseListingsService
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private var _categoryList: MutableLiveData<List<CategoryModel>>? = null
    private var _bannerList: MutableLiveData<ArrayList<SliderModel>>? = null

    internal fun getBannerList(): MutableLiveData<ArrayList<SliderModel>> {
        if (_bannerList == null) {
            _bannerList = MutableLiveData()
            loadBannerList()
        }
        return _bannerList as MutableLiveData<ArrayList<SliderModel>>
    }

    private fun loadBannerList() {
        viewModelScope.launch {
            val fetchedList = FirebaseListingsService.getBannerList()
            _bannerList?.postValue(fetchedList)
        }
    }

    internal fun getCategoryDetail(): MutableLiveData<List<CategoryModel>> {
        if (_categoryList == null) {
            _categoryList = MutableLiveData()
            loadCategoryList()
        }
        return _categoryList as MutableLiveData<List<CategoryModel>>
    }


    private fun loadCategoryList() {
        viewModelScope.launch {
            val fetchedList = FirebaseListingsService.getCategory()
            _categoryList?.postValue(fetchedList)
        }
    }


}