package com.neobis.fms.ui.fragments.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.fms.model.user.UpdateUserBody
import com.neobis.fms.model.user.UserResponse
import com.neobis.fms.repository.MainRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class ProfileFragmentViewModel(private val mainRepository: MainRepository) : ViewModel() {


    var responseCurrentUser: MutableLiveData<Response<UserResponse>> = MutableLiveData()

    var responseUpdateUserBody: MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    fun getCurrentUser() {
        viewModelScope.launch {
            val response = mainRepository.getCurrentUser()
            responseCurrentUser.value = response
        }
    }


    fun updateUserBody(updateUserBody: UpdateUserBody) {
        viewModelScope.launch {
            val response = mainRepository.updateProfile(updateUserBody)
            responseUpdateUserBody.value = response
        }
    }
}