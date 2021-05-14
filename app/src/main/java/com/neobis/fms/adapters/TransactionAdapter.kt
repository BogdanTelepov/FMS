package com.neobis.fms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.fms.R
import com.neobis.fms.model.transaction.TransactionItem
import com.neobis.fms.model.wallet.TransactionModel
import com.neobis.fms.utilits.convertData
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<TransactionModel>() {
        override fun areItemsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionModel,
            newItem: TransactionModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val transaction = differ.currentList[position]
        holder.itemView.apply {
            item_category.text = transaction?.categoryName ?: "N/A"
            item_date.text = convertData(transaction.createdDate)
            item_money.text = transaction?.amount.toString()
            item_bankCard.text = transaction.walletName
            item_personName.text = transaction.counterpartyName
            if (transaction.transactionType == "INCOME") {
                img_arrow.setImageResource(R.drawable.ic_arrow_down)
            } else if (transaction.transactionType == "EXPENSE") {
                img_arrow.setImageResource(R.drawable.ic_arrow_up)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}