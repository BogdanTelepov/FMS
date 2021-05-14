package com.neobis.fms.ui.fragments.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.EditAccountBottomsheetBinding
import com.neobis.fms.model.bankWallet.WalletResponseItem
import com.neobis.fms.repository.MainRepository

class BottomSheetEditWallet : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private var _binding: EditAccountBottomsheetBinding? = null
    private val binding get() = _binding!!
    val repository = MainRepository()
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditAccountBottomsheetBinding.inflate(inflater, container, false)
        val viewModelFactory = MainViewModelFactory(repository)
        settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)






        binding.btnSaveAccount.setOnClickListener {
            val walletName = binding.editBankAccountName.text.toString().trim()
            val walletBalance = binding.editBankAccountAmount.text.toString()
            val walletId = arguments?.getInt("id")
            if (walletName.isEmpty()) {
                binding.editBankAccountName.error = "Пустое поле"
                return@setOnClickListener
            }

            if (walletBalance.isEmpty()) {
                binding.editBankAccountAmount.error = "Пустое поле"
                return@setOnClickListener
            }

            val editWallet =
                walletId?.let { it1 ->
                    WalletResponseItem(
                        availableBalance = walletBalance.toDouble(),
                        id = it1,
                        name = walletName,
                        status = "ACCESSIBLE"
                    )
                }
            if (editWallet != null) {
                settingsViewModel.updateWallet(editWallet)
            }
            Toast.makeText(requireContext(), "Кошелек успешно обновлен!", Toast.LENGTH_SHORT)
            .show()
            findNavController().navigate(R.id.action_bottomSheetEditWallet_to_settingsFragment)



        }

        binding.btnCancelAccount.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_bottomSheetEditWallet_to_settingsFragment)
        }


        binding.imgCloseBottomSheet.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_bottomSheetEditWallet_to_settingsFragment)
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editBankAccountAmount.setText(arguments?.getDouble("availableBalance").toString())
        binding.editBankAccountName.setText(arguments?.getString("name"))
    }

}


