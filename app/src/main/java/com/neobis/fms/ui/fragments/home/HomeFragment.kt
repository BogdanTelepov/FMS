package com.neobis.fms.ui.fragments.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.adapters.BankWalletAdapter
import com.neobis.fms.adapters.TransactionAdapter
import com.neobis.fms.databinding.FragmentHomeBinding
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.BottomSheetAddBankWalletFragment
import net.cachapa.expandablelayout.ExpandableLayout

class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var expandableLayout: ExpandableLayout

    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var bankWalletAdapter: BankWalletAdapter
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel


    var startDate: String? = "2021-01-01"
    var endDate: String? = "2021-12-31"
    var walletId: Int? = null
    var categoryId: Int? = null
    var userId: Int? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        val repository = MainRepository()
        val mainViewModelFactory = MainViewModelFactory(repository)
        homeFragmentViewModel =
                ViewModelProvider(this, mainViewModelFactory).get(HomeFragmentViewModel::class.java)


        initExpandableLayout()

        fetchFilterTransactions()

        fetchAllBankActiveWallets()

        binding.withdrawTypeChipGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.overall_balance -> {
                    fetchFilterTransactions()
                }
                R.id.income_balance -> {
                    fetchAllIncomeTransactions()
                }
                R.id.expense_balance -> {
                    fetchAllExpenseTransactions()
                }
            }

        }



        binding.swipeLayout.setOnRefreshListener {
            fetchFilterTransactions()
        }

        binding.btnOpenBottomSheet.setOnClickListener {
            val bottomSheet = BottomSheetAddBankWalletFragment()
            bottomSheet.show(childFragmentManager, "bottomSheet")
        }


        homeFragmentViewModel.getTotalBalance()
        homeFragmentViewModel.responseGetTotalBalance.observe(viewLifecycleOwner, { response ->

            if (response.isSuccessful) {
                binding.tvBankAccountMoney.text = response.body()?.totalBalance.toString()
            }

        })




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        startDate = arguments?.getString("startDate", "2021-01-01").toString()
        endDate = arguments?.getString("endDate", "2021-12-31").toString()
        walletId = arguments?.getInt("walletId")
        categoryId = arguments?.getInt("categoryId")
        userId = arguments?.getInt("userId")


    }


    private fun fetchFilterTransactions() {

        binding.swipeLayout.isRefreshing = true
        transactionAdapter = TransactionAdapter()

        homeFragmentViewModel.globalFiltration(
                startDate, endDate, walletId, categoryId, userId
        )
        homeFragmentViewModel.responseListTransactions.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                transactionAdapter.differ.submitList(response.body()?.toList())
                binding.rvListTransaction.apply {
                    adapter = transactionAdapter
                    layoutManager = LinearLayoutManager(activity)
                }

            } else {
                binding.swipeLayout.isRefreshing = false

            }
        })
    }


    private fun fetchAllIncomeTransactions() {
        binding.swipeLayout.isRefreshing = true
        transactionAdapter = TransactionAdapter()
        homeFragmentViewModel.getAllIncomeTransactions()
        homeFragmentViewModel.responseListIncomeTransactions.observe(viewLifecycleOwner,
                { response ->
                    if (response.isSuccessful) {
                        binding.swipeLayout.isRefreshing = false
                        transactionAdapter.differ.submitList(response.body()?.toList())
                        binding.rvListTransaction.apply {
                            adapter = transactionAdapter
                            layoutManager = LinearLayoutManager(activity)
                        }
                    } else {
                        binding.swipeLayout.isRefreshing = false

                    }
                })
    }

    private fun fetchAllExpenseTransactions() {
        binding.swipeLayout.isRefreshing = true
        transactionAdapter = TransactionAdapter()
        homeFragmentViewModel.getAllExpenseTransactions()
        homeFragmentViewModel.responseListExpenseTransactions.observe(viewLifecycleOwner,
                { response ->
                    if (response.isSuccessful) {
                        binding.swipeLayout.isRefreshing = false
                        transactionAdapter.differ.submitList(response.body()?.toList())
                        binding.rvListTransaction.apply {
                            adapter = transactionAdapter
                            layoutManager = LinearLayoutManager(activity)
                        }
                    } else {
                        binding.swipeLayout.isRefreshing = false

                    }
                })
    }

    private fun fetchAllBankActiveWallets() {
        bankWalletAdapter = BankWalletAdapter()
        homeFragmentViewModel.getAllActiveWallets()
        homeFragmentViewModel.responseListActiveWallets.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                bankWalletAdapter.differ.submitList(response.body()?.toList())
                binding.rvBankAccountListItem.apply {
                    adapter = bankWalletAdapter

                }
            }
        })
    }

    private fun initExpandableLayout() {
        expandableLayout = binding.expandableLayout0

        expandableLayout.setOnExpansionUpdateListener({ expansionFraction, stete -> })


        binding.imExpandLayout.setOnClickListener {
            if (expandableLayout.isExpanded) {
                binding.imExpandLayout.setImageResource(R.drawable.ic_btn_down)
                binding.withdrawTypeChipGroup.visibility = View.VISIBLE
                expandableLayout.collapse()
            } else {
                binding.imExpandLayout.setImageResource(R.drawable.ic_btn_up)
                binding.withdrawTypeChipGroup.visibility = View.GONE
                expandableLayout.expand()
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter_page) {
            val action = HomeFragmentDirections.actionHomeFragmentToFilterFragment()
            findNavController().navigate(action)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        this.arguments?.clear()
    }

}