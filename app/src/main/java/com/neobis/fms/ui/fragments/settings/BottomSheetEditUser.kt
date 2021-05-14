package com.neobis.fms.ui.fragments.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.EditUserBottomsheetBinding
import com.neobis.fms.model.user.UpdateUser
import com.neobis.fms.repository.MainRepository

class BottomSheetEditUser : BottomSheetDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private var _binding: EditUserBottomsheetBinding? = null
    private val binding get() = _binding!!
    val repository = MainRepository()
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditUserBottomsheetBinding.inflate(inflater, container, false)
        val viewModelFactory = MainViewModelFactory(repository)
        settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)






        binding.btnSaveUser.setOnClickListener {
            val userName = binding.editEmployeeName.text.toString().trim()
            val userLasName = binding.editEmployeeLastName.text.toString().trim()
            val userEmail = binding.editEmployeeEmail.text.toString().trim()
            val userId = arguments?.getInt("id")
            if (userName.isEmpty()) {
                binding.editEmployeeName.error = "Пустое поле"
                return@setOnClickListener
            }

            if (userLasName.isEmpty()) {
                binding.editEmployeeLastName.error = "Пустое поле"
                return@setOnClickListener
            }

            if (userEmail.isEmpty()) {
                binding.editEmployeeEmail.error = "Пустое поле"
                return@setOnClickListener
            }
            val editUser =
                userId?.let { it1 ->
                    UpdateUser(
                        email = userEmail,
                        groupIds = listOf(),
                        id = userId,
                        name = userName,
                        phoneNumber = "+996778256478",
                        surname = userLasName,
                        userStatus = "APPROVED"
                        )
                }
            if (editUser != null) {
                settingsViewModel.updateUser(editUser)
            }
            Toast.makeText(requireContext(), "Пользователь успешно обновлен!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_bottomSheetEditUser_to_settingsFragment)


        }

        binding.btnCancelUser.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_bottomSheetEditUser_to_settingsFragment)
        }


        binding.imgCloseBottomSheet.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.action_bottomSheetEditUser_to_settingsFragment)
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editEmployeeName.setText(arguments?.getString("name"))
        binding.editEmployeeLastName.setText(arguments?.getString("surname"))
        binding.editEmployeeEmail.setText(arguments?.getString("email"))
    }
}