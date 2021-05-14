package com.neobis.fms.ui.fragments.settings

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.AddIncomeBottomsheetBinding
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.addTransaction.SectionName
import com.neobis.fms.utilits.spinnerSectionFilter

class BottomSheetAddIncomeCategory: BottomSheetDialogFragment() {
    private var sectionType = ""
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }


    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    private var _binding: AddIncomeBottomsheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddIncomeBottomsheetBinding.inflate(inflater, container, false)

        val repository = MainRepository()
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


        binding.btnCreateIncomeCategory.setOnClickListener {
            val incomeCategory = binding.inputCategoryName.text.toString().trim()
            if (incomeCategory.isEmpty()) {
                binding.inputCategoryName.error = "Пустое поле"
                return@setOnClickListener
            }

            val addIncomeCategory = CategoryResponseItem(
                "ACTIVE",
                1,
                incomeCategory,
                sectionType,
                "INCOME"
            )
            settingsViewModel.addIncCategories(addIncomeCategory)
            dismiss()
            Toast.makeText(
                requireContext(),
                "Категория доходов успешно создана!",
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