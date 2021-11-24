package com.dicoding.latihan.githubuserdicoding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.adapter.ShareCallback
import com.dicoding.latihan.githubuserdicoding.adapter.UserAdapter
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityMainBinding
import com.dicoding.latihan.githubuserdicoding.raw.User
import com.dicoding.latihan.githubuserdicoding.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private val list: ArrayList<User> = arrayListOf()
    private lateinit var viewModel : MainViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //list.addAll(GithubUser.listGithubUser



        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)


        binding.btnSearch.setOnClickListener {
            searchUser()

        }

        binding.edQuery.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        showRecylerView()

        viewModel.getUser().observe(this, { users ->
            if (users != null) {
                userAdapter.setListSearch(users)
                showLoading(false)
            } else {
                showLoading(false)
                binding.frameError.visibility = View.VISIBLE
            }
        })


    }


    private fun searchUser() {
        val query = binding.edQuery.text.toString()
        if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)
    }


    private fun showRecylerView() {
        userAdapter = UserAdapter()
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.adapter = userAdapter
    }

    /*
    override fun onShareClick(user: User) {
        if (application != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share)
                .setText(this.resources.getString(R.string.shareAccept, user.name))
                .startChooser()
        }
    }


    override fun onNavDetail(user: User) {
//        val intent = Intent(this, UserDetailActivity::class.java)
//        intent.putExtra(UserDetailActivity.EXTRA_INTENT, GithubUser.listGithubUser[0])
//        intent.putExtra(UserDetailActivity.EXTRA_INTENT, user)
//        startActivity(intent)
    }

     */

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}