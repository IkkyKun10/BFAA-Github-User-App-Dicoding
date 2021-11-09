package com.dicoding.latihan.githubuserdicoding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.adapter.ShareCallback
import com.dicoding.latihan.githubuserdicoding.adapter.UserAdapter
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityMainBinding
import com.dicoding.latihan.githubuserdicoding.detail.UserDetailActivity
import com.dicoding.latihan.githubuserdicoding.raw.User
import com.dicoding.latihan.githubuserdicoding.raw.GithubUser

class MainActivity : AppCompatActivity(), ShareCallback {

    private lateinit var binding: ActivityMainBinding
    private val list: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list.addAll(GithubUser.listGithubUser)

        showRecyler()
    }

    private fun showRecyler() {
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.setHasFixedSize(true)
        val githubUser = UserAdapter(list, this)
        binding.rvMain.adapter = githubUser
    }

    override fun onShareClick(user: User) {
        if (application != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share)
                .setText("Segera kunjungi akun GitHub ${user.name} untuk menikmati sajian coding open source")
                .startChooser()
        }
    }

    override fun onNavDetail(user: User) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_INTENT, GithubUser.listGithubUser[0])
        intent.putExtra(UserDetailActivity.EXTRA_INTENT, user)
        startActivity(intent)
    }
}