package com.neobis.fms.ui.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.fms.model.bankWallet.WalletResponseItem
import com.neobis.fms.model.category.CategoryResponse
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.user.RegistrationModel
import com.neobis.fms.model.user.UpdateUser
import com.neobis.fms.model.user.UserResponse
import com.neobis.fms.model.wallet.WalletItem
import com.neobis.fms.repository.MainRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response


class SettingsViewModel(private val mainRepository: MainRepository) : ViewModel() {
    var responseListActiveWallets: MutableLiveData<Response<List<WalletResponseItem>>> =
        MutableLiveData()
    var responseWalletItemList: MutableLiveData<Response<WalletResponseItem>> = MutableLiveData()
    var responseWalletEdit: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var responseArchiveWallet: MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    var responseUserList: MutableLiveData<Response<List<UserResponse>>> = MutableLiveData()
    var responseUserAdd: MutableLiveData<Response<RegistrationModel>> = MutableLiveData()
    var responseUserEdit: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var responseArchiveUser: MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    var responseCategoryList: MutableLiveData<Response<CategoryResponse>> = MutableLiveData()
    var responseCategoryExpList: MutableLiveData<Response<CategoryResponse>> = MutableLiveData()
    var responseExpCategoryAdd: MutableLiveData<Response<CategoryResponseItem>> = MutableLiveData()
    var responseIncCategoryAdd: MutableLiveData<Response<CategoryResponseItem>> = MutableLiveData()
    var responseCategoryUpdate: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var responseCategoreExpUpdate: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var responseArchiveExpense: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var responseArchiveIncome: MutableLiveData<Response<ResponseBody>> = MutableLiveData()

    fun getAllActiveWallets() {
        viewModelScope.launch {
            val response = mainRepository.getAllActiveWallets()
            responseListActiveWallets.value = response
        }
    }

    fun addWallet(walletResponseItem: WalletResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.addWallet(walletResponseItem)
            responseWalletItemList.value = response
        }

    }

    fun updateWallet(walletResponseItem: WalletResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.updateWallet(walletResponseItem)
            responseWalletEdit.value = response
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            val response = mainRepository.getUsers()
            responseUserList.value = response
        }
    }

    fun addUser(registrationModel: RegistrationModel) {
        viewModelScope.launch {
            val response = mainRepository.addUser(registrationModel)
            responseUserAdd.value = response
        }
    }

    fun updateUser(updateUser: UpdateUser) {
        viewModelScope.launch {
            val response = mainRepository.updateUser(updateUser)
            responseUserEdit.value = response
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val response = mainRepository.getActiveCategories()
            responseCategoryList.value = response
        }
    }

    fun getExpCategories() {
        viewModelScope.launch {
            val response = mainRepository.getActiveCategories()
            responseCategoryExpList.value = response
        }
    }

    fun addExpCategories(categoryResponseItem: CategoryResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.addCategory(categoryResponseItem)
            responseExpCategoryAdd.value = response
        }

    }

    fun addIncCategories(categoryResponseItem: CategoryResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.addCategory(categoryResponseItem)
            responseIncCategoryAdd.value = response
        }

    }


    fun updateCategory(categoryResponseItem: CategoryResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.updateCategory(categoryResponseItem)
            responseCategoryUpdate.value = response
        }
    }

    fun updateExpCategory(categoryResponseItem: CategoryResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.updateCategory(categoryResponseItem)
            responseCategoreExpUpdate.value = response
        }
    }

    fun archiveExpense(categoryResponseItem: CategoryResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.updateCategory(categoryResponseItem)
            responseArchiveExpense.value = response
        }
    }

    fun archiveIncome(categoryResponseItem: CategoryResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.updateCategory(categoryResponseItem)
            responseArchiveIncome.value = response
        }
    }

    fun archiveWallet(walletResponseItem: WalletResponseItem) {
        viewModelScope.launch {
            val response = mainRepository.updateWallet(walletResponseItem)
            responseArchiveWallet.value = response
        }
    }

    fun archiveUser(updateUser: UpdateUser) {
        viewModelScope.launch {
            val response = mainRepository.updateUser(updateUser)
            responseArchiveUser.value = response
        }
    }


}
