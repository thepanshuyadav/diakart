package com.thepanshu.diakart.ui.account

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thepanshu.diakart.models.UserModel
import com.thepanshu.diakart.repository.FirebaseUserService
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private var user: MutableLiveData<UserModel>? = null
    private var redeemProcessRes: MutableLiveData<Boolean>? = null

    internal fun getUserDetail(): MutableLiveData<UserModel> {
        if (user == null) {
            user = MutableLiveData()
            loadUserInfo()
        }
        return user as MutableLiveData<UserModel>
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            user?.value = FirebaseUserService.getProfileData()
        }
    }

    internal fun redeemPoints(inviteCode: String): MutableLiveData<Boolean> {
        if (redeemProcessRes == null) {
            redeemProcessRes = MutableLiveData()
        }
        redeemPointsFB(inviteCode)
        return redeemProcessRes as MutableLiveData<Boolean>
    }

    private fun redeemPointsFB(inviteCode: String) {
        viewModelScope.launch {
            FirebaseUserService.redeemPoints(inviteCode)
        }
    }



}