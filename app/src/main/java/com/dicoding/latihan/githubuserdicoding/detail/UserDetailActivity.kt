package com.dicoding.latihan.githubuserdicoding.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.SectionPagerAdapter
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
import com.dicoding.latihan.githubuserdicoding.viewmodel.DetailViewModel

class UserDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_INTENT = "extra_intent"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

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
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //binding.toolbar.setNavigationOnClickListener{onBackPressed()}

        val username = intent.getStringExtra(EXTRA_INTENT)

        username?.let { viewModel.setDetailUser(it) }

        viewModel.getDetailUser().observe(this, {users ->
            if (username == users.username) {
                if (users != null) {
                    dataShowUser(users)
                }
            }
        })

        sectionPagerAdapter.username = username
        //


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
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


}