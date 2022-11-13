package com.dicoding.latihan.githubuserdicoding.detail.follow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.ItemFollowBinding
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch

class FollowAdapter : ListAdapter<UserSearch, FollowAdapter.FollowViewHolder>(DIFF_CALLBACK) {

    companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserSearch>() {
                override fun areItemsTheSame(oldItem: UserSearch, newItem: UserSearch): Boolean =
                    oldItem.userId == newItem.userId


                override fun areContentsTheSame(oldItem: UserSearch, newItem: UserSearch): Boolean =
                    oldItem == newItem

            }
        }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val listFollow = ArrayList<UserSearch>()

    fun setList(users: ArrayList<UserSearch>) {
        this.listFollow.clear()
        this.listFollow.addAll(users)
        notifyDataSetChanged()
    }

    inner class FollowViewHolder(private val binding: ItemFollowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(users: UserSearch) {

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(users)
            }

            with(binding) {
                binding.tvFollowName.text = users.username
                binding.tvFollowId.text = users.userId.toString()

                Glide.with(itemView)
                    .load(users.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgItemFollow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val itemBinding = ItemFollowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size


    interface OnItemClickCallback {
        fun onItemClick(data: UserSearch)
    }
}