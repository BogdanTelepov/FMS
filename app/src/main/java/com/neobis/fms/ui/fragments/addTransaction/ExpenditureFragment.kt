package com.neobis.fms.ui.fragments.addTransaction

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.FragmentExpenditureBinding
import com.neobis.fms.model.addTransaction.AddIncomeOrExpense
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.home.HomeFragment
import com.neobis.fms.utilits.spinnerCategoryFilter
import com.neobis.fms.utilits.spinnerSectionFilter
import com.neobis.fms.utilits.spinnerWalletFilter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExpenditureFragment : BottomSheetDialogFragment() {
    private var walletId = 0
    private var categoryId = 0
    private var sectionType = -1
    private var type = 1
    private val walletArray: ArrayList<WalletCurrent> = ArrayList()
    private val categoryArray: ArrayList<CategoryName> = ArrayList()

    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyy-MM-dd")

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)


    private var _binding: FragmentExpenditureBinding? = null
    private val binding get() = _binding!!
    private lateinit var addTransactionViewModel: AddTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpenditureBinding.inflate(inflater, container, false)

        val repository = MainRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        addTransactionViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddTransactionViewModel::class.java)
        val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()

        builder.setTitleText("Select a Date")
        val startDatePicker: MaterialDatePicker<*> = builder.build()

        val currentDate = sdf.format(Date())

        binding.addDateExp.setOnClickListener {
            startDatePicker.show(childFragmentManager, startDatePicker.toString())
        }

        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        startDatePicker.addOnPositiveButtonClickListener {

            binding.addDateExp.text = outputDateFormat.format(it)
            Toast.makeText(requireContext(), startDatePicker.headerText, Toast.LENGTH_SHORT).show()
        }

        startDatePicker.addOnNegativeButtonClickListener {
            startDatePicker.dismiss()
        }
        binding.addDateExp.text = currentDate

        addTransactionViewModel.getAllActiveWallets()
        addTransactionViewModel.responseAllWalletList.observe(viewLifecycleOwner, { response ->
            response.body()?.forEach {
                walletArray.add(WalletCurrent(it.id, it.name))
            }
        })


        spinnerWalletFilter(requireContext(), walletArray, binding.accountActvExp)
        binding.accountActvExp.onItemClickListener =
            object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val wallet = parent?.getItemAtPosition(position) as WalletCurrent
                    walletId = wallet.id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }


            }



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
                    val sectionName: SectionName =
                        parent?.getItemAtPosition(position) as SectionName
                    sectionType = sectionName.id
                    getCategory(sectionType, type)
                }
            }

//        addTransactionViewModel.getCategories()
//        addTransactionViewModel.getCategoriesResponse.observe(viewLifecycleOwner, { response ->
//            response.body()?.forEach {
//                categoryArray.add(CategoryName(it.id, it.name))
//            }
//        })

        spinnerCategoryFilter(requireContext(), categoryArray, binding.categoryActvExp)

        binding.categoryActvExp.onItemClickListener =
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
                    val categoryIdName: CategoryName =
                        parent?.getItemAtPosition(position) as CategoryName
                    categoryId = categoryIdName.id

                }
            }

        binding.addExpenditureBtn.setOnClickListener {
            val sum = binding.addAmountExp.text.toString()
            val comment = binding.commentsEv.text.toString().trim()
            val dateGet = binding.addDateExp.text.toString()

            if (sum.isEmpty()) {
                binding.addAmountExp.error = "Пустое поле"
                return@setOnClickListener
            }

            if (dateGet.isEmpty()) {
                binding.addDateExp.error = "Пустое поле"
                return@setOnClickListener
            }

            val addIncomeOrExpense = AddIncomeOrExpense(
                sum.toInt(), categoryId, comment, 1, "TODO", dateGet, walletId
            )
            addTransactionViewModel.addIncomeOrExpense(addIncomeOrExpense)

            Toast.makeText(
                activity,
                "Транзакция дохода создано",
                Toast.LENGTH_SHORT
            )
                .show()
            dismiss()
        }

        binding.imgCloseBottomSheet.setOnClickListener {
            dismiss()
        }

        binding.clearTv.setOnClickListener {
            binding.addDateExp.text = currentDate
            binding.accountActvExp.text = null
            binding.categoryActvExp.text = null
            binding.companyActv.text = null
            walletId = 0
            categoryId = 0
            binding.addAmountExp.text = null
        }

        return binding.root

    }

    private fun loadFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction?.replace(R.id.navHostFragment, fragment)
            fragmentTransaction?.commit()
        }
    }

    private fun getCategory(section: Int, type: Int) {
        addTransactionViewModel.getCategoryBySectionAndType(section, type)
        addTransactionViewModel.getCategoriesBySectionAndType.observe(viewLifecycleOwner) { response ->
            response.body()?.forEach {
                categoryArray.add(CategoryName(it.id, it.name))
            }
        }
    }

}