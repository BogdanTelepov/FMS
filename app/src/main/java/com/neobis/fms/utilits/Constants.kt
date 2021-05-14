package com.neobis.fms.utilits

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import com.neobis.fms.R
import com.neobis.fms.model.category.CategoryIdName
import com.neobis.fms.model.user.UserIdName
import com.neobis.fms.model.wallet.WalletIdName
import com.neobis.fms.ui.fragments.addTransaction.CategoryName
import com.neobis.fms.ui.fragments.addTransaction.SectionName
import com.neobis.fms.ui.fragments.addTransaction.WalletCurrent

const val BASE_URL = "https://fms-team2.herokuapp.com"


const val UNAUTHORIZED_CODE = 401
const val SUCCESS_CODE = 200

const val LOGOUT = "LOGOUT"
const val FORCE_UPDATE = "FORCE_UPDATE"

fun convertData(data: String): String {
    return data.substring(0, 10).replace('-', '.')
}

fun spinnerWalletFilter(
    context: Context,
    arrayList: ArrayList<WalletCurrent>,
    autoCompleteTextView: AutoCompleteTextView
) {
    val adapter = ArrayAdapter(
        context,
        R.layout.dropdown_item, arrayList
    )

    autoCompleteTextView.setAdapter(adapter)
}


fun customSpinnerWalletList(
    context: Context,
    arrayList: ArrayList<WalletIdName>,
    autoCompleteTextView: AutoCompleteTextView
) {
    val adapter = ArrayAdapter(context, R.layout.dropdown_item, arrayList)
    autoCompleteTextView.setAdapter(adapter)
}

fun customSpinnerCategoryList(
    context: Context,
    arrayList: ArrayList<CategoryIdName>,
    autoCompleteTextView: AutoCompleteTextView
) {
    val adapter = ArrayAdapter(context, R.layout.dropdown_item, arrayList)
    autoCompleteTextView.setAdapter(adapter)
}

fun customSpinnerUserList(
    context: Context,
    arrayList: ArrayList<UserIdName>,
    autoCompleteTextView: AutoCompleteTextView
) {
    val adapter = ArrayAdapter(context, R.layout.dropdown_item, arrayList)
    autoCompleteTextView.setAdapter(adapter)
}


fun spinnerCategoryFilter(
    context: Context,
    arrayList: ArrayList<CategoryName>,
    autoCompleteTextView: AutoCompleteTextView
) {
    val adapter = ArrayAdapter(
        context,
        R.layout.dropdown_item, arrayList
    )

    autoCompleteTextView.setAdapter(adapter)
}


fun spinnerSectionFilter(context: Context, autoCompleteTextView: AutoCompleteTextView) {
    val sectionNameArray: ArrayList<SectionName> = ArrayList()
    sectionNameArray.add(SectionName(0, "NEOBIS"))
    sectionNameArray.add(SectionName(1, "NEOLABS"))
    val adapter = ArrayAdapter(
        context,
        R.layout.dropdown_item, sectionNameArray
    )

    autoCompleteTextView.setAdapter(adapter)
}