package com.prongbang.archmvvm.ui.user

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.prongbang.arch_mvvm.R
import com.prongbang.arch_mvvm.databinding.ItemUserBinding
import com.prongbang.archmvvm.vo.User

class UserAdapter(private val dataBindingComponent: DataBindingComponent) : ListAdapter<User, UserAdapter.ViewHolder>(DIFF_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_user,
                parent,
                false,
                dataBindingComponent
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val mBind: ItemUserBinding) : RecyclerView.ViewHolder(mBind.root) {

        fun bind(user: User) {
            mBind.user = user
        }
    }

    companion object {

        val DIFF_COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User?, newItem: User?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User?, newItem: User?): Boolean {
                return oldItem?.id == newItem?.id
            }
        }
    }
}

