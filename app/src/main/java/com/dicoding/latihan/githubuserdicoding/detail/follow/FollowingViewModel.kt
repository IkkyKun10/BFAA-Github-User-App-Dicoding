package com.dicoding.latihan.githubuserdicoding.detail.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuserdicoding.api.ApiConfig
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val TAG = "FollowingViewModel"

    private var listFollowingUsers = MutableLiveData<ArrayList<UserSearch>>()

    fun setFollowingList(username: String) {
        val client = ApiConfig.apiInstance.getFollowingUser(username)
        client.enqueue(object : Callback<ArrayList<UserSearch>> {
            override fun onResponse(call: Call<ArrayList<UserSearch>>, response: Response<ArrayList<UserSearch>>) {
                if (response.isSuccessful) {
                    listFollowingUsers.postValue(response.body())
                } else {
                    Log.e(TAG, "Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserSearch>>, t: Throwable) {
                Log.e(TAG, "Failure: ${t.message}")
            }

        })
    }

    fun getFollowingList() : LiveData<ArrayList<UserSearch>> {
        return listFollowingUsers
    }
}