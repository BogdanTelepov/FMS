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
import com.neobis.fms.databinding.FragmentTransferBinding
import com.neobis.fms.model.addTransaction.AddTransfer
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.home.HomeFragment
import com.neobis.fms.utilits.spinnerWalletFilter
import java.text.SimpleDateFormat
import java.util.*


class TransferFragment : BottomSheetDialogFragment() {
    @SuppressLint("SimpleDateFormat")
    private val sdf = SimpleDateFormat("yyyy-MM-dd")
    private var walletFrom = 0
    private var walletTo = 0
    private val walletNameArray: ArrayList<WalletCurrent> = ArrayList()

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!
    private lateinit var addTransactionViewModel: AddTransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTransferBinding.inflate(inflater, container, false)


        val repository = MainRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        addTransactionViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddTransactionViewModel::class.java)
        val builder: MaterialDatePicker.Builder<*> = MaterialDatePicker.Builder.datePicker()

        builder.setTitleText("Select a Date")
        val startDatePicker: MaterialDatePicker<*> = builder.build()

        val currentDate = sdf.format(Date())

        binding.imgCloseBottomSheet.setOnClickListener {
            dismiss()
        }


//        binding.addDateTransfer.setOnClickListener {
//            startDatePicker.show(childFragmentManager, startDatePicker.toString())
//        }
//
//        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
//            timeZone = TimeZone.getTimeZone("UTC")
//        }
//
//        startDatePicker.addOnPositiveButtonClickListener {
//
//            binding.addDateTransfer.text = outputDateFormat.format(it)
//            Toast.makeText(requireContext(), startDatePicker.headerText, Toast.LENGTH_SHORT).show()
//        }
//
//        startDatePicker.addOnNegativeButtonClickListener {
//            startDatePicker.dismiss()
//        }
//        binding.addDateTransfer.text = currentDate

        addTransactionViewModel.getAllActiveWallets()
        addTransactionViewModel.responseAllWalletList.observe(viewLifecycleOwner, { response ->
            response.body()?.forEach {
                walletNameArray.add(WalletCurrent(it.id, it.name))
            }
        })

        spinnerWalletFilter(requireContext(), walletNameArray, binding.fromAccount)
        binding.fromAccount.onItemClickListener =
            object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val wallet = parent?.getItemAtPosition(position) as WalletCurrent
                    walletFrom = wallet.id
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

        spinnerWalletFilter(requireContext(), walletNameArray, binding.toAccount)
        binding.toAccount.onItemClickListener =
            object : AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val wallet = parent?.getItemAtPosition(position) as WalletCurrent
                    walletTo = wallet.id
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

        binding.addTransferBtn.setOnClickListener {
            val sumTransfer = binding.addAmountTransfer.text.toString()
            val comment = binding.commentsEv.text.toString()
            if (sumTransfer.isEmpty()) {
                binding.addAmountTransfer.error = "Пустое поле"
                return@setOnClickListener
            }
            val addTransfer = AddTransfer(sumTransfer.toInt(), comment, walletFrom, walletTo)
            addTransactionViewModel.addTransfer(addTransfer)
            Toast.makeText(
                activity,
                "Перевод успешно создан",
                Toast.LENGTH_SHORT
            )
                .show()
            dismiss()

        }

        binding.clearTv.setOnClickListener {
            binding.fromAccount.text = null
            binding.toAccount.text = null
            walletFrom = 0
            walletTo = 0
            binding.addAmountTransfer.text = null
        }
        return binding.root
    }

    private fun loadFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction().also { fragmentTransaction ->
            fragmentTransaction?.replace(R.id.navHostFragment, fragment)
            fragmentTransaction?.commit()
        }
    }


}