package com.neobis.fms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.fms.R
import com.neobis.fms.model.category.CategoryResponseItem
import kotlinx.android.synthetic.main.expenditure_category_item.view.*
import kotlinx.android.synthetic.main.income_category_item.view.*

class ExpCategoryAdapter(private val listener: OnItemClickListener): RecyclerView.Adapter<ExpCategoryAdapter.MyViewHolder>() {
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpCategoryAdapter.MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.expenditure_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExpCategoryAdapter.MyViewHolder, position: Int) {
        val deposit = differ.currentList[position]
        holder.itemView.apply {
            if (deposit.transactionType == "EXPENSE") {
                expenditure_one.text = deposit.name
            }
            else {
                expenditure_one.visibility = View.GONE
                expenditure_archive_btn.visibility = View.GONE
                expenditure_edit_btn.visibility = View.GONE
            }
            expenditure_edit_btn.setOnClickListener {
                listener.editExpCategory(deposit)
            }
            expenditure_archive_btn.setOnClickListener {
                listener.archiveExpCategory(deposit)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
    interface OnItemClickListener {
        fun editExpCategory(categoryResponseItem: CategoryResponseItem)
        fun archiveExpCategory(categoryResponseItem: CategoryResponseItem)

    }
}
