package com.neobis.fms.network

import com.neobis.fms.model.LoginRequest
import com.neobis.fms.model.LoginResponse
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
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Api {


    @Headers("Content-Type: application/json")
    @POST("/login")
    fun login(@Body userInfo: LoginRequest): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @GET("/transaction/getAll")
    suspend fun getAllTransactions(): Response<List<TransactionModel>>


    @Headers("Content-Type: application/json")
    @GET("/transaction/getByGlobalFiltration")
    suspend fun getAllIncomeTransactions(
            @Query("transactionTypeId") transactionTypeId: Int = 0
    ): Response<List<TransactionModel>>


    @Headers("Content-Type: application/json")
    @GET("/transaction/getByGlobalFiltration")
    suspend fun getAllExpenseTransactions(
            @Query("transactionTypeId") transactionTypeId: Int = 1
    ): Response<List<TransactionModel>>


    @Headers("Content-Type: application/json")
    @GET("/user/getCurrentUser")
    suspend fun getCurrentUser(): Response<UserResponse>


    @Headers("Content-Type: application/json")
    @GET("/wallet/getAllActiveWallets")
    suspend fun getAllActiveWallets(): Response<List<WalletResponseItem>>


    @Headers("Content-Type: application/json")
    @GET("/wallet/getAllActiveWallets")
    suspend fun getWallets(): Response<List<WalletItem>>

    @Headers("Content-Type: application/json")
    @GET("/transaction/getByGlobalFiltration")
    suspend fun globalFiltration(
            @Query("startDate") startDate: String?,
            @Query("endDate") endDate: String?,
            @Query("walletId") walletId: Int? = null,
            @Query("categoryId") categoryId: Int? = null,
            @Query("userId") userId: Int? = null
    ): Response<List<TransactionModel>>

    @Headers("Content-Type: application/json")
    @POST("/wallet/add")
    suspend fun addWallet(
            @Body walletResponseItem: WalletResponseItem
    ): Response<WalletResponseItem>


    @Headers("Content-Type: application/json")
    @GET("/wallet/getTotalBalance")
    suspend fun getTotalBalance(): Response<TotalBalance>


    @Headers("Content-Type: application/json")
    @GET("/category/getAllActiveCategories")
    suspend fun getCategories(): Response<List<CategoryResponseItem>>

    @Headers("Content-Type: application/json")
    @GET("/user/getAllUsers")
    suspend fun getUsers(): Response<List<UserResponse>>

    @Headers("Content-Type: application/json")
    @GET("/category/getNeoSections")
    suspend fun getSection(): Response<List<SectionItem>>


    @Headers("Content-Type: application/json")
    @PUT("/user/updateProfileWithPassword")
    suspend fun updateProfile(
            @Body updateUserBody: UpdateUserBody
    ): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/transaction/addIncomeOrExpense")
    suspend fun addIncome(@Body addIncomeOrExpense: AddIncomeOrExpense): Response<AddIncomeOrExpense>

    @Headers("Content-Type: application/json")
    @POST("/transaction/addIncomeOrExpense")
    suspend fun addIncomeOrExpense(@Body addIncomeOrExpense: AddIncomeOrExpense): Response<AddIncomeOrExpense>

    @Headers("Content-Type: application/json")
    @POST("/transaction/addTransfer")
    suspend fun addTransfer(@Body addTransfer: AddTransfer): Response<AddTransfer>

    @Headers("Content-Type: application/json")
    @GET("/category/getAllActiveCategoriesBySectionAndType")
    suspend fun getCategoriesBySectionAndType(
            @Query("neoSectionId") section: Int,
            @Query("transactionTypeId") type: Int
    ): Response<CategoryResponse>

    @Headers("Content-Type: application/json")
    @PUT("/user/updateUser")
    suspend fun updateUser(@Body updateUser: UpdateUser): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @PUT("/category/update")
    suspend fun updateCategory(@Body categoryResponseItem: CategoryResponseItem): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @POST("/category/add")
    suspend fun addCategory(@Body categoryResponseItem: CategoryResponseItem): Response<CategoryResponseItem>

    @Headers("Content-Type: application/json")
    @POST("/registration/newAccountant")
    suspend fun addUser(@Body registrationModel: RegistrationModel): Response<RegistrationModel>

    @Headers("Content-Type: application/json")
    @PUT("/wallet/update")
    suspend fun updateWallet(@Body walletResponseItem: WalletResponseItem): Response<ResponseBody>

    @Headers("Content-Type: application/json")
    @GET("/category/getAllActiveCategories")
    suspend fun getActiveCategories(): Response<CategoryResponse>



}