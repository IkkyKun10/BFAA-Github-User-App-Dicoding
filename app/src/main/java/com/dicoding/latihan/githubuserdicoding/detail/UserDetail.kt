package com.dicoding.latihan.githubuserdicoding.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.SectionPagerAdapter
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityUserDetailBinding
import com.dicoding.latihan.githubuserdicoding.raw.User

class UserDetail : AppCompatActivity() {

    companion object {
        const val EXTRA_INTENT = "extra_intent"
    }

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        //supportActionBar?.hide()

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabsLayout.setupWithViewPager(binding.viewPager)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //binding.toolbar.setNavigationOnClickListener{onBackPressed()}

        val userIntent = intent.getParcelableExtra<User>(EXTRA_INTENT)

        dataShowUser(userIntent)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun dataShowUser(user: User?) {

        binding.tvName.text = user?.name
        binding.tvFollowers.text = user?.follower.toString()
        binding.tvFollowing.text = user?.following.toString()
        binding.tvRepository.text = user?.repository.toString()
        binding.tvCompany.text = user?.company
        binding.tvLocation.text = user?.location
        binding.tvUsername.text = "@${user?.username}"


        Glide.with(this)
            .load(user?.avatar)
            .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
            .error(R.drawable.ic_error)
            .into(binding.imagePhoto)
    }


}