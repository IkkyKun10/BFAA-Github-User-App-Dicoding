package com.dicoding.latihan.githubuserdicoding.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.ItemUserGithubBinding
import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch

class UserAdapter(private var callback: ShareCallback) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    /**
     * alternatif intent ke kelas Detail
     */
//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    private val listUser = ArrayList<UserSearch>()

    fun setList(users: ArrayList<UserSearch>) {
        this.listUser.clear()
        this.listUser.addAll(users)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserGithubBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserSearch){

            /**
             * alternatif intent ke kelas Detail

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(user)
            }
            */

            with(binding){
                tvItemName.text = user.username
                tvItemId.text = user.userId.toString()
                tvItemUrl.text = user.htmlUrl
                tvItemType.text = user.type

                imgshare.setOnClickListener { callback.onShareClick(user) }
                itemView.setOnClickListener { callback.onNavDetail(user) }

                Glide.with(itemView)
                    .load(user.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgItemUser)
            }



            Log.e("User Adapter", "Checking: ${user.username}")

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemUserGithubBinding = ItemUserGithubBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemUserGithubBinding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    /**
     * alternatif intent ke kelas Detail
     */
//    interface OnItemClickCallback {
//        fun onItemClick(data: UserSearch)
//    }
}

