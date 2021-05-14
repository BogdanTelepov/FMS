package com.neobis.fms.ui.fragments.addTransaction

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.fms.R
import kotlinx.android.synthetic.main.fragment_add_transaction.*

class AddTransactionFragment : BottomSheetDialogFragment() {
    override fun getTheme(): Int = R.style.BottomSheetDialogTheme
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        BottomSheetDialog(requireContext(), theme)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_transaction, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        layoutIncome.setOnClickListener {
            val income = IncomeFragment()
            income.show(childFragmentManager, "income")
        }

        layoutExpenditure.setOnClickListener {
            val expenditure = ExpenditureFragment()
            expenditure.show(childFragmentManager, "expenditure")

        }

        layoutTransfer.setOnClickListener {
            val transfer = TransferFragment()
            transfer.show(childFragmentManager, "transfer")
        }

    }


}