package com.neobis.fms.ui.fragments.filter

import android.content.Context

class FiltrationManager(context: Context) {

    private val filterPreferences = context.getSharedPreferences("Filtration", Context.MODE_PRIVATE)


    companion object {
        const val START_DATE = "startDate"
        const val END_DATE = "endDate"
        const val USER_ID = "0"
        const val WALLET_ID = "0"
        const val CATEGORY_ID = "0"

    }


    fun saveQueries(startDate: String?, endDate: String?) {
        val editor = filterPreferences.edit()
        editor.putString(START_DATE, startDate)
        editor.putString(END_DATE, endDate)
        editor.apply()
    }

    fun saveQueries(walletID: Int?, categoryId: Int?, userId: Int?) {
        val editor = filterPreferences.edit()
        editor.putString(WALLET_ID.toString(), walletID.toString())
        editor.putString(CATEGORY_ID.toString(), categoryId.toString())
        editor.putString(USER_ID.toString(), userId.toString())
        editor.apply()

    }

    fun saveQueries(
        startDate: String?,
        endDate: String?,
        walletID: Int?,
        categoryId: Int?,
        userId: Int?
    ) {
        val editor = filterPreferences.edit()
        editor.putString(START_DATE, startDate)
        editor.putString(END_DATE, endDate)
        editor.putString(WALLET_ID.toString(), walletID.toString())
        editor.putString(CATEGORY_ID.toString(), categoryId.toString())
        editor.putString(USER_ID.toString(), userId.toString())
        editor.apply()

    }


    fun fetchStartDate(): String? {
        return filterPreferences.getString(START_DATE, "2021-01-01")
    }

    fun fetchEndDate(): String? {
        return filterPreferences.getString(END_DATE, "2021-12-31")
    }

    fun fetchWalletId(): String? {
        return filterPreferences.getString(WALLET_ID.toString(), "0")
    }

    fun fetchCategoryId(): String? {
        return filterPreferences.getString(CATEGORY_ID.toString(), "0")
    }

    fun fetchUserId(): String? {
        return filterPreferences.getString(USER_ID.toString(), "0")
    }

    fun clearData() {
        filterPreferences.edit().clear().apply()
    }


}