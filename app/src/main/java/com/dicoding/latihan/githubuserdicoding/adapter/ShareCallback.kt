package com.dicoding.latihan.githubuserdicoding.adapter

import com.dicoding.latihan.githubuserdicoding.raw.User

interface ShareCallback {
    fun onShareClick(user: User)
}