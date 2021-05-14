package com.neobis.fms.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.fms.R
import com.neobis.fms.model.bankWallet.WalletResponseItem
import kotlinx.android.synthetic.main.account_item.view.*

class AccountItemAdapter(private val listener: OnItemClickListener) :
        RecyclerView.Adapter<AccountItemAdapter.MyViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<WalletResponseItem>() {
        override fun areItemsTheSame(
                oldItem: WalletResponseItem,
                newItem: WalletResponseItem
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
                oldItem: WalletResponseItem,
                newItem: WalletResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.account_item, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val walletResponseItem = differ.currentList[position]
        holder.itemView.apply {
            account_one.text = walletResponseItem.name
            amount.text = walletResponseItem.availableBalance.toString() + " cом"
            edit_btn.setOnClickListener {
                listener.editWallet(walletResponseItem)
            }
            archive_btn.setOnClickListener {
                listener.archiveWallet(walletResponseItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}

    interface OnItemClickListener {
        fun editWallet(walletResponseItem: WalletResponseItem)
        fun archiveWallet(walletResponseItem: WalletResponseItem)

    }
}