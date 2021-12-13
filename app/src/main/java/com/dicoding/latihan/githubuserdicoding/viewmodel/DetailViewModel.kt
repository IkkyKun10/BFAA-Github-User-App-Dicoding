package com.dicoding.latihan.githubuserdicoding.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuserdicoding.api.ApiConfig
import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavorite
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavoriteDao
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavoriteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "Detail ViewModel"

    private val user = MutableLiveData<UserDetailResponse>()

    private var userDao: UserFavoriteDao?
    private var userDb: UserFavoriteDatabase?

    init {
        userDb = UserFavoriteDatabase.getInstance(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun setDetailUser(username: String) {
        val client = ApiConfig.apiInstance.geDetailUser(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(call: Call<UserDetailResponse>, response: Response<UserDetailResponse>) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                } else {
                    Log.e(TAG, "Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(TAG, "Failure: ${t.message}")
            }

        })
    }

    fun getDetailUser() : LiveData<UserDetailResponse> {
        return user
    }

    fun insertFavorite(userId: Int, username: String, avatar: String, type: String, htmlUri: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val users = UserFavorite(
                userId,
                username,
                avatar,
                type,
                htmlUri
            )
            userDao?.insertFavorite(users)
        }
    }

    fun checkUser(userId: Int) = userDao?.checkUser(userId)

    fun deleteById(userId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteById(userId)
        }
    }
}