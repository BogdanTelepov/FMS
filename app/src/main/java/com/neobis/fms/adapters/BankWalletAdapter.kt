package com.neobis.fms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.fms.R
import com.neobis.fms.model.bankWallet.WalletResponseItem
import kotlinx.android.synthetic.main.bank_wallet_item.view.*

class BankWalletAdapter() : RecyclerView.Adapter<BankWalletAdapter.MyViewHolder>() {

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
            LayoutInflater.from(parent.context).inflate(R.layout.bank_wallet_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val bankWalletItem = differ.currentList[position]
        holder.itemView.apply {

            bankAccount_name.text = bankWalletItem.name
            bankAccount_amount.text = bankWalletItem.availableBalance.toString()
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}