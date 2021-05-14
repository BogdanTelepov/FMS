package com.neobis.fms.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.FragmentBottomSheetAddBankWalletBinding
import com.neobis.fms.model.bankWallet.WalletResponseItem
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.home.HomeFragmentViewModel


class BottomSheetAddBankWalletFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetAddBankWalletBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeFragmentViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetAddBankWalletBinding.inflate(layoutInflater, container, false)


        val repository = MainRepository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        homeViewModel =
            ViewModelProvider(this, mainViewModelFactory).get(HomeFragmentViewModel::class.java)


        binding.btnCreateBankAccount.setOnClickListener {
            val walletName = binding.editInputBankAccountName.text.toString().trim()
            val walletAmount = binding.editInputBankAccountAmount.text.toString()

            if (walletName.isEmpty()) {
                binding.editInputBankAccountName.error = "Пустое поле"
                return@setOnClickListener
            }

            if (walletAmount.isEmpty()) {
                binding.editInputBankAccountAmount.error = "Пустое поле"
                return@setOnClickListener
            }

            val wallet = WalletResponseItem(walletAmount.toDouble(), id, walletName, "ACCESSIBLE")

            homeViewModel.addWallet(wallet)
            dismiss()

            Toast.makeText(requireContext(), "Кошелек успешно создан!", Toast.LENGTH_SHORT)
                .show()

        }

        binding.imgCloseBottomSheet.setOnClickListener {
            dismiss()
        }


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}