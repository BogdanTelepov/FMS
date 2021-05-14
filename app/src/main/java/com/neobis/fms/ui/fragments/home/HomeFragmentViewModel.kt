package com.neobis.fms.ui.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neobis.fms.model.balance.TotalBalance
import com.neobis.fms.model.bankWallet.WalletResponseItem
import com.neobis.fms.model.transaction.TransactionItem
import com.neobis.fms.model.wallet.TransactionModel
import com.neobis.fms.model.wallet.WalletResponse
import com.neobis.fms.repository.MainRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeFragmentViewModel(private val mainRepository: MainRepository) : ViewModel() {


    var responseListTransactions: MutableLiveData<Response<List<TransactionModel>>> =
        MutableLiveData()

    var responseListIncomeTransactions: MutableLiveData<Response<List<TransactionModel>>> =
        MutableLiveData()

    var responseListExpenseTransactions: MutableLiveData<Response<List<TransactionModel>>> =
        MutableLiveData()

    var responseListActiveWallets: MutableLiveData<Response<List<WalletResponseItem>>> =
        MutableLiveData()


    var responseWalletItemList: MutableLiveData<Response<WalletResponseItem>> = MutableLiveData()

    var responseGetTotalBalance: MutableLiveData<Response<TotalBalance>> = MutableLiveData()

    fun getAllTransactions() {
        viewModelScope.launch {
            val response = mainRepository.getAllTransactions()
            responseListTransactions.value = response
        }
    }

    fun globalFiltration(
        startDate: String?,
        endDate: String?,
        walletId: Int?,
        categoryId: Int?,
        userId: Int?
    ) {
        viewModelScope.launch {
            val response =
                mainRepository.globalFiltration(startDate, endDate, walletId, categoryId, userId)
            responseListTransactions.value = response
        }

    }

    fun getAllIncomeTransactions() {
        viewModelScope.launch {
            val response = mainRepository.getAllIncomeTransactions()
            responseListIncomeTransactions.value = response
        }
    }


    fun getAllExpenseTransactions() {
        viewModelScope.launch {
            val response = mainRepository.getAllExpenseTransactions()
            responseListExpenseTransactions.value = response
        }
    }

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

    fun getTotalBalance() {
        viewModelScope.launch {
            val response = mainRepository.getTotalBalance()
            responseGetTotalBalance.value = response
        }
    }

}