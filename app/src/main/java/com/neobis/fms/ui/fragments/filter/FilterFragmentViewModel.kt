package com.neobis.fms.ui.fragments.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.user.UserResponse
import com.neobis.fms.model.wallet.WalletItem
import com.neobis.fms.repository.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class FilterFragmentViewModel(private val mainRepository: MainRepository) : ViewModel() {


    var getWalletsResponse: MutableLiveData<Response<List<WalletItem>>> = MutableLiveData()
    var getCategoriesResponse: MutableLiveData<Response<List<CategoryResponseItem>>> =
        MutableLiveData()

    var getUsersResponse: MutableLiveData<Response<List<UserResponse>>> = MutableLiveData()


    fun getWallets() {
        viewModelScope.launch {
            val response = mainRepository.getWallets()
            getWalletsResponse.value = response
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val response = mainRepository.getCategories()
            getCategoriesResponse.value = response
        }
    }


    fun getUsers() {
        viewModelScope.launch {
            val response = mainRepository.getUsers()
            getUsersResponse.value = response
        }
    }

}