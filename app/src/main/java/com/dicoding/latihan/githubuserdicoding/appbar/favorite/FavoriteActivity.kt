package com.dicoding.latihan.githubuserdicoding.appbar.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.appbar.settings.SettingThemeActivity
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
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
            val item = users.isEmpty()
            if (item) {
                binding.empty.visibility = View.VISIBLE
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId)  {
            R.id.settings -> {
                val intent = Intent(this@FavoriteActivity, SettingThemeActivity::class.java)
                startActivity(intent)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return true
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