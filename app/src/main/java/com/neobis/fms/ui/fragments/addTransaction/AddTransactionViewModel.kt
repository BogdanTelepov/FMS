package com.neobis.fms.ui.fragments.addTransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.fms.model.addTransaction.AddIncomeOrExpense
import com.neobis.fms.model.addTransaction.AddTransfer
import com.neobis.fms.model.category.CategoryResponse
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.category.SectionItem
import com.neobis.fms.model.wallet.WalletItem
import com.neobis.fms.repository.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class AddTransactionViewModel(private val mainRepository: MainRepository) : ViewModel() {
    var responseAllWalletList: MutableLiveData<Response<List<WalletItem>>> =
        MutableLiveData()
    var responseAllSection: MutableLiveData<Response<List<SectionItem>>> =
        MutableLiveData()
    var getCategoriesResponse: MutableLiveData<Response<List<CategoryResponseItem>>> =
        MutableLiveData()
    var addIncomeOrExpenseResponse: MutableLiveData<Response<AddIncomeOrExpense>> =
        MutableLiveData()
    var addTransferResponse: MutableLiveData<Response<AddTransfer>> =
        MutableLiveData()
    var getCategoriesBySectionAndType: MutableLiveData<Response<CategoryResponse>> =
        MutableLiveData()

    fun getAllActiveWallets() {
        viewModelScope.launch {
            val response = mainRepository.getWallets()
            responseAllWalletList.value = response
        }
    }

    fun getSection() {
        viewModelScope.launch {
            val response = mainRepository.getSection()
            responseAllSection.value = response
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val response = mainRepository.getCategories()
            getCategoriesResponse.value = response
        }
    }

    fun addIncomeOrExpense(addIncomeOrExpense: AddIncomeOrExpense) {
        viewModelScope.launch {
            val response = mainRepository.addIncomeOrExpense(addIncomeOrExpense)
            addIncomeOrExpenseResponse.value = response
        }
    }

    fun addTransfer(addTransfer: AddTransfer) {
        viewModelScope.launch {
            val response = mainRepository.addTransfer(addTransfer)
            addTransferResponse.value = response
        }
    }

    fun getCategoryBySectionAndType(section: Int, type: Int) {
        viewModelScope.launch {
            val response = mainRepository.getCategoryBySectionAndType(section, type)
            getCategoriesBySectionAndType.value = response
        }
    }
}