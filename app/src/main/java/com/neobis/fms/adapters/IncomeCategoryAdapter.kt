package com.neobis.fms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.fms.R
import com.neobis.fms.model.category.CategoryResponseItem
import com.neobis.fms.model.user.UserResponse
import kotlinx.android.synthetic.main.income_category_item.view.*

class IncomeCategoryAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<IncomeCategoryAdapter.MyViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<CategoryResponseItem>() {
        override fun areItemsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponseItem,
            newItem: CategoryResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IncomeCategoryAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.income_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IncomeCategoryAdapter.MyViewHolder, position: Int) {
        val deposit = differ.currentList[position]
        holder.itemView.apply {
            if (deposit.transactionType == "INCOME") {
                category_one.text = deposit.name
            } else {
                category_archive_btn.visibility = View.GONE
                category_edit_btn.visibility = View.GONE
                category_one.visibility = View.GONE
            }
            category_edit_btn.setOnClickListener {
                listener.editIncomeCategory(deposit)
            }
            category_archive_btn.setOnClickListener {
                listener.archiveIncomeCategory(deposit)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    interface OnItemClickListener {
        fun editIncomeCategory(categoryResponseItem: CategoryResponseItem)
        fun archiveIncomeCategory(categoryResponseItem: CategoryResponseItem)

    }
}
