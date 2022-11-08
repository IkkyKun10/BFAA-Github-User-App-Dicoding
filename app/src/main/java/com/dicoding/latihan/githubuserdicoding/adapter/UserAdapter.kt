package com.dicoding.latihan.githubuserdicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.ItemUserGithubBinding
import com.dicoding.latihan.githubuserdicoding.raw.User

class UserAdapter(private val listUser: ArrayList<User>, private val callback: ShareCallback) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: ItemUserGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User){
            with(binding){
                tvItemName.text = user.name
                tvItemFollower.text = user.follower.toString()
                tvItemCompany.text = user.company
                tvItemLocation.text = user.location
                itemView.setOnClickListener { callback.onNavDetail(user) }

                imgshare.setOnClickListener { callback.onShareClick(user) }

                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgItemUser)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserGithubBinding = ItemUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemUserGithubBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listUser.size


}