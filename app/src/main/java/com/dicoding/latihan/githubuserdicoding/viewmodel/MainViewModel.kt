package com.dicoding.latihan.githubuserdicoding.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.latihan.githubuserdicoding.api.ApiConfig
import com.dicoding.latihan.githubuserdicoding.raw.UserResponse
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val listUsers = MutableLiveData<ArrayList<UserSearch>>()

    val listMainUsers : LiveData<ArrayList<UserSearch>> = listUsers

//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading : LiveData<Boolean> = _isLoading

    private var _noData = MutableLiveData<Boolean>()
    val noData : LiveData<Boolean> = _noData

    fun setSearchUser(query: String) {
        //_isLoading.value = true
        val client = ApiConfig.apiInstance.getSearchUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                //_isLoading.value = false
                _noData.value = false
                if (response.isSuccessful) {
                    listUsers.postValue(response.body()?.items)
                } else {
                    _noData.value = true
                    Log.d(TAG, "Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                //_isLoading.value = false
                _noData.value = true
                Log.d(TAG, "Failure: ${t.message}")
            }

        })

    }

    fun getListUser() {
        //_isLoading.value = true
        val client = ApiConfig.apiInstance.getListUser("users")
        client.enqueue(object : Callback<ArrayList<UserSearch>> {
            override fun onResponse(call: Call<ArrayList<UserSearch>>, response: Response<ArrayList<UserSearch>>) {
                //_isLoading.value = false
                _noData.value = false
                if (response.isSuccessful) {
                    listUsers.value = response.body()
                } else {
                    _noData.value = true
                    Log.d(TAG, "Failure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<UserSearch>>, t: Throwable) {
                //_isLoading.value = false
                _noData.value = true
                Log.d(TAG, "Failure: ${t.message}")
            }

        })
    }

    fun getSearchUser() : LiveData<ArrayList<UserSearch>> {
        return listUsers
    }

}