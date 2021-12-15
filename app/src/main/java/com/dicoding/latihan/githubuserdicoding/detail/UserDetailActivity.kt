package com.dicoding.latihan.githubuserdicoding.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.SectionPagerAdapter
import com.dicoding.latihan.githubuserdicoding.appbar.settings.SettingThemeActivity
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavorite
import com.dicoding.latihan.githubuserdicoding.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INTENT = "extra_intent"
        const val EXTRA_USERS = "extra_users"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel by viewModels<DetailViewModel>()
    private var isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f


        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabsLayout.setupWithViewPager(binding.viewPager)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        //supportActionBar?.setDisplayShowHomeEnabled(true)

        //binding.toolbar.setNavigationOnClickListener{onBackPressed()}

        val username = intent.getStringExtra(EXTRA_INTENT)
        val userIntent = intent.getParcelableExtra<UserSearch>(EXTRA_USERS)

        showLoading(true)
        username?.let { viewModel.setDetailUser(it) }

        viewModel.getDetailUser().observe(this, {users ->
            if (username == users.username) {
                if (users != null) {
                    dataShowUser(users)
                    showLoading(false)
                }
            }
        })

        sectionPagerAdapter.username = username
        //
        addRemoveFav(userIntent)

    }

    private fun stateFavorite(state: Boolean) {
        if (state) {
            binding.fabFav.setImageResource(R.drawable.ic_favorite_pink)
        } else {
            binding.fabFav.setImageResource(R.drawable.ic_favorite_grey)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(this@UserDetailActivity, SettingThemeActivity::class.java)
            startActivity(intent)
        }
        finish()
        return true
    }

    private fun dataShowUser(users: UserDetailResponse?) {

        binding.tvName.text = users?.name
        binding.tvFollowers.text = users?.follower.toString()
        binding.tvFollowing.text = users?.following.toString()
        binding.tvRepository.text = users?.repository.toString()
        binding.tvCompany.text = users?.company
        binding.tvLocation.text = users?.location
        binding.tvUsername.text = this.resources.getString(R.string.name_user, users?.username)


        Glide.with(this)
            .load(users?.avatar)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding.imagePhoto)
    }


    private fun addRemoveFav(userIntent: UserSearch?) {
        CoroutineScope(Dispatchers.IO).launch {
            val count = userIntent?.let { viewModel.checkUser(it.userId) }
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        isChecked = true
                        stateFavorite(isChecked)
                    } else {
                        isChecked = false
                        stateFavorite(isChecked)
                    }
                }
            }
        }

        binding.fabFav.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                userIntent?.let { user ->
                    viewModel.insertFavorite(
                        user.userId,
                        user.username,
                        user.avatar,
                        user.type,
                        user.htmlUrl) }
                Toast.makeText(this, "Berhasil Favoritkan " + userIntent?.username, Toast.LENGTH_SHORT).show()
            } else {
                userIntent?.userId?.let { user -> viewModel.deleteById(user) }
                Toast.makeText(this, "Berhasil Menghapus ${userIntent?.username} Sebagai Favorite", Toast.LENGTH_SHORT).show()
            }
            stateFavorite(isChecked)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


}