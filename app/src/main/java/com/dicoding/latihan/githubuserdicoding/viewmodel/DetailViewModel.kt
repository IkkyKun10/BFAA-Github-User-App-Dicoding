package com.dicoding.latihan.githubuserdicoding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuserdicoding.api.ApiConfig
import com.dicoding.latihan.githubuserdicoding.raw.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val TAG = "Detail ViewModel"

    private val user = MutableLiveData<UserDetailResponse>()

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
}