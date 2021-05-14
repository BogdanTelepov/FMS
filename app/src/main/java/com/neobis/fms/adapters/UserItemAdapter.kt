package com.neobis.fms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.neobis.fms.R
import com.neobis.fms.model.user.UpdateUser
import com.neobis.fms.model.user.UserResponse
import kotlinx.android.synthetic.main.users_item.view.*

class UserItemAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserItemAdapter.MyViewHolder>() {
    private val differCallback = object : DiffUtil.ItemCallback<UserResponse>() {
        override fun areItemsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: UserResponse,
            newItem: UserResponse
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.users_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userResponse = differ.currentList[position]
        holder.itemView.apply {
            users_one.text = userResponse.name
            user_edit_btn.setOnClickListener {
                listener.editUser(userResponse)
            }
            user_archive_btn.setOnClickListener {
                listener.editUser(userResponse)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {}
    interface OnItemClickListener {
        fun editUser(updateUser: UserResponse)
        fun archiveUser(updateUser: UpdateUser)

    }
}