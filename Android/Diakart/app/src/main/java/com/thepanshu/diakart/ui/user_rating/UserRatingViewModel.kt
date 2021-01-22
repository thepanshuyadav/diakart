package com.thepanshu.diakart.ui.user_rating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepanshu.diakart.models.UserRatingModel
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class UserRatingViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var productsList: MutableLiveData<List<UserRatingModel>>? = null

    internal fun getProductsList(): MutableLiveData<List<UserRatingModel>> {
        if (productsList == null) {
            productsList = MutableLiveData()
            loadProductsList()
        }
        return productsList as MutableLiveData<List<UserRatingModel>>
    }

    private fun loadProductsList() {
        viewModelScope.launch {
            productsList?.value = FirebaseUserService.getUserRatingList()
        }

    }
}