package com.dicoding.latihan.githubuserdicoding.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.ItemUserGithubBinding
import com.dicoding.latihan.githubuserdicoding.raw.User
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    //private val callback: ShareCallback

    private val listUser = ArrayList<UserSearch>()
    private val listCallback = ArrayList<User>()

    fun setListSearch(users: ArrayList<UserSearch>) {
        this.listUser.clear()
        this.listUser.addAll(users)
        notifyDataSetChanged()
    }

    fun listMain(user: UserSearch) {

    }

    inner class UserViewHolder(private val binding: ItemUserGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserSearch){

            with(binding){
                tvItemName.text = user.username
                tvItemId.text = user.userId.toString()
                tvItemUrl.text = user.htmlUrl
                tvItemType.text = user.type


                Glide.with(itemView)
                    .load(user.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgItemUser)
            }

            Log.e("User Adapter", "Failure: ${user.username}")

        }

        fun clickCallback(user: User) {

            //itemView.setOnClickListener { callback.onNavDetail(user) }

            //imgshare.setOnClickListener { callback.onShareClick(user) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserGithubBinding = ItemUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemUserGithubBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
        //holder.clickCallback(listCallback[position])
    }

    override fun getItemCount(): Int = listUser.size


}