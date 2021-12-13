package com.dicoding.latihan.githubuserdicoding.appbar.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.ItemFavoriteBinding
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

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
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size


    interface OnItemClickCallback {
        fun onItemClick(data: UserSearch)
    }
}