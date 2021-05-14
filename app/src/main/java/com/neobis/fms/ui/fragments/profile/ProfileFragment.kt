package com.neobis.fms.ui.fragments.profile

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.neobis.fms.MainActivity
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.FragmentProfileBinding
import com.neobis.fms.model.user.UpdateUserBody
import com.neobis.fms.network.SessionManager
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.activities.AuthActivity


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    private lateinit var profileViewModel: ProfileFragmentViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val repository = MainRepository()
        val mainViewModelFactory = MainViewModelFactory(repository)

        profileViewModel =
                ViewModelProvider(this, mainViewModelFactory).get(ProfileFragmentViewModel::class.java)

        profileViewModel.getCurrentUser()
        profileViewModel.responseCurrentUser.observe(viewLifecycleOwner, { response ->
            binding.editName.setText(response.body()?.name ?: "Имя")
            binding.editSurname.setText(response.body()?.surname ?: "Фамилия")
            binding.editEmail.hint = response.body()?.email ?: "Email"


        })









        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        binding.btnExit.setOnClickListener {


            (activity as MainActivity).authActivityTransition()
            sessionManager.clearData()

        }

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
        }






        binding.btnSave.setOnClickListener {

            val name: String = binding.editName.text.toString().trim()
            val surname: String = binding.editSurname.text.toString().trim()
            val oldPassword: String = binding.editCurrentPassword.text.toString().trim()
            val newPassword: String = binding.editNewPassword.text.toString().trim()
            val confirmNewPassword: String = binding.editConfirmNewPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.editName.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (surname.isEmpty()) {
                binding.editSurname.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (oldPassword.isEmpty()) {
                binding.editCurrentPassword.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (newPassword.isEmpty()) {
                binding.editNewPassword.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (newPassword != confirmNewPassword) {
                binding.editConfirmNewPassword.error = "Пароли не совпадают"
                return@setOnClickListener
            }

            val updateUserBody = UpdateUserBody(
                    name = name,
                    phoneNumber = "996551123456",
                    surname = surname,
                    oldPassword = oldPassword,
                    newPassword = newPassword

            )
            profileViewModel.updateUserBody(updateUserBody)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}