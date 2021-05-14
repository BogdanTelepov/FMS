package com.neobis.fms.ui.fragments.settings

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.neobis.fms.MainViewModelFactory
import com.neobis.fms.R
import com.neobis.fms.adapters.AccountItemAdapter
import com.neobis.fms.adapters.ExpCategoryAdapter
import com.neobis.fms.adapters.IncomeCategoryAdapter
import com.neobis.fms.adapters.UserItemAdapter
import com.neobis.fms.databinding.FragmentSettingsBinding
import com.neobis.fms.model.bankWallet.WalletResponseItem
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.user.UpdateUser
import com.neobis.fms.model.user.UserResponse
import com.neobis.fms.repository.MainRepository
import com.neobis.fms.ui.fragments.BottomSheetAddBankWalletFragment
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment(), AccountItemAdapter.OnItemClickListener,
    UserItemAdapter.OnItemClickListener, IncomeCategoryAdapter.OnItemClickListener,
    ExpCategoryAdapter.OnItemClickListener {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var accountItemAdapter: AccountItemAdapter
    private lateinit var settingsViewModel: SettingsViewModel
    private lateinit var incomeCategoryAdapter: IncomeCategoryAdapter
    private lateinit var expCategoryAdapter: ExpCategoryAdapter
    private lateinit var userItemAdapter: UserItemAdapter


    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val repository = MainRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        settingsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SettingsViewModel::class.java)

        binding.income.setOnClickListener {
            accounts_layout.visibility = View.GONE
            users_layout.visibility = View.GONE
            income_category_layout.visibility = View.VISIBLE
            expenditure_category_layout.visibility = View.GONE
        }

        binding.expense.setOnClickListener {
            accounts_layout.visibility = View.GONE
            users_layout.visibility = View.GONE
            income_category_layout.visibility = View.GONE
            expenditure_category_layout.visibility = View.VISIBLE
        }

        binding.overall.setOnClickListener {
            accounts_layout.visibility = View.VISIBLE
            users_layout.visibility = View.VISIBLE
            income_category_layout.visibility = View.GONE
            expenditure_category_layout.visibility = View.GONE

        }
        binding.swipeLayout.setOnRefreshListener {
            getAllAccounts()
            getAllUsers()
            getIncomeCategories()
            getExpCategories()
        }

        binding.addAccountBtn.setOnClickListener {
            val bottomSheet = BottomSheetAddBankWalletFragment()
            bottomSheet.show(childFragmentManager, "bottomSheet")
        }

        binding.addExpenditureCategoryBtn.setOnClickListener {
            val expBottomSheet =
                BottomSheetAddExpenseCategory()
            expBottomSheet.show(childFragmentManager, "expBottomSheet")
        }

        binding.addIncomeCategoryBtn.setOnClickListener {
            val incomeBottomSheet =
                BottomSheetAddIncomeCategory()
            incomeBottomSheet.show(childFragmentManager, "incomeBottomSheet")
        }


        binding.addUserBtn.setOnClickListener {
            val userBottomSheet = BottomSheetAddUser()
            userBottomSheet.show(childFragmentManager, "userBottomSheet")
        }


        return binding.root
    }


    override fun editWallet(walletResponseItem: WalletResponseItem) {
        val bundle = Bundle().apply {
            walletResponseItem.availableBalance?.let { putDouble("availableBalance", it) }
            putString("name", walletResponseItem.name)
            putInt("id", walletResponseItem.id)
        }
        findNavController().navigate(R.id.action_settingsFragment_to_bottomSheetEditWallet, bundle)

    }


    private fun getAllAccounts() {
        binding.swipeLayout.isRefreshing = true
        accountItemAdapter = AccountItemAdapter(this)
        settingsViewModel.getAllActiveWallets()
        settingsViewModel.responseListActiveWallets.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                accountItemAdapter.differ.submitList(response.body()?.toList())
                binding.accountRvControl.apply {
                    adapter = accountItemAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            } else {
                binding.swipeLayout.isRefreshing = false
//                Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAllUsers() {
        binding.swipeLayout.isRefreshing = true
        userItemAdapter = UserItemAdapter(this)
        settingsViewModel.getUsers()
        settingsViewModel.responseUserList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                userItemAdapter.differ.submitList(response.body()?.toList())
                binding.userRvControl.apply {
                    adapter = userItemAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            } else {
                binding.swipeLayout.isRefreshing = false
            }
        } )
    }

    private fun getIncomeCategories() {
        binding.swipeLayout.isRefreshing = true
        incomeCategoryAdapter = IncomeCategoryAdapter(this)
        settingsViewModel.getCategories()
        settingsViewModel.responseCategoryList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                incomeCategoryAdapter.differ.submitList(response.body()?.toList())
                binding.incomeRvControl.apply {
                    adapter = incomeCategoryAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            } else {
                binding.swipeLayout.isRefreshing = false
//                Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getExpCategories() {
        binding.swipeLayout.isRefreshing = true
        expCategoryAdapter = ExpCategoryAdapter(this)
        settingsViewModel.getExpCategories()
        settingsViewModel.responseCategoryExpList.observe(viewLifecycleOwner, { response ->
            if (response.isSuccessful) {
                binding.swipeLayout.isRefreshing = false
                expCategoryAdapter.differ.submitList(response.body()?.toList())
                binding.expenditureRvControl.apply {
                    adapter = expCategoryAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            } else {
                binding.swipeLayout.isRefreshing = false
//                Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
            }
        })

    }


    override fun archiveWallet(walletResponseItem: WalletResponseItem) {
        val archiveWallet = WalletResponseItem(
            availableBalance = walletResponseItem.availableBalance,
            id = walletResponseItem.id,
            name = walletResponseItem.name,
            status = "ARCHIVED"
        )
        settingsViewModel.archiveWallet(archiveWallet)
    }

    override fun editUser(updateUser: UserResponse) {
        val bundle = Bundle().apply {
            putString("name", updateUser.name)
            putString("surname", updateUser.surname)
            putString("email", updateUser.email)
            putInt("id", updateUser.id)
        }
        findNavController().navigate(R.id.action_settingsFragment_to_bottomSheetEditUser, bundle)
    }

    override fun archiveUser(updateUser: UpdateUser) {
        val userArchive = UpdateUser(
            email = updateUser.email,
            groupIds = updateUser.groupIds,
            id = updateUser.id,
            name = updateUser.name,
            phoneNumber = updateUser.phoneNumber,
            surname = updateUser.surname,
            userStatus = updateUser.userStatus
        )
        settingsViewModel.archiveUser(userArchive)
    }

    override fun editIncomeCategory(categoryResponseItem: CategoryResponseItem) {
        val bundle = Bundle().apply {
            putString("name", categoryResponseItem.name)
            putString("neoSection", categoryResponseItem.neoSection)
            putInt("id", categoryResponseItem.id)
        }
        findNavController().navigate(
            R.id.action_settingsFragment_to_bottomSheetEditIncomeCategory,
            bundle
        )
    }

    override fun archiveIncomeCategory(categoryResponseItem: CategoryResponseItem) {
        val archiveIncomeCategory = CategoryResponseItem(
            categoryStatus = "ARCHIVED",
            id = categoryResponseItem.id,
            name = categoryResponseItem.name,
            neoSection = categoryResponseItem.neoSection,
            transactionType = "INCOME"
        )
        settingsViewModel.archiveIncome(archiveIncomeCategory)
    }

    override fun editExpCategory(categoryResponseItem: CategoryResponseItem) {
        val bundle = Bundle().apply {
            putString("name", categoryResponseItem.name)
            putString("neoSection", categoryResponseItem.neoSection)
            putInt("id", categoryResponseItem.id)
        }
        findNavController().navigate(
            R.id.action_settingsFragment_to_bottomSheetExpenseCategory,
            bundle
        )
    }

    override fun archiveExpCategory(categoryResponseItem: CategoryResponseItem) {
        val archiveExpCategory = CategoryResponseItem(
            categoryStatus = "ARCHIVED",
            id = categoryResponseItem.id,
            name = categoryResponseItem.name,
            neoSection = categoryResponseItem.neoSection,
            transactionType = "EXPENSE"
        )
        settingsViewModel.archiveExpense(archiveExpCategory)
    }
}

