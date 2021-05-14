package com.neobis.fms.ui.fragments.filter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.databinding.FragmentFilterBinding
import com.neobis.fms.model.category.CategoryIdName
import com.neobis.fms.model.user.UserIdName
import com.neobis.fms.model.wallet.WalletIdName
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.utilits.customSpinnerCategoryList
import com.neobis.fms.utilits.customSpinnerUserList
import com.neobis.fms.utilits.customSpinnerWalletList

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var filterViewModel: FilterFragmentViewModel


    private var categoryId: Int? = null
    private var walletId: Int? = null
    private var userId: Int? = null
    private var startDate: String? = "2021-01-01"
    private var endDate: String? = "2021-12-31"

    private val walletNameArray: ArrayList<WalletIdName> = ArrayList()

    private val categoriesNameArray: ArrayList<CategoryIdName> = ArrayList()
    private val usersNameArray: ArrayList<UserIdName> = ArrayList()


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFilterBinding.inflate(layoutInflater, container, false)

        val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()

        builder.setTitleText("Select a Date")
        val startDatePicker: MaterialDatePicker<*> = builder.build()
        val endDatePicker: MaterialDatePicker<*> = builder.build()

        binding.startDateInputLayout.setEndIconOnClickListener {

            startDatePicker.show(childFragmentManager, startDatePicker.toString())
        }


        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        startDatePicker.addOnPositiveButtonClickListener {

            binding.startDateInputEditText.setText(outputDateFormat.format(it))

            startDate = outputDateFormat.format(it)

        }

        startDatePicker.addOnNegativeButtonClickListener {
            startDatePicker.dismiss()
        }

        binding.endDateInputLayout.setEndIconOnClickListener {
            endDatePicker.show(childFragmentManager, startDatePicker.toString())
        }

        endDatePicker.addOnPositiveButtonClickListener {
            binding.endDateInputEditText.setText(outputDateFormat.format(it))
            endDate = outputDateFormat.format(it)


        }

        endDatePicker.addOnNegativeButtonClickListener {
            endDatePicker.dismiss()
        }


        val mainRepository = MainRepository()
        val viewModelFactory = MainViewModelFactory(mainRepository)
        filterViewModel =
                ViewModelProvider(this, viewModelFactory).get(FilterFragmentViewModel::class.java)

        filterViewModel.getWallets()

        filterViewModel.getWalletsResponse.observe(viewLifecycleOwner, { response ->
            response.body()?.forEach {
                walletNameArray.add(WalletIdName(it.id, it.name))
            }

        })
        customSpinnerWalletList(requireContext(), walletNameArray, binding.walletFilter)
        binding.walletFilter.onItemClickListener =
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
                        val wallet = parent?.getItemAtPosition(position) as WalletIdName
                        walletId = wallet.id

                    }
                }






        filterViewModel.getCategories()
        filterViewModel.getCategoriesResponse.observe(viewLifecycleOwner, { response ->
            response.body()?.forEach {
                categoriesNameArray.add(CategoryIdName(it.id, it.name))
            }
        })

        customSpinnerCategoryList(requireContext(), categoriesNameArray, binding.categoryFilter)
        binding.categoryFilter.onItemClickListener =
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
                        val category = parent?.getItemAtPosition(position) as CategoryIdName
                        categoryId = category.id

                    }
                }




        filterViewModel.getUsers()

        filterViewModel.getUsersResponse.observe(viewLifecycleOwner, { response ->
            response.body()?.forEach {
                usersNameArray.add(UserIdName(it.id, it.name))
            }

        })

        customSpinnerUserList(requireContext(), usersNameArray, binding.usersFilter)
        binding.usersFilter.onItemClickListener =
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
                        val user = parent?.getItemAtPosition(position) as UserIdName
                        userId = user.id

                    }
                }






        binding.btnApplyFilter.setOnClickListener {
            val bundle = Bundle().apply {
                putString("startDate", startDate)
                putString("endDate", endDate)
                walletId?.let { it1 -> putInt("walletId", it1) }
                categoryId?.let { it1 -> putInt("categoryId", it1) }
                userId?.let { it1 -> putInt("userId", it1) }
            }

            findNavController().navigate(R.id.action_filterFragment_to_homeFragment, bundle)
        }

        binding.btnClear.setOnClickListener {
            binding.startDateInputEditText.text = null
            binding.endDateInputEditText.text = null
            binding.walletFilter.text = null
            binding.categoryFilter.text = null
            binding.usersFilter.text = null
            walletId = null
            categoryId = null
            userId = null
            startDate = "2021-01-01"
            endDate = "2021-12-31"

        }




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}