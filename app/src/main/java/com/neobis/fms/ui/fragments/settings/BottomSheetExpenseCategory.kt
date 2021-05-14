package com.neobis.fms.ui.fragments.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.EditExpenditureBottomsheetBinding
import com.neobis.fms.databinding.EditUserBottomsheetBinding
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.user.UpdateUser
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.addTransaction.SectionName
import com.neobis.fms.utilits.spinnerSectionFilter

class BottomSheetExpenseCategory : BottomSheetDialogFragment() {
    private var sectionType = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private var _binding: EditExpenditureBottomsheetBinding? = null
    private val binding get() = _binding!!
    val repository = MainRepository()
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EditExpenditureBottomsheetBinding.inflate(inflater, container, false)
        val viewModelFactory = MainViewModelFactory(repository)
        settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)


        spinnerSectionFilter(requireContext(), binding.companyActv)

        binding.companyActv.onItemClickListener =
            object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val sectionName: SectionName = parent?.getItemAtPosition(position) as SectionName
                    sectionType = sectionName.name

                }
            }



        binding.btnSaveExp.setOnClickListener {
            val expName = binding.editExpCategoryName.text.toString().trim()
            val expenseCategoryId = arguments?.getInt("id")
            if (expName.isEmpty()) {
                binding.editExpCategoryName.error = "Пустое поле"
                return@setOnClickListener
            }

            val editExpenseCategory =
                expenseCategoryId?.let { it1 ->
                    CategoryResponseItem(
                        categoryStatus = "ACTIVE",
                        id = expenseCategoryId,
                        name = expName,
                        neoSection = sectionType,
                        transactionType = "EXPENSE"
                        )
                }
            if (editExpenseCategory != null) {
                settingsViewModel.updateExpCategory(editExpenseCategory)
            }
            Toast.makeText(
                requireContext(),
                "Категория расходов успешно обновлена!",
                Toast.LENGTH_SHORT
            )
                .show()
            dismiss()


        }

        binding.btnCancelExp.setOnClickListener {
            dismiss()
        }


        binding.imgCloseBottomSheet.setOnClickListener {
            dismiss()
        }


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editExpCategoryName.setText(arguments?.getString("name"))
        binding.companyActv.setText(arguments?.getString("neoSection"))
    }
}