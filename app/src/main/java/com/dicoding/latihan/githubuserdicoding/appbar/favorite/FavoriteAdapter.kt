package com.dicoding.latihan.githubuserdicoding.appbar.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.ItemFavoriteBinding
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch

class FavoriteAdapter : ListAdapter<UserSearch, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

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

    private val listFavorite = ArrayList<UserSearch>()

    fun setList(users: ArrayList<UserSearch>) {
        this.listFavorite.clear()
        this.listFavorite.addAll(users)
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userFavorite: UserSearch){

            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClick(userFavorite)
            }

            with(binding){
                binding.tvFavName.text = userFavorite.username
                binding.tvFavId.text = userFavorite.userId.toString()
                binding.tvFavType.text = userFavorite.type

                Glide.with(itemView)
                    .load(userFavorite.avatar)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgItemFav)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val item = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(item)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size


    interface OnItemClickCallback {
        fun onItemClick(data: UserSearch)
    }
}