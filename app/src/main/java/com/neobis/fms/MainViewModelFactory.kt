package com.neobis.fms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.addTransaction.AddTransactionViewModel
import com.neobis.fms.ui.fragments.home.HomeFragmentViewModel
import com.neobis.fms.ui.fragments.filter.FilterFragmentViewModel
import com.neobis.fms.ui.fragments.profile.ProfileFragmentViewModel
import com.neobis.fms.ui.fragments.settings.SettingsViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> HomeFragmentViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(ProfileFragmentViewModel::class.java) -> ProfileFragmentViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(AddTransactionViewModel::class.java) -> AddTransactionViewModel(
                mainRepository
            ) as T


            modelClass.isAssignableFrom(FilterFragmentViewModel::class.java) -> FilterFragmentViewModel(
                mainRepository
            ) as T

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel(
                mainRepository
            ) as T

            else -> throw IllegalArgumentException("ViewModel not found :(")
        }
    }
}