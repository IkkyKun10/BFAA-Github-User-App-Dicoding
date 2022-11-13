package com.dicoding.latihan.githubuserdicoding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ShareCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.adapter.ShareCallback
import com.dicoding.latihan.githubuserdicoding.adapter.UserAdapter
import com.dicoding.latihan.githubuserdicoding.appbar.favorite.FavoriteActivity
import com.dicoding.latihan.githubuserdicoding.appbar.settings.SettingPreference
import com.dicoding.latihan.githubuserdicoding.appbar.settings.SettingThemeActivity
import com.dicoding.latihan.githubuserdicoding.appbar.settings.SettingViewModel
import com.dicoding.latihan.githubuserdicoding.appbar.settings.ViewModelFactory
import com.dicoding.latihan.githubuserdicoding.databinding.ActivityMainBinding
import com.dicoding.latihan.githubuserdicoding.detail.UserDetailActivity
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import com.dicoding.latihan.githubuserdicoding.viewmodel.MainViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity(), ShareCallback {

    private lateinit var binding: ActivityMainBinding
    //private lateinit var viewModel : MainViewModel
    private lateinit var userAdapter: UserAdapter
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreference.getInstance(dataStore)
        settingViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSetting().observe(this) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        /**
         * Alternatif View Model untuk mendapatkan data thema
         */
        //viewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)

//        viewModel.getThemeSetting().observe(this, {isDarkModeActive ->
//            if (isDarkModeActive) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//        })

        getMainUser()

        binding.btnSearch.setOnClickListener {
            searchUser()

        }

        binding.edQuery.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        userAdapter = UserAdapter(this@MainActivity)
        showRecylerView()

        viewModel.getSearchUser().observe(this) { users ->
            if (users != null) {
                //userAdapter.setList(users)
                userAdapter.submitList(users)
                showLoading(false)
            }
            val item = users.isEmpty()
            if (item) {
                binding.frameError.visibility = View.VISIBLE
            }
        }

        viewModel.noData.observe(this) {
            showNoData(it)
            showLoading(false)
        }

        /**
         * alternatif intent ke kelas Detail
         */
//        userAdapter.setItemClickCallback(object : UserAdapter.OnItemClickCallback {
//            override fun onItemClick(data: UserSearch) {
//                Intent(this@MainActivity, UserDetailActivity::class.java).also {
//                    it.putExtra(UserDetailActivity.EXTRA_INTENT, data.username)
//                    startActivity(it)
//                }
//            }
//
//        })

    }

    private fun getMainUser() {
        showLoading(true)
        viewModel.getListUser()

        viewModel.listMainUsers.observe(this) { listMain ->
            if (listMain != null) {
                //userAdapter.setList(listMain)
                userAdapter.submitList(listMain)
                showLoading(false)
            }
        }
    }


    private fun searchUser() {
        val query = binding.edQuery.text.toString()
        if (query.isEmpty()) return
            showLoading(true)
            viewModel.setSearchUser(query)
    }


    private fun showRecylerView() {

        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.adapter = userAdapter
    }


    override fun onShareClick(users: UserSearch) {
        if (application != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(R.string.share)
                .setText(this.resources.getString(R.string.shareAccept, users.username))
                .startChooser()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav_button -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting_button -> {
                val intent = Intent(this, SettingThemeActivity::class.java)
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onNavDetail(users: UserSearch) {
        val intent = Intent(this, UserDetailActivity::class.java)
        intent.putExtra(UserDetailActivity.EXTRA_INTENT, users.username)
        intent.putExtra(UserDetailActivity.EXTRA_USERS, users)
        startActivity(intent)
    }



    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showNoData(state: Boolean) {
        if (state) binding.frameError.visibility = View.VISIBLE else View.GONE
    }
}