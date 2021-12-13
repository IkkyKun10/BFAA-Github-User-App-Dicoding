package com.dicoding.latihan.githubuserdicoding.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityFavoriteBinding
import com.dicoding.latihan.githubuserdicoding.detail.UserDetailActivity
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavorite
import com.dicoding.latihan.githubuserdicoding.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val viewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User Github"

        showRecycler()

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback{
            override fun onItemClick(data: UserSearch) {
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_INTENT, data.username)
                    it.putExtra(UserDetailActivity.EXTRA_USERS, data)
                    startActivity(it)
                }
            }

        })

        viewModel.getListFavorite()?.observe(this, {users ->
            if (users != null) {
                val list = mapList(users)
                adapter.setList(list)
            }
        })

    }

    private fun showRecycler() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        adapter = FavoriteAdapter()
        binding.rvFavorite.adapter = adapter
        binding.rvFavorite.setHasFixedSize(true)
    }

    private fun mapList(users: List<UserFavorite>) : ArrayList<UserSearch> {
        val listUser = ArrayList<UserSearch>()
        for (user in users) {
            val mapp = UserSearch(
                user.username,
                user.userId,
                user.avatar,
                user.type,
                user.htmlUri
            )
            listUser.add(mapp)
        }
        return listUser
    }
}