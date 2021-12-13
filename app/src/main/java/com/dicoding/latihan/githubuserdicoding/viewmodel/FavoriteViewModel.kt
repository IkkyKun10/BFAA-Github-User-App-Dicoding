package com.dicoding.latihan.githubuserdicoding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavorite
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavoriteDao
import com.dicoding.latihan.githubuserdicoding.raw.local.UserFavoriteDatabase

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDb: UserFavoriteDatabase?

    init {
        userDb = UserFavoriteDatabase.getInstance(application)
        userDao = userDb?.userFavoriteDao()
    }

    fun getListFavorite() : LiveData<List<UserFavorite>>? {
        return userDao?.getFavoriteUser()
    }
}