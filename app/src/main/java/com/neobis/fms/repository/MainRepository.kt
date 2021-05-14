package com.neobis.fms.repository

import com.neobis.fms.model.addTransaction.AddIncomeOrExpense
import com.neobis.fms.model.addTransaction.AddTransfer
import com.neobis.fms.model.balance.TotalBalance
import com.neobis.fms.model.bankWallet.WalletResponseItem
import com.neobis.fms.model.category.CategoryResponse
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.category.SectionItem
import com.neobis.fms.model.transaction.TransactionItem
import com.neobis.fms.model.user.RegistrationModel
import com.neobis.fms.model.user.UpdateUser
import com.neobis.fms.model.user.UpdateUserBody
import com.neobis.fms.model.user.UserResponse
import com.neobis.fms.model.wallet.TransactionModel
import com.neobis.fms.model.wallet.WalletItem
import com.neobis.fms.network.RetrofitInstance
import okhttp3.ResponseBody
import retrofit2.Response

class MainRepository {


    suspend fun getAllTransactions(): Response<List<TransactionModel>> {
        return RetrofitInstance.api.getAllTransactions()
    }

    suspend fun getAllIncomeTransactions(): Response<List<TransactionModel>> {
        return RetrofitInstance.api.getAllIncomeTransactions()
    }

    suspend fun getAllExpenseTransactions(): Response<List<TransactionModel>> {
        return RetrofitInstance.api.getAllExpenseTransactions()
    }


    suspend fun getCurrentUser(): Response<UserResponse> {
        return RetrofitInstance.api.getCurrentUser()
    }

    suspend fun globalFiltration(
        startDate: String?,
        endDate: String?,
        walletId: Int?,
        categoryId: Int?,
        userId: Int?
    ): Response<List<TransactionModel>> {


        return RetrofitInstance.api.globalFiltration(
            startDate,
            endDate,
            walletId,
            categoryId,
            userId
        )
    }


    suspend fun getAllActiveWallets(): Response<List<WalletResponseItem>> {
        return RetrofitInstance.api.getAllActiveWallets()
    }

    suspend fun getWallets(): Response<List<WalletItem>> {
        return RetrofitInstance.api.getWallets()
    }

    suspend fun addWallet(walletResponseItem: WalletResponseItem): Response<WalletResponseItem> {

        return RetrofitInstance.api.addWallet(walletResponseItem)

    }

    suspend fun getTotalBalance(): Response<TotalBalance> {
        return RetrofitInstance.api.getTotalBalance()
    }

    suspend fun getCategories(): Response<List<CategoryResponseItem>> {
        return RetrofitInstance.api.getCategories()
    }


    suspend fun getUsers(): Response<List<UserResponse>> {
        return RetrofitInstance.api.getUsers()
    }

    suspend fun getSection(): Response<List<SectionItem>> {
        return RetrofitInstance.api.getSection()
    }

    suspend fun updateProfile(
        updateUserBody: UpdateUserBody
    ): Response<ResponseBody> {
        return RetrofitInstance.api.updateProfile(updateUserBody)
    }

    suspend fun addIncomeOrExpense(addIncomeOrExpense: AddIncomeOrExpense): Response<AddIncomeOrExpense> {
        return RetrofitInstance.api.addIncomeOrExpense(addIncomeOrExpense)
    }

    suspend fun addTransfer(addTransfer: AddTransfer): Response<AddTransfer> {
        return RetrofitInstance.api.addTransfer(addTransfer)
    }

    suspend fun getCategoryBySectionAndType(section: Int, type: Int): Response<CategoryResponse> {
        return RetrofitInstance.api.getCategoriesBySectionAndType(section, type)
    }

    suspend fun updateUser(updateUser: UpdateUser): Response<ResponseBody> {
        return RetrofitInstance.api.updateUser(updateUser)
    }

    suspend fun addUser(registrationModel: RegistrationModel): Response<RegistrationModel> {
        return RetrofitInstance.api.addUser(registrationModel)
    }

    suspend fun updateCategory(categoryResponseItem: CategoryResponseItem): Response<ResponseBody> {
        return RetrofitInstance.api.updateCategory(categoryResponseItem)
    }

    suspend fun addCategory(categoryResponseItem: CategoryResponseItem): Response<CategoryResponseItem> {
        return RetrofitInstance.api.addCategory(categoryResponseItem)
    }

    suspend fun updateWallet(walletResponseItem: WalletResponseItem): Response<ResponseBody> {
        return RetrofitInstance.api.updateWallet(walletResponseItem)
    }

    suspend fun getActiveCategories(): Response<CategoryResponse> {
        return RetrofitInstance.api.getActiveCategories()
    }


}