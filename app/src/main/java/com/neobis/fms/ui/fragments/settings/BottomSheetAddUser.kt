package com.neobis.fms.ui.fragments.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.AddUserBottomsheetBinding
import com.neobis.fms.model.user.RegistrationModel
import com.neobis.fms.repository.MainRepository

class BottomSheetAddUser : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private var _binding: AddUserBottomsheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddUserBottomsheetBinding.inflate(inflater, container, false)

        val repository = MainRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding.btnCreateUser.setOnClickListener {
            val name = binding.inputUserName.text.toString().trim()
            val surname = binding.inputUserLastName.text.toString().trim()
            val email = binding.inputUserEmail.text.toString().trim()
            val password = binding.inputUserPassword.text.toString()

            if (name.isEmpty()) {
                binding.inputUserName.error = "Пустое поле"
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                binding.inputUserLastName.error = "Пустое поле"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.inputUserEmail.error = "Пустое поле"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.inputUserPassword.error = "Пустое поле"
                return@setOnClickListener
            }

            val user = RegistrationModel(
                email,
                listOf(1),
                name,
                password,
                "+996778265489",
                surname
            )
            settingsViewModel.addUser(user)
            dismiss()
            Toast.makeText(
                requireContext(),
                "Пользователь успешно создан!",
                Toast.LENGTH_SHORT
            )
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